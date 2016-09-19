package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
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
    private String categoryName_;
    private MarkovDegree markovDegree_;
    private Score score_;

    public Generator(String categoryName) {
        categoryName_ = categoryName;
        getTrainedData();
        score_ = new Score();
    }

    /**
     * Fixme: This function is temporary and will be removed soon.
     */
    public void readHarmonicBase(Tonality t, int numberOfDegree) {
        Part part = new Part();
        CPhrase chords = new CPhrase();
        Harmonic harmonic = new Harmonic(t, markovDegree_);
        ArrayList<ChordDegree> base = harmonic.generateHarmonicBase(numberOfDegree);
        Rhythm rhythm = new Rhythm();
        for (ChordDegree chd : base)
            chords.addChord(harmonic.getChord(chd, 60), rhythm.getRhythm());
        part.addCPhrase(chords);
        score_.add(part);
        Play.midi(score_);
    }

    public MarkovDegree getMarkovDegree_() {
        return markovDegree_;
    }

    public GenreLearner getTrainedData() {
        XStream xstream = new XStream(new DomDriver());
        // For each category in data set, create the corresponding XML files in the trained data dir
        File dir = new File(ExecutionParameters.trainedDataPath.toString());
        File[] subDirs = dir.listFiles();
        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (subDir.isDirectory() && subDir.getName().equals(categoryName_)) {
                    Path dataFile = subDir.toPath().resolve("trained_data.xml");
                    GenreLearner genreLearner = (GenreLearner) xstream.fromXML(dataFile.toFile());
                    markovDegree_ = genreLearner.getMarkovDegree();
                }
            }
        }
        return null;
    }
}
