package analysis.harmonic;

import analysis.ScoreAnalyser;

import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import org.junit.Test;

import java.util.ArrayList;

public class PlayTest {
    @Test
    public void playTest() {
        String midifile = tools.Misc.getJarPath() + "../assets/midi/dev_midi/prelude.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();
        Play.midi(init);
    }
}
