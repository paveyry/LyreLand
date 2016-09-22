package main;

import analysis.ScoreAnalyser;
import analysis.harmonic.Tonality;
import generation.Generator;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
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
        /*String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();*/
        /*Generator gene = createGenerators().get("anime_ost");
        ProbabilityVector<String> lol = gene.getTonalityVector();
        System.out.println(lol.toString());
        System.out.println(lol.getValue().toString());*/
        //Tonality tonality = new Tonality(0, 0); // CMajor
        //gene.writeHarmonicBase(tonality, 12, getProjectPath() + "yolo.mid"); // tonalily, numberofdegree
        //Play.midi(init);
    }
}
