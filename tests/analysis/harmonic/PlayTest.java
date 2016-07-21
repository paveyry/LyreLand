package analysis.harmonic;

import analysis.ScoreAnalyser;

import analysis.bars.BarLexer;
import analysis.phrase.Cadence;
import analysis.phrase.CadenceExtractor;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

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
        /*CadenceExtractor lol = new CadenceExtractor(bal, sa.getDegreeList());
        ArrayList<Cadence> test = lol.extractCadences();
        System.out.println(test.size());
        for (Cadence cadence : test)
            System.out.println(cadence.toString());*/
        //Play.midi(init);
    }
}
