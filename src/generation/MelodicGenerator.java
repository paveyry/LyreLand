package generation;

import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import training.MelodicLearner;
import training.probability.MarkovMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MelodicGenerator {
    private Random generator_;
    private Scale scale_;
    private MelodicLearner melodicLearner_;

    public MelodicGenerator(Random generator, Tonality tonality, MelodicLearner melodicLearner) {
        generator_ = generator;
        scale_ = new Scale(tonality, 12);
        melodicLearner_ = melodicLearner;
    }

    public void fillWithPitches(ArrayList<GeneratedNote> rhythmicSequence) {
        for (GeneratedNote gn : rhythmicSequence) {
            Integer context = null;
            ArrayList<Integer> chord = new ArrayList<Integer>();
            Integer note = null;
            for (int i = 0; i < gn.getChordSize(); ++i) {
                MarkovMatrix<Integer> markovChain = melodicLearner_.getMarkovMatrices().get(gn.getDegree());
                Integer noteIndex = 2000;
                while (noteIndex >= 129)
                    noteIndex = markovChain.getRandomValue(Arrays.asList(context), generator_);
                note = scale_.getScale().get(noteIndex);
                chord.add(note);
            }
            gn.setChordPitches(chord);
            context = note;
        }
    }
}
