package main;

import analysis.ScoreAnalyser;
import analysis.harmonic.Tonality;
import generation.Generator;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import training.GenreLearner;
import training.probability.MarkovDegree;
import training.probability.ProbabilityVector;

import static tools.Misc.getProjectPath;
import static tools.filemanagement.TrainedDataDeserializer.createGenerators;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void playTest() {
        String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();

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
