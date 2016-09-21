package main;

import analysis.harmonic.Tonality;
import generation.Generator;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Generator gene = createGenerators().get("baroque");
        Tonality tonality = new Tonality(0, 0); // CMajor
        gene.writeHarmonicBase(tonality, 12, getProjectPath() + "yolo.mid"); // tonalily, numberofdegree
        //Play.midi(init);
    }
}
