package training.MarkovChain;

import analysis.harmonic.ChordDegree;
import javafx.util.Pair;

import java.util.HashMap;

public class MarkovDegree {

    // Using two ChordDegree parameter instead of a Pair one because
    // Values cannot be changed in a Javafx pair ...
    // https://docs.oracle.com/javafx/2/api/javafx/util/Pair.html
    private ChordDegree depth2_;
    private ChordDegree depth1_;
    private HashMap<Pair<ChordDegree, ChordDegree>, HashMap<ChordDegree, Integer>> transitions_;

    /**
     * Constructor for the MarkovDegree class.
     * Takes no parameter so transition matrix should be
     */
    public MarkovDegree() {
        depth1_ = null;
        depth2_ = null;
        transitions_ = new HashMap<>();
    }

    // ---------- PUBLIC FUNCTIONS ----------

    /**
     * This function add a degree to fill the markov transition matrix.
     * @param chordDegree
     */
    public void addDegree(ChordDegree chordDegree) {
        Pair<ChordDegree, ChordDegree> line = new Pair<>(depth2_, depth1_);
        addLineEntry(line, chordDegree);
        HashMap<ChordDegree, Integer> column = transitions_.get(line);
        updateDepth(chordDegree);
    }

    // --------- PRIVATE FUNCTIONS ----------

    /**
     * This function add a new column in the Markov Transition Matrix when the given
     * ChordDegree doesn't match a key of the column. Otherwise, it increment the integer
     * value of the existing key.
     * @param column
     * @param key
     */
    private void addColumnEntry(HashMap<ChordDegree, Integer> column, ChordDegree key) {
        if (column.get(key) == null)
            column.put(key, 1);
        else
            column.put(key, column.get(key) + 1);
    }

    /**
     * This function add a new line in the Markov Transition matrix when
     * the combinaison of values of depth1_ and depth2_ is new.
     * @param lineKey
     * @param columnKey
     */
    private void addLineEntry(Pair<ChordDegree, ChordDegree> lineKey, ChordDegree columnKey) {
        HashMap<ChordDegree, Integer> column = transitions_.get(lineKey);
        if (column == null) {
            column = new HashMap<>();
            addColumnEntry(column, columnKey);
            transitions_.put(lineKey, column);
        }
        else
            addColumnEntry(column, columnKey);
    }

    /**
     * This function update the depth the transition matrix by replacing
     * the depth2 value by the depth1 and the depth1 value by the newly added
     * ChordDegree.
     * @param chordDegree
     */
    private void updateDepth(ChordDegree chordDegree) {
        depth2_ = depth1_;
        depth1_ = depth2_;
    }
}
