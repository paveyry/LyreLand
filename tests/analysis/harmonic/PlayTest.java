package analysis.harmonic;

import analysis.ScoreAnalyser;

import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import org.junit.Test;

public class PlayTest {
    //@Test
    public void playTest() {
        String midifile = tools.Misc.getJarPath() + "../assets/midi/dev_midi/Hikaru_Nara.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        //double tempo = init.getTempo();
        //Play.midi(init);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        ChordLexer nr = new ChordLexer(sa.getScore(), sa.getBarUnit(), sa.getBeatsPerBar(), sa.getTonality());
        System.out.println("Shortest: " + init.getShortestRhythmValue());
        //System.out.println("Normalized: " + nr.getQuantum());
        //nr.normaliseRhythm();
        //nr.normalisePhraseLength();
        //Play.midi(nr.getScore());
    }
}
