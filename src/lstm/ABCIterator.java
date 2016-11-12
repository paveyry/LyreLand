package lstm;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class ABCIterator implements DataSetIterator, Serializable {
    private int batchSize_; // Number of lines of the files taken at once by the next() function.
    private int exampleLength_; // Size of one line of the file.
    private int numberOfExample_; // Number of line in the file. (1 example = 1 line)
    private char[] fileChars_; // All the characters from the file.
    private HashMap<Character, Integer> charToInt_; // HashMap used to convert a Character to an Integer for INDArrays.
    private HashMap<Integer, Character> intToChar_; // HashMap used to convert an Integer to a Character for INDArrays.
    private Random random_; // Randomize inputs given in a batch.
    private LinkedList<Integer> exampleStartOffsets_; // All the offsets where a line start in the fileChars_ array.

    /**
     * Constructor of DataSetIterator
     * @param filePath
     * @param batchSize Number of examples taken in one batch by the LSTM
     * @param exampleLength Size of an example (length of a row)
     * @throws IOException
     */
    public ABCIterator(String filePath, Charset fileEncoding, int batchSize, int exampleLength, Random random) throws IOException {
        // Check if given dataFile exist.
        if( !new File(filePath).exists())
            throw new IOException("Could not access file (does not exist): " + filePath);
        // Check miniBatchSize value > 0
        if( batchSize <= 0 )
            throw new IllegalArgumentException("Invalid miniBatchSize strictly positive");

        batchSize_ = batchSize;
        exampleLength_ = exampleLength;
        charToInt_ = new HashMap<>();
        intToChar_ = new HashMap<>();
        random_ = random;
        exampleStartOffsets_ = new LinkedList<>();

        List<String> lines = Files.readAllLines(new File(filePath).toPath(), fileEncoding); // Remove '\n' ...
        numberOfExample_ = lines.size();
        char[] characters = new char[(numberOfExample_ * exampleLength_) + numberOfExample_]; // To add missing '\n'
        int index = 0;
        charToInt_.put('\n', 0);
        intToChar_.put(0, '\n');
        int mapindex = 0;
        for(String line : lines ){
            char[] currLine = line.toCharArray();
            for (char c : currLine) {
                characters[index++] = c;
                if (!charToInt_.containsKey(c))
                    charToInt_.put(c, mapindex);
                if (!intToChar_.containsValue(c))
                    intToChar_.put(mapindex++, c);
            }
            characters[index++] = '\n';
        }
        fileChars_ = characters;
        initializeOffsets();
    }

    // PRIVATE METHODS

    /**
     * This function fill the exampleStartOffsets_ linkedList with the start values of each line
     * and shuffle them according to random_.
     */
    private void initializeOffsets() {
        // Initialize all offset and shuffle them according to random_.
        for (int i = 0; i < numberOfExample_; i++)
            exampleStartOffsets_.add(i * exampleLength_);
        //Collections.shuffle(exampleStartOffsets_, random_);
    }

    // PUBLIC METHODS
    @Override
    public boolean hasNext() {
        return exampleStartOffsets_.size() > 0;
    }

    @Override
    public DataSet next() {
        return this.next(batchSize_);
    }

    /**
     * This function fill two arrays (input/output) with the next N examples
     * in order to create the dataSet given to the LSTM.
     * @param number
     * @return
     */
    @Override
    public DataSet next(int number) {
        if(exampleStartOffsets_.size() == 0)
            throw new NoSuchElementException();

        int currBatchSize = Math.min(number, exampleStartOffsets_.size());

        // Order of the INDArray.
        // dimension 0 = number of examples in batch
        // dimension 1 = size of each vector (i.e., number of characters)
        // dimension 2 = length of each time series/example
        INDArray input = Nd4j.create(new int[]{currBatchSize, charToInt_.size(), exampleLength_}, 'f');
        INDArray labels = Nd4j.create(new int[]{currBatchSize, charToInt_.size(), exampleLength_}, 'f');

        for(int i = 0; i < currBatchSize; i++){
            int startIndex = exampleStartOffsets_.removeFirst();
            int endIndex = startIndex + exampleLength_;
            int currCharIndex = charToInt_.get(fileChars_[startIndex]);	//Current input
            int c = 0;
            for(int j = startIndex+1; j < endIndex; j++, c++){
                int nextCharIndex = charToInt_.get(fileChars_[j]); //Next character to predict
                input.putScalar(new int[]{i, currCharIndex, c}, 1.0);
                labels.putScalar(new int[]{i, nextCharIndex, c}, 1.0);
                currCharIndex = nextCharIndex;
            }
        }
        return new DataSet(input, labels);
    }

    // Number of possible
    @Override
    public int totalExamples() {
        return numberOfExample_;
    }

    @Override
    public int inputColumns() {
        return charToInt_.size();
    }

    @Override
    public int totalOutcomes() {
        return charToInt_.size();
    }

    @Override
    public void reset() {
        exampleStartOffsets_.clear();
        initializeOffsets();
    }

    @Override
    public int cursor() {
        return totalExamples() - exampleStartOffsets_.size();
    }

    @Override
    public int numExamples() {
        return totalExamples();
    }

    @Override
    public int batch() {
        return batchSize_;
    }

    @Override
    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean asyncSupported() {
        return true;
    }

    // UNIMPLEMENTED METHODS (not used ...)

    @Override
    public void setPreProcessor(DataSetPreProcessor dataSetPreProcessor) {
        throw new UnsupportedOperationException("Method setPreProcessor not implemented");
    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        throw new UnsupportedOperationException("Method getPreProcessor not Implemented");
    }

    @Override
    public List<String> getLabels() {
        throw new UnsupportedOperationException("Method getLabels not Implemented");
    }

    // GETTERS
    public HashMap<Character, Integer> getCharToInt() {
        return charToInt_;
    }

    public HashMap<Integer, Character> getIntToChar() {
        return intToChar_;
    }

}
