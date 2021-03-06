package generation;

import analysis.harmonic.ChordDegree;
import training.probability.MarkovMatrix;

import java.util.*;

/**
 * Fixme : This class will later be use to generate real and complete bars
 * using the probabilities learnt from a given category ...
 * It is added now to highlight the future architecture of the generation of a Score.
 */
public class Rhythm {
    private ArrayList<ChordDegree> degrees_;
    private ArrayList<GeneratedNote> generatedNotes_;
    private HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> rhythmMatrices_;
    private double barRhythmValue_;
    private Random generator_;

    public Rhythm(ArrayList<ChordDegree> degrees, HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> rhythmMatrices,
                  double barRhythmValue, Random generator) {
        this.degrees_ = degrees;
        this.generatedNotes_ = new ArrayList<>();
        this.rhythmMatrices_ = rhythmMatrices;
        this.barRhythmValue_ = barRhythmValue;
        this.generator_ = generator;
    }


    public ArrayList<GeneratedNote> generateRhythms() {
        //rhythmMatrices_.forEach((a, b) -> {if (b.getTransitionMatrix().size() == 0) System.out.println("BITE: " + a);});
        ArrayList<ChordDegree> context = new ArrayList<>(Collections.nCopies(2, null));
        ChordDegree degree = degrees_.get(0);
        context.add(degree);

        for (int i = 1; i <= degrees_.size(); i++) {
            double rhythmslength = barRhythmValue_ / degree.getBarFractionDen();
            if (i < degrees_.size())
                degree = degrees_.get(i);
            else
                degree = null;
            context.remove(0);
            context.add(degree);
            //System.out.println("Matrices:");
            //System.out.println(context.get(0) + ", " + context.get(1) + ", " + context.get(2) + " : " + rhythmMatrices_.get(context));
            ArrayList<Double> rhythms = getRhythms(rhythmMatrices_.get(context), rhythmslength);
            fillGeneratedNotes(generatedNotes_, context.get(1), rhythms, i - 1);
        }
        return generatedNotes_;
    }

    private ArrayList<Double> getRhythms(MarkovMatrix<Double> rhythmMatrix, double rhythmslength) {
        double currentRhythmlength = 0;
        ArrayList<Double> rhythms = new ArrayList<>();
        ArrayList<Double> context = new ArrayList<>();
        context.add(null);
        int iter = 0;
        while (currentRhythmlength != rhythmslength) {
            Double newRhythm = 0.0;
            newRhythm = rhythmMatrix.getRandomValue(context, generator_);
            if (newRhythm == null) {
                rhythms.add(rhythmslength - currentRhythmlength);
                break;
            }
            context.remove(0);
            context.add(newRhythm);
            rhythms.add(newRhythm);
            currentRhythmlength += newRhythm;
            if (currentRhythmlength > rhythmslength) {
                if (iter > 1000) {
                    System.out.println("Bad fix");
                    newRhythm = rhythms.get(rhythms.size() - 1);
                    rhythms.remove(rhythms.size() - 1);
                    newRhythm = newRhythm - (currentRhythmlength - rhythmslength);
                    rhythms.add(newRhythm);
                    break;
                }
                else {
                    currentRhythmlength = 0;
                    rhythms.clear();
                    context.clear();
                    context.add(null);
                    iter++;
                }
            }
        }
        // FIXME: Reset the rhythmMatrix ?
        return rhythms;
    }

    private static void fillGeneratedNotes(ArrayList<GeneratedNote> generatedNotes, ChordDegree degree,
                                           ArrayList<Double> rhythms, int degree_nb) {
        rhythms.forEach(d -> generatedNotes.add(new GeneratedNote(degree, 1, d, degree_nb)));
    }
}
