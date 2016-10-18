package training;

import analysis.ScoreAnalyser;
import analysis.bars.Bar;
import analysis.bars.BarNote;
import analysis.harmonic.ChordDegree;
import training.probability.MarkovMatrix;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for Melodic sequences learning
 */
public class MelodicLearner {
    private HashMap<ChordDegree, MarkovMatrix<Integer>> trainingData_;

    /**
     * Constructor
     */
    public MelodicLearner() {
        trainingData_ = new HashMap<>();
    }

    /**
     * Process training on a specific score
     * @param score Extracted features of the score
     */
    public void learnExample(ScoreAnalyser score) {
        ArrayList<ChordDegree> degrees = score.getDegreeList();
        ArrayList<Bar> bars = score.getBarLexer().getBars();
        int beatsPerBar = score.getBeatsPerBar();

        ChordDegree degree = degrees.get(0);
        int deg_ind = 1;
        for (Bar bar : bars) {
            double bar_frac = 0.0;
            for (;bar_frac < beatsPerBar; ++deg_ind) {
                ArrayList<Integer> notes = getDegreeNotes(degree, bar, bar_frac, beatsPerBar);
                bar_frac += (double)beatsPerBar / degree.getBarFractionDen();
                if (deg_ind < degrees.size())
                    degree = degrees.get(deg_ind);
                else
                    degree = null;

 }
        }
    }

    /**
     * Get all the notes in a section of the score corresponding to a specific degree
     * @param degree The degree of the section
     * @param bar The bar containing the section
     * @param low_bound The beginning beat of the section relative to the bar
     * @param beatsPerBar The number of beats per bar
     * @return
     */
    private ArrayList<Integer> getDegreeNotes(ChordDegree degree, Bar bar, double low_bound, int beatsPerBar) {
        ArrayList<Integer> result = new ArrayList<>();
        double high_bound = low_bound + (double)beatsPerBar / degree.getBarFractionDen();
        ArrayList<BarNote> notes = bar.getNotes();
        for (BarNote note : notes) {
            double startTime = note.getStartTime();
            if (low_bound < startTime && startTime < high_bound) {
                if (note.getDuration() > 0)
                    result.add(note.getPitch());
            }
        }
        return result;
    }

    private void addEntry(ChordDegree context, ArrayList<Integer> pitches) {
        MarkovMatrix<Integer> mat = trainingData_.get(context);
        if (mat == null) {
            mat = new MarkovMatrix<>(1);
            for (Integer entry : pitches)
                mat.addEntry(entry);
            mat.resetContext();
            trainingData_.put(context, mat);
        }
        else {
            for (Integer entry : pitches)
                mat.addEntry(entry);
            mat.resetContext();
        }
    }
}
