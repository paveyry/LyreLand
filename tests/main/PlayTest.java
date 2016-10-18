package main;

import analysis.ScoreAnalyser;
<<<<<<< HEAD
import analysis.harmonic.ModulationDetector;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
=======
import analysis.harmonic.Tonality;
import generation.Generator;
>>>>>>> generator: rhythm: add generator, it will work once the bar and degrees are correct
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.MidiUpdate;

import java.util.ArrayList;
import java.util.Arrays;

import static tools.filemanagement.TrainedDataDeserializer.createGenerators;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void playTest() {
        /*
        String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();

        Tonality t = new Tonality(0,0);
        long lol = 123456789;
        Generator g = createGenerators().get("anime_ost");
        g.writeHarmonicBase(t, 12, "toto", lol);

        GenreLearner l = g.getLearner();

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
