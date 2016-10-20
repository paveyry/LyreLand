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
        Random generator = new Random(seed);
        Tonality tonality = learner_.getTonalityVector().getValue(generator);

        Harmonic harmonic = new Harmonic(tonality, learner_.getMarkovDegree(), learner_.getEndingsVector());
        ArrayList<ChordDegree> harmonicBase = harmonic.generateHarmonicBase(barNumber, generator);
        System.out.println(harmonicBase);

        int beatperbar = learner_.getBeatPerBarVector().getValue(generator);
        double barUnit = learner_.getBarUnitVector().getValue(generator);
        Rhythm rhythm = new Rhythm(harmonicBase, learner_.getRhythmMatrices_(), beatperbar * barUnit, generator);
        ArrayList<GeneratedNote> melody = rhythm.generateRhythms();


        MelodicGenerator melodicGenerator = new MelodicGenerator(new Random(seed), tonality, learner_.getMelodicLearner());
        melodicGenerator.fillWithPitches(melody);

        score_ = new Score();
        Part part = new Part();
        CPhrase chords = new CPhrase();
        // Jmusic take int[] and not ArrayList<Integer> for the
        for (GeneratedNote gn : melody) {
            // Only in Java 8
            int[] pitches = gn.getChordPitches().stream().mapToInt(i -> i).toArray();
            for (int i = 0; i < pitches.length; ++i)
                if (pitches[i] > 125)
                    pitches[i] = pitches[i] % 12 + 12 * 4;
            chords.addChord(pitches, gn.getRhythm());
        }
        CPhrase degrees = new CPhrase();
        for (ChordDegree degree : harmonicBase) {
            System.out.println(degree);
            degrees.addChord(harmonic.getChord(degree,12),
                    (double) (beatperbar * barUnit) / (double)degree.getBarFractionDen());
        }
        part.addCPhrase(chords);
        score_.add(part);
        Part acc = new Part();
        acc.addCPhrase(degrees);
        score_.add(acc);
        // Code to play the file
        //Play.midi(score_);
        // Code to write the file
        Write.midi(score_, fileName);
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
        ArrayList<ChordDegree> base = harmonic.generateHarmonicBase(numberOfDegree, generator);

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
