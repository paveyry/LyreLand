package analysis.harmonic;

import analysis.ScoreAnalyser;

import jm.music.data.Score;
import jm.util.Read;
import options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = true;
    }

    @Test
    public void playTest() {
        String midifile = tools.Misc.getJarPath() + "../assets/midi/dev_midi/prelude.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();
        //Play.midi(init);
    }
}
