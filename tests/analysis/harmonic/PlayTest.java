package analysis.harmonic;

import analysis.ScoreAnalyser;

import analysis.bars.BarLexer;
import analysis.metadata.MetadataExtractor;
import analysis.phrase.Cadence;
import analysis.phrase.CadenceExtractor;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tonality.Tonality;

import java.lang.reflect.Array;
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
        BarLexer bal = sa.getBarLexer();
        sa.printScoreInfo();
        Tonality t = sa.getTonality();
        ArrayList<Tonality> ts = t.computeRelativesTonality();
        for (Tonality to : ts)
            System.out.println(to.toString());
        Tonality test = MetadataExtractor.getTonality(-1, 1);
        //Play.midi(init);
    }
}
