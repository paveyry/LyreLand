package main;

import analysis.ScoreAnalyser;

import analysis.bars.BarLexer;
import analysis.metadata.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tonality.Tonality;

import java.util.ArrayList;


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
        //Play.midi(init);
    }
}
