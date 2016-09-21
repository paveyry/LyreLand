package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import com.sun.tools.javac.jvm.Gen;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import main.options.ExecutionParameters;
import training.GenreLearner;
import training.MarkovChain.MarkovDegree;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Generator {
    private MarkovDegree markovDegree_;
    private Score score_;
    private GenreLearner learner_;

    public Generator(GenreLearner learner) {
        learner_ = learner;
        markovDegree_ = learner.getMarkovDegree();
        score_ = new Score();
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
}
