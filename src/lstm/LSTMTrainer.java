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
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

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

    /**
     * Constructor
     * @param trainingSet Text file containing several ABC music files
     * @throws IOException
     */
    public LSTMTrainer(String trainingSet, int seed) throws IOException {
        lstmLayerSize_ = 200;
        batchSize_ = 32;
        exampleLength_ = 1000;
        truncatedBackPropThroughTimeLength_ = 50;
        nbEpochs_ = 1;
        generateSamplesEveryNMinibatches_ = 10;
        generationInitialization_ = "X";
        seed_ = seed;
        random_ = new Random(seed);

        // Create the two convertion hashMap ... FIXME
        HashMap<Character, Integer> charToInt = new HashMap<>();
        HashMap<Integer, Character> intToChar = new HashMap<>();

        trainingSetIterator_ = new ABCIterator(trainingSet, Charset.forName("UTF-8"), batchSize_,
                                               exampleLength_, charToInt, random_);
        int nOut = trainingSetIterator_.totalOutcomes();

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(1)
                .learningRate(0.1)
                .rmsDecay(0.95)
                .seed(seed_)
                .regularization(true)
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
        int miniBatchNumber = 0;
        for (int i = 0; i < nbEpochs_; ++i) {
            while (trainingSetIterator_.hasNext()) {
                DataSet ds = trainingSetIterator_.next();
                lstmNet_.fit(ds);
            }
            trainingSetIterator_.reset(); // Reset for next epoch
        }
        System.out.println("LSTM training complete");
    }
}
