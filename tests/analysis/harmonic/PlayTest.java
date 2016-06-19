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
        //Score init = new Score();
        //Read.midi(init, midifile);
        //double tempo = init.getTempo();
        //Play.midi(init);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.processDegreeList();
        System.out.println("BeatPerBar : " + sa.getBeatsPerBar());
        System.out.println("Tonality : " + sa.getTonality().toString());
        System.out.println(sa.getDegreeList().size());
        System.out.println(sa.getDegreeList().size());
        for (ChordDegree chd : sa.getDegreeList()) {
            System.out.print(chd.toString() + " ");
        }
        System.out.println("\n");
        //ChordLexer nr = new ChordLexer(sa.getScore(), sa.getBarUnit(), sa.getBeatsPerBar(), sa.getTonality());
        //System.out.println("Shortest: " + init.getShortestRhythmValue());
        //System.out.println("Normalized: " + nr.getQuantum());
        //nr.normaliseRhythm();
        //nr.normalisePhraseLength();
        //Play.midi(nr.getScore());
    }
}
