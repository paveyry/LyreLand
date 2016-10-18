package main;

import analysis.ScoreAnalyser;
import analysis.harmonic.ModulationDetector;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.MidiUpdate;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void playTest() {
        //MidiUpdate.UpdateMidiTonality("../assets/midi/baroque/bach/bwv774.mid", 2, 0);
        /*String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/bach/bwv772.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();


        ModulationDetector m = new ModulationDetector(sa.getTonality(), sa.getBarLexer());
        ArrayList<Tonality> results = m.computeTonalities();
        System.out.println(results);*/

        /*Generator g = createGenerators().get("anime_ost");

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
