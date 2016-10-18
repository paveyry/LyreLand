package analysis.harmonic;

import analysis.ScoreAnalyser;
import jm.music.data.Score;
import jm.util.Read;
import org.junit.*;
import java.util.ArrayList;

public class ModulationTest {

    @Test
    public void modulationDetection1() {
        // initialisation
        String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/bach/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        // Use the BachPrelude Harmonic reduction.
        Tonality CMajor = new Tonality(0, 0);
        Tonality GMajor = new Tonality(1, 0);
        Tonality DMinor = new Tonality(-1, 1);
        DMinor.setMode_(Tonality.Mode.HARMONICMINOR);
        Tonality CMinor = new Tonality(-3, 1);
        CMinor.setMode_(Tonality.Mode.HARMONICMINOR);
        Tonality FMajor = new Tonality(-1, 0);
        Tonality GMinor = new Tonality(-2, 1);
        GMinor.setMode_(Tonality.Mode.HARMONICMINOR);
        ArrayList<Tonality> wantedResult = new ArrayList<>();
        for (int i = 0; i < 5; ++i)
            wantedResult.add(CMajor);
        for (int i = 0; i < 6; ++i)
            wantedResult.add(GMajor);
        for (int i = 0; i < 2; ++i)
            wantedResult.add(DMinor);
        wantedResult.add(CMinor);
        for (int i = 0; i < 5; ++i)
            wantedResult.add(CMajor);
        for (int i = 0; i < 2; ++i)
            wantedResult.add(FMajor);
        wantedResult.add(GMinor);
        for (int i = 0; i < 2; ++i)
            wantedResult.add(CMinor);
        for (int i = 0; i < 3; ++i)
            wantedResult.add(CMajor);
        wantedResult.add(GMinor);
        for (int i = 0; i < 3; ++i)
            wantedResult.add(CMajor);
        for (int i = 0; i < 2; ++i)
            wantedResult.add(FMajor);
        for (int i = 0; i < 2; ++i)
            wantedResult.add(CMajor);
        ModulationDetector m = new ModulationDetector(sa.getTonality(), sa.getBarLexer());
        ArrayList<Tonality> approximation = m.computeTonalities();
        //System.out.println(wantedResult);
        //System.out.println(approximation);
        double counter = 0.0;
        for (int i = 0; i < approximation.size(); ++i)
            if (wantedResult.get(i).equals(approximation.get(i)))
                ++counter;
        double value = (counter / approximation.size()) * 100;
        boolean result = value >= 85;
        //System.out.println(value);
        Assert.assertTrue(result);
    }
}
