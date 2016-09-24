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

import java.io.File;
import java.util.ArrayList;

public class Generator {
    private GenreLearner learner_;
    private Score score_;

    private MarkovDegree markovDegree_;
    private ProbabilityVector<Tonality> tonalityVector_;

    public Generator(GenreLearner learner) {
        learner_ = learner;
        score_ = new Score();

        markovDegree_ = learner.getMarkovDegree();
        tonalityVector_ = learner.getTonalityVector();
    }

    /**
     * Fixme: This function is temporary and will be removed soon.
     */
    public void writeHarmonicBase(int numberOfDegree, String filename, long seed) {
        score_ = new Score();
        Part part = new Part();
        CPhrase chords = new CPhrase();
        Harmonic harmonic = new Harmonic(tonalityVector_.getValue(), markovDegree_);
        ArrayList<ChordDegree> base = harmonic.generateHarmonicBase(numberOfDegree, seed);
        Rhythm rhythm = new Rhythm();
        for (ChordDegree chd : base)
            chords.addChord(harmonic.getChord(chd, 60), rhythm.getRhythm());
        part.addCPhrase(chords);
        score_.add(part);
        File f = new File(filename);
        f.delete();
        Write.midi(score_, filename);
    }

    public MarkovDegree getMarkovDegree() {
        return markovDegree_;
    }

    public GenreLearner getLearner() {
        return learner_;
    }

    public ProbabilityVector<Tonality> getTonalityVector() {
        return tonalityVector_;
    }
}
