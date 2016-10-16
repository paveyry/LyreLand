package training.rhythmic;

import analysis.ScoreAnalyser;
import analysis.bars.Bar;
import analysis.bars.BarNote;
import analysis.harmonic.ChordDegree;
import training.probability.MarkovMatrix;

import java.util.*;

import static java.lang.System.exit;


/**
 * Created by light on 14/10/16.
 */
public class RhythmicLearner {

    public static void rhythmlearning(ScoreAnalyser score, HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> rhythmMatrices) {
        Queue<ChordDegree> context = new LinkedList<>(Collections.nCopies(2, null));
        ArrayList<ChordDegree> degrees = score.getDegreeList();
        ArrayList<Bar> bars = score.getBarLexer().getBars();
        int beatsPerBar = score.getBeatsPerBar();

        ChordDegree degree = degrees.get(0);
        context.add(degree);
        int degree_index = 1;
        for (Bar bar : bars) {
            double bar_frac = 0;
            while (bar_frac < beatsPerBar) {
                ArrayList<Double> rhythms = getRhythmsDegree(degree, bar, bar_frac, beatsPerBar);
                bar_frac += (double)beatsPerBar / degree.getBarFractionDen();
                if (degree_index < degrees.size())
                    degree = degrees.get(degree_index);
                else
                    degree = null;
                context.remove();
                context.add(degree);
                add_entry(context, rhythms, rhythmMatrices);
                degree_index++;
            }
        }
        // Print the hashmap
        //rhythmMatrices.forEach((a, b) -> System.out.println(a.get(0) + ", " + a.get(1) + ", " + a.get(2) + " : " + b.toString()));
    }

    private static ArrayList<Double> getRhythmsDegree(ChordDegree degree, Bar bar, double low_bound, int beatsPerBar) {
        ArrayList<Double> result = new ArrayList<>();
        double high_bound = low_bound + (double)beatsPerBar / degree.getBarFractionDen();
        ArrayList<BarNote> notes = bar.getNotes();
        for (BarNote note : notes) {
            double startTime = note.getStartTime();
            if (low_bound < startTime && startTime < high_bound) {
                if (note.getDuration() > 0)
                    result.add(note.getDuration());
            }
        }
        return result;
    }

    private static void add_entry(Queue<ChordDegree> context, ArrayList<Double> rhythmes,
                           HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> rhythmMatrices) {
        ArrayList<ChordDegree> key = new ArrayList<>(context);
        MarkovMatrix<Double> mat = rhythmMatrices.get(key);
        if (mat == null) {
            mat = new MarkovMatrix<>(1);
            for (Double entry : rhythmes)
                mat.addEntry(entry);
            mat.resetContext();
            rhythmMatrices.put(key, mat);
        }
        else {
            for (Double entry : rhythmes)
                mat.addEntry(entry);
            mat.resetContext();
        }
    }


}
