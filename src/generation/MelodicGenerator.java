package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import training.MelodicLearner;
import training.probability.MarkovMatrix;

import java.util.*;

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
        ArrayList<ChordDegree> context = new ArrayList<>(Collections.nCopies(1, null));
        // Init the context with the first degree
        context.add(rhythmicSequence.get(0).getDegree());
        // Init the context with the second degree, yes it is awfull
        for (GeneratedNote gn : rhythmicSequence) {
            if (gn.getDegree_nb_() != 0) {
                context.add(gn.getDegree());
                break;
            }
        }
        int ind = 1;
        for (GeneratedNote gn : rhythmicSequence) {
            Integer context_note = null;
            if (gn.getDegree_nb_() != ind && gn.getDegree_nb_() > 1) {
                ind++;
                context.remove(0);
                context.add(gn.getDegree());
            }
            Integer note = null;
            ArrayList<Integer> chord = new ArrayList<>();
            for (int i = 0; i < gn.getChordSize(); ++i) {
                MarkovMatrix<Integer> markovChain = melodicLearner_.getMarkovMatrices().get(context);
                Integer noteIndex = 2000;
                while (noteIndex == null || noteIndex >= 129)
                    noteIndex = markovChain.getRandomValue(Arrays.asList(context_note), generator_);
                note = scale_.getScale().get(noteIndex);
                chord.add(note);
            }
            gn.setChordPitches(chord);
            context_note = note;
        }
    }
}
