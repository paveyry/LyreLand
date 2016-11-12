package lstm;

import main.options.ExecutionParameters;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.weights.HistogramIterationListener;
import org.deeplearning4j.ui.UiConnectionInfo.Builder.*;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

/**
 * Class for ABC training using LSTMs
 */
public class LSTMTrainer implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;

    // Parameters for training
    private int lstmLayerSize_;
    private int batchSize_;
    private int exampleLength_;
    private int truncatedBackPropThroughTimeLength_;
    private int nbEpochs_;
    private int generateSamplesEveryNMinibatches_;
    private int seed_;
    private Random random_;
    private String generationInitialization_;

    // DataSet Iterator
    private ABCIterator trainingSetIterator_;

    // LSTM neural network
    private MultiLayerNetwork lstmNet_;

    // Two convertion HashMap
    private HashMap<Character, Integer> charToInt_;
    private HashMap<Integer, Character> intToChar_;

    /**
     * Constructor
     * @param trainingSet Text file containing several ABC music files
     * @throws IOException
     */
    public LSTMTrainer(String trainingSet, int seed) throws IOException {
        lstmLayerSize_ = 200; // original 200
        batchSize_ = 50; // original 32
        exampleLength_ = 129;
        truncatedBackPropThroughTimeLength_ = 50;
        nbEpochs_ = 1;
        generateSamplesEveryNMinibatches_ = 10;
        generationInitialization_ = "X";
        seed_ = seed;
        random_ = new Random(seed);

        trainingSetIterator_ = new ABCIterator(trainingSet, Charset.forName("ASCII"), batchSize_,
                                               exampleLength_, random_);
        charToInt_ = trainingSetIterator_.getCharToInt();
        intToChar_ = trainingSetIterator_.getIntToChar();
        int nOut = trainingSetIterator_.totalOutcomes();

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(1)
                .learningRate(0.05) // 0.1 original
                .rmsDecay(0.95) // 0.95 original
                .seed(seed_)
                .regularization(true) // true original
                .l2(0.001)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.RMSPROP)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(trainingSetIterator_.inputColumns()).nOut(lstmLayerSize_)
                        .activation("tanh").build())
                .layer(1, new GravesLSTM.Builder().nIn(lstmLayerSize_).nOut(lstmLayerSize_)
                        .activation("tanh").build())
                .layer(2, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).activation("softmax")
                        .nIn(lstmLayerSize_).nOut(nOut).build())
                .backpropType(BackpropType.TruncatedBPTT)
                    .tBPTTForwardLength(truncatedBackPropThroughTimeLength_)
                    .tBPTTBackwardLength(truncatedBackPropThroughTimeLength_)
                .pretrain(false).backprop(true)
                .build();

        lstmNet_ = new MultiLayerNetwork(conf);
        lstmNet_.init();
        lstmNet_.setListeners(new ScoreIterationListener(1));
        //lstmNet_.setListeners(new HistogramIterationListener(1));

        if (ExecutionParameters.verbose) {
            Layer[] layers = lstmNet_.getLayers();
            int totalNumParams = 0;
            for (int i = 0; i < layers.length; i++) {
                int nParams = layers[i].numParams();
                System.out.println("Number of parameters in layer " + i + ": " + nParams);
                totalNumParams += nParams;
            }
            System.out.println("Total number of network parameters: " + totalNumParams);
        }
    }

    /**
     * Method to run the LSTM training
     */
    public void train() {
        int counter = 0;
        for (int i = 0; i < nbEpochs_; ++i) {
            while (trainingSetIterator_.hasNext()) {
                DataSet ds = trainingSetIterator_.next();
                lstmNet_.fit(ds);
                if (counter % generateSamplesEveryNMinibatches_ == 0) {
                    String[] samples = sampleCharactersFromNetwork(generationInitialization_,lstmNet_,
                                                                   trainingSetIterator_, 500, 1);
                    for(int j = 0; j < samples.length; j++){
                        System.out.print(samples[j]);
                        System.out.println();
                    }
                }
                ++counter;
            }
            trainingSetIterator_.reset(); // Reset for next epoch
            counter = 0;
        }
        System.out.println("LSTM training complete");
    }

    /** Generate a sample from the network, given an (optional, possibly null) initialization. Initialization
     * can be used to 'prime' the RNN with a sequence you want to extend/continue.<br>
     * Note that the initalization is used for all samples
     * @param initialization String, may be null. If null, select a random character as initialization for all samples
     * @param charactersToSample Number of characters to sample from network (excluding initialization)
     * @param net MultiLayerNetwork with one or more GravesLSTM/RNN layers and a softmax output layer
     * @param iter CharacterIterator. Used for going from indexes back to characters
     */
    private String[] sampleCharactersFromNetwork(String initialization, MultiLayerNetwork net,
                                                        ABCIterator iter, int charactersToSample, int numSamples ){
        //Create input for initialization
        INDArray initializationInput = Nd4j.zeros(numSamples, iter.inputColumns(), initialization.length());
        char[] init = initialization.toCharArray();
        for(int i=0; i < init.length; i++){
            int idx = charToInt_.get(init[i]);
            for(int j=0; j < numSamples; j++)
                initializationInput.putScalar(new int[] {j, idx, i}, 1.0f);
        }

        StringBuilder[] sb = new StringBuilder[numSamples];
        for(int i=0; i < numSamples; i++) sb[i] = new StringBuilder(initialization);

        //Sample from network (and feed samples back into input) one character at a time (for all samples)
        //Sampling is done in parallel here
        net.rnnClearPreviousState();
        INDArray output = net.rnnTimeStep(initializationInput);
        output = output.tensorAlongDimension(output.size(2) - 1, 1, 0);	//Gets the last time step output

        for(int i=0; i < charactersToSample; i++){
            //Set up next input (single time step) by sampling from previous output
            INDArray nextInput = Nd4j.zeros(numSamples, iter.inputColumns());
            //Output is a probability distribution. Sample from this for each example we want to generate, and add it to the new input
            for(int s = 0; s < numSamples; s++){
                double[] outputProbDistribution = new double[iter.totalOutcomes()];
                for(int j=0; j < outputProbDistribution.length; j++ )
                    outputProbDistribution[j] = output.getDouble(s,j);
                int sampledCharacterIdx = sampleFromDistribution(outputProbDistribution);

                nextInput.putScalar(new int[]{s,sampledCharacterIdx}, 1.0f); //Prepare next time step input
                sb[s].append(intToChar_.get(sampledCharacterIdx));	//Add sampled character to StringBuilder (human readable output)
            }

            output = net.rnnTimeStep(nextInput);	//Do one time step of forward pass
        }
        String[] out = new String[numSamples];
        for( int i=0; i<numSamples; i++ ) out[i] = sb[i].toString();
        return out;
    }

    /** Given a probability distribution over discrete classes, sample from the distribution
     * and return the generated class index.
     * @param distribution Probability distribution over classes. Must sum to 1.0
     */
    public int sampleFromDistribution(double[] distribution){
        double d = random_.nextDouble();
        double sum = 0.0;
        for(int i=0; i < distribution.length; i++){
            sum += distribution[i];
            if(d <= sum)
                return i;
        }
        throw new IllegalArgumentException("Distribution is invalid ? d = " + d + ", sum = " + sum);
    }

    /**
     * Serialize current object
     * @param filename File to store serialized data
     * @throws IOException
     */
    public void serialize(String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this);
    }

    /**
     * Deserialize an object
     * @param filename File where the serialized data is stored
     * @return Deserialized object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LSTMTrainer deserialize(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        return (LSTMTrainer) ois.readObject();
    }
}
