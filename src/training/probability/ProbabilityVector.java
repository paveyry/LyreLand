package training.probability;

import java.util.HashMap;
import java.util.Random;

public class ProbabilityVector<T> {
    private String type_;
    private HashMap<T, Double> probabilities_;

    /**
     * Constructor of the probabilityVector. The parameter Type is only used
     * for the user to know what are the probabilities learn by this vector.
     * @param type
     */
    public ProbabilityVector(String type) {
        type_ = type;
        probabilities_ = new HashMap<>();
    }

    /**
     * Add a new entry in the table if it does not exist. Otherwise
     * It increase the value of an existing one by 1.0.
     * @param entry
     */
    public void addEntry(T entry) {
        if (probabilities_.get(entry) == null)
            probabilities_.put(entry, 1.0);
        else
            probabilities_.put(entry, probabilities_.get(entry) + 1.0);
    }

    /**
     * Divide each value of the table per the sum of all values to
     * Normalize the sum to 1.0.
     */
    public void closeLearning() {
        double sum = 0;
        for (T key : probabilities_.keySet())
            sum += probabilities_.get(key);
        for (T key : probabilities_.keySet())
            probabilities_.put(key, probabilities_.get(key) / sum);
    }

    /**
     * This function returns randomly according to the leaned probabilities.
     * @return A <T> value.
     */
    public T getValue() {
        Random generator = new Random();
        double random =  generator.nextDouble(); // Generate double between 0 and 1.
        double sum = 0;
        for (T key : probabilities_.keySet())
            if (probabilities_.get(key) + sum > random)
                return key;
            else
                sum += probabilities_.get(key);
        return null;
    }

    /**
     * This function transform the probabilityVector into a String.
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T entry : probabilities_.keySet())
            sb.append(entry.toString()).append(" : ").append(probabilities_.get(entry)).append("\n");
        return sb.toString();
    }

    // GETTERS / SETTERS

    public String getType() {
        return type_;
    }

    public HashMap<T, Double> getProbabilities() {
        return probabilities_;
    }
}
