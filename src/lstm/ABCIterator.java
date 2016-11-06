package lstm;

import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ABCIterator implements DataSetIterator {
    private int miniBatchSize_;
    private int exempleLength_;

    /**
     * Constructor of DataSetIterator
     * @param filePath
     * @param miniBatchSize Number of examples taken in one batch by the LSTM
     * @param exampleLength Size of an example (length of a row)
     * @throws IOException
     */
    public ABCIterator(String filePath, int miniBatchSize, int exampleLength) throws IOException {
        // Check if given dataFile exist.
        if( !new File(filePath).exists())
            throw new IOException("Could not access file (does not exist): " + filePath);
        // Check miniBatchSize value > 0
        if( miniBatchSize <= 0 )
            throw new IllegalArgumentException("Invalid miniBatchSize strictly positive");

        miniBatchSize_ = miniBatchSize;
        exempleLength_ = exampleLength;
    }

    @Override
    public DataSet next(int i) {
        return null;
    }

    @Override
    public DataSet next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int totalExamples() {
        return 0;
    }

    @Override
    public int inputColumns() {
        return 0;
    }

    @Override
    public int totalOutcomes() {
        return 0;
    }


    @Override
    public void reset() {

    }

    @Override
    public int batch() {
        return 0;
    }

    @Override
    public int cursor() {
        return 0;
    }

    @Override
    public int numExamples() {
        return 0;
    }

    // Basic or Unimplemented Methods

    @Override
    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean asyncSupported() {
        return true;
    }

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

}
