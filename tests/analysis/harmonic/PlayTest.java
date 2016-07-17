package analysis.harmonic;

import analysis.ScoreAnalyser;

import analysis.bars.Bar;
import analysis.bars.BarLexer;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
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
        BarLexer bal = sa.getBarLexer();
        sa.printScoreInfo();
        //Play.midi(init);
    }
}
