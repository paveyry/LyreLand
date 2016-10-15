package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Write;
import training.GenreLearner;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Generator {
    private GenreLearner learner_;
    private Score score_;

    public Generator(GenreLearner learner) {
        learner_ = learner;
        score_ = new Score();
    }

    /**
     * Fixme: This function is temporary and will be removed soon.
     */
    public void writeHarmonicBase(Tonality t, int numberOfDegree, String filename, long seed) {
        score_ = new Score();
        Part part = new Part();
        CPhrase chords = new CPhrase();
        Random generator = new Random(seed);
        Harmonic harmonic = new Harmonic(learner_.getTonalityVector().getValue(generator),
                                         learner_.getMarkovDegree(),
                                         learner_.getEndingsVector());
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

    // GETTERS / SETTERS

    /**
     * Getter for the Learner class attribute.
     * @return learner_
     */
    public GenreLearner getLearner() {
        return learner_;
    }

}
