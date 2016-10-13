package training.probability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * FIXME
 * T must be hashable.
 * Must store the sum of a line somewhere -> hashmap(null) ?
 * @param <T>
 */
public class MarkovMatrix<T> {
    private String type_;
    private int depth_;
    private ArrayList<T> context_;
    private HashMap<ArrayList<T>, HashMap<T, Integer>> transitions_;

    public MarkovMatrix(String type, int depth) {
        type_ = type;
        depth_ = depth;
        context_ = new ArrayList<>(Collections.nCopies(depth_, null));
        transitions_ = new HashMap<>();
    }

    public void updateContext(T t) {
        for (int i = context_.size() - 1; i > 0; --i)
            context_.set(i, context_.get(i - 1));
        context_.set(0, t);
    }

    public void addEntry(T entry) {
        updateContext(entry);
    }

    /**
     * Get a random double and a inputs ArrayList<T> in parameters and returns a T element
     * in function of the probability.
     */
    public void getValue() {
        return;
    }



}
