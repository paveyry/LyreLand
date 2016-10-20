package training.probability;

import java.util.*;

/**
 * Markov transition matrix generic class
 * T must be hashable.
 * @param <T> Type of the elements
 */
public class MarkovMatrix<T> {
    private int depth_;
    private Queue<T> context_;
    private HashMap<ArrayList<T>, HashMap<T, Integer>> transitionMatrix_;

    /**
     * Constructor
     * @param depth Number of elements of the context
     */
    public MarkovMatrix(int depth) {
        depth_ = depth;
        resetContext();
        transitionMatrix_ = new HashMap<>();
    }

    /**
     * Reset the context to the starting context
     */
    public void resetContext() {
        context_ = new LinkedList<>(Collections.nCopies(depth_, null));
    }

    /**
     * Insert a new element in the Markov chain. The context is preserved.
     * @param entry Element to insert
     */
    public void addEntry(T entry) {
        ArrayList<T> key = new ArrayList<T>(context_);
        HashMap<T, Integer> line = transitionMatrix_.get(key);
        if (line == null) {
            line = new HashMap<>();
            line.put(entry, 1);
            line.put(null, 1); // Store the number of elements in the line
            transitionMatrix_.put(key, line);
        }
        else {
            Integer nb = line.get(entry);
            line.put(entry, (nb != null) ? nb + 1 : 1);
            nb = line.get(null);
            line.put(null, (nb != null) ? nb + 1 : 1);
        }
        updateContext(entry);
    }

    private void updateContext(T t) {
        context_.remove();
        context_.add(t);
    }

    /**
     * Get a new random element in the Markov chain.
     * @param context The last n elements (n = depth_)
     * @param generator Pseudo-random generator
     * @return
     */
    public T getRandomValue(List<T> context, Random generator) {
        HashMap<T, Integer> line = transitionMatrix_.get(context);
        double count = line.get(null);
        double ran =  generator.nextDouble() * count;
        double sum = 0;
        for (T key : line.keySet()) {
            if (key == null)
                continue;
            sum += (double) line.get(key);
            if (sum >= ran)
                return key;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<T> context : transitionMatrix_.keySet()) {
            sb.append(context).append("\t\t:\t").append(transitionMatrix_.get(context)).append('\n');
        }
        return sb.toString();
    }

    public HashMap<ArrayList<T>, HashMap<T, Integer>> getTransitionMatrix() {
        return transitionMatrix_;
    }
}
