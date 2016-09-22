package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Write;
import training.GenreLearner;
import training.probability.MarkovDegree;
import training.probability.ProbabilityVector;

import java.util.ArrayList;

public class Generator {
    private GenreLearner learner_;
    private Score score_;

    private MarkovDegree markovDegree_;
    private ProbabilityVector<String> tonalityVector_;

    public Generator(GenreLearner learner) {
        learner_ = learner;
        score_ = new Score();

        markovDegree_ = learner.getMarkovDegree();
        tonalityVector_ = learner.getTonalityVector();
    }

    /**
     * Fixme: This function is temporary and will be removed soon.
     */
    public void writeHarmonicBase(Tonality t, int numberOfDegree, String filename) {
        Part part = new Part();
        CPhrase chords = new CPhrase();
        Harmonic harmonic = new Harmonic(t, markovDegree_);
        ArrayList<ChordDegree> base = harmonic.generateHarmonicBase(numberOfDegree);
        Rhythm rhythm = new Rhythm();
        for (ChordDegree chd : base)
            chords.addChord(harmonic.getChord(chd, 60), rhythm.getRhythm());
        part.addCPhrase(chords);
        score_.add(part);
        Write.midi(score_, filename);
    }

    public MarkovDegree getMarkovDegree() {
        return markovDegree_;
    }

    public GenreLearner getLearner() {
        return learner_;
    }

    public ProbabilityVector<String> getTonalityVector() {
        return tonalityVector_;
    }
}
