package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
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

    public void generate(String fileName, int barNumber, long seed) {
        ArrayList<ChordDegree> harmonicBase = computeHarmonicBase(barNumber, seed);
        ArrayList<GeneratedNote> melody = computeRhythm(harmonicBase, seed);

        /* -------------------------------------------------
          |  Call function to add pitches to the melody HERE |
           ------------------------------------------------- */

        score_ = new Score();
        Part part = new Part();
        CPhrase chords = new CPhrase();
        // Jmusic take int[] and not ArrayList<Integer> for the
        for (GeneratedNote gn : melody) {
            // Only in Java 8
            int[] pitches = gn.getChordPitches().stream().mapToInt(i -> i).toArray();
            chords.addChord(pitches, gn.getRhythm());
        }
        part.addCPhrase(chords);
        score_.add(part);
        // Code to play the file
        //Play.midi(score_);
        // Code to write the file
        /*Write.midi(score_, fileName);
        Read.midi(score_);*/
    }

    /**
     * This function compute the Harmonic Base of the generated music.
     * @param barNumber
     * @param seed
     * @return ArrayList<ChordDegree>
     */
    private ArrayList<ChordDegree> computeHarmonicBase(int barNumber, long seed) {
        Random generator = new Random(seed);
        Harmonic harmonic = new Harmonic(learner_.getTonalityVector().getValue(generator), learner_.getMarkovDegree(),
                                                                                           learner_.getEndingsVector());
        return harmonic.generateHarmonicBase(barNumber, seed);
    }

    /**
     * This function generate an ArrayList<GeneratedNote> and fill the rythm of these
     * GeneratedNote based on the Harmonic Base of the future score.
     * @param base
     * @param seed
     * @return
     */
    private ArrayList<GeneratedNote> computeRhythm(ArrayList<ChordDegree> base, long seed) {
        Random generator = new Random(seed);
        int beatperbar = learner_.getBeatPerBarVector().getValue(generator);
        double barUnit = learner_.getBarUnitVector().getValue(generator);
        Rhythm rhythm = new Rhythm(base, learner_.getRhythmMatrices_(), beatperbar * barUnit, generator);
        return rhythm.generateRhythms();
    }

    /**
     * Fixme: This function must Disappear soon Call Generate instead.
     */
    public void writeHarmonicBase(Tonality t, int numberOfDegree, String filename, long seed) {
        score_ = new Score();
        Part part = new Part();
        Random generator = new Random(seed);
        int beatperbar = learner_.getBeatPerBarVector().getValue(generator);
        double barUnit = learner_.getBarUnitVector().getValue(generator);
        //CPhrase chords = new CPhrase();
        Harmonic harmonic = new Harmonic(learner_.getTonalityVector().getValue(generator),
                                         learner_.getMarkovDegree(),
                                         learner_.getEndingsVector());
        ArrayList<ChordDegree> base = harmonic.generateHarmonicBase(numberOfDegree, seed);

        Rhythm rhythm = new Rhythm(base, learner_.getRhythmMatrices_(), beatperbar * barUnit, generator);
        ArrayList<GeneratedNote> generatedNotes = rhythm.generateRhythms();

        System.out.println("BarUnit: " + beatperbar * barUnit);
        for (GeneratedNote gn : generatedNotes) {
            System.out.println(gn.getDegree() + " : " + gn.getRhythm());
        }

        /*
        Rhythm rhythm = new Rhythm();
        for (ChordDegree chd : base)
            chords.addChord(harmonic.getChord(chd, 60), rhythm.getRhythm());
        part.addCPhrase(chords);
        score_.add(part);
        File f = new File(filename);
        f.delete();
        Write.midi(score_, filename);
        */
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
