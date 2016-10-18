package main;

import analysis.ScoreAnalyser;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import generation.GeneratedNote;
import generation.Generator;
import generation.Rhythm;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.MidiUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static tools.filemanagement.TrainedDataDeserializer.createGenerators;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void playTest() {
        /*String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();*/

        // 14235
        /*long seed = 14235;
        Random generator = new Random(seed);
        Generator g = createGenerators().get("baroque");
        ArrayList<ChordDegree> base = g.computeHarmonicBase(10, seed);
        System.out.println(base);
        int beatperbar = g.getLearner().getBeatPerBarVector().getValue(generator);
        double barUnit = g.getLearner().getBarUnitVector().getValue(generator);

        Rhythm rhythm = new Rhythm(base, g.getLearner().getRhythmMatrices_(), beatperbar * barUnit, generator);
        ArrayList<GeneratedNote> generatedNotes = rhythm.generateRhythms();
        System.out.println("BarUnit: " + beatperbar * barUnit);
        for (GeneratedNote gn : generatedNotes) {
            System.out.println(gn.getDegree() + " : " + gn.getRhythm());
        }*/

        /*GenreLearner l = g.getLearner();

        System.out.println("Tonalities\n" + l.getTonalityVector());
        System.out.println("Bar unit vector\n" + l.getBarUnitVector());
        System.out.println("Beat per bar\n" + l.getBeatPerBarVector());
        System.out.println("Bar Number\n" + l.getBarNumberVector());
        System.out.println("Tempo\n" + l.getTempoVector());

        System.out.println(l.getTonalityVector().getValue());*/

        //System.out.println(beat);
        //Tonality tonality = new Tonality(0, 0); // CMajor
        //gene.writeHarmonicBase(tonality, 12, getProjectPath() + "yolo.mid"); // tonalily, numberofdegree
        //Play.midi(init);
    }
}
