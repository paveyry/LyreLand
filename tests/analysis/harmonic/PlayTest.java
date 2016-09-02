package analysis.harmonic;

import analysis.ScoreAnalyser;

import analysis.bars.BarLexer;
import analysis.metadata.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import tonality.Tonality;

import java.util.ArrayList;

import static jm.constants.Pitches.*;
import static tonality.Tonality.Mode.MAJOR;
import static tonality.Tonality.Mode.MINOR;

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
        BarLexer bal = sa.getBarLexer();
        sa.printScoreInfo();
        Tonality t = sa.getTonality();
        ArrayList<Tonality> ts = t.computeRelativeTonalities(init.getKeySignature(), init.getKeyQuality());
        for (Tonality to : ts)
            System.out.println(to.toString());
        Tonality test = MetadataExtractor.getTonality(-1, 1);*/
        
        Tonality t1 = new Tonality(F4, MINOR, 0);
        Tonality t2 = new Tonality(-4, 1);
        System.out.println(t1.toString() + "        " + t2.toString());
        System.out.println("Tonality 1 : " + "        ||     Tonality 2 : ");
        System.out.println("Tonic : " + t1.getTonic() % 12 + "        ||     " + t2.getTonic() % 12);
        System.out.println("Mode : " + t1.getMode() + "        ||      " + t2.getMode());
        System.out.println("alteration ? : " + t1.getAlteration() + "        ||     " + t2.getAlteration());
        //Play.midi(init);
    }
}
