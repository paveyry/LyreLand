package analysis.harmonic;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import org.junit.Test;
import tools.TypeConversion;

import java.util.ArrayList;

public class PlayTest {
    //@Test
    public void playTest() {
        String midifile = tools.Misc.getJarPath() + "../assets/midi/dev_midi/Guren_no_Yumiya.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        Part[] initPart = init.getPartArray();
        double tempo = init.getTempo();
        //Play.midi(init);
        ChordLexer nr = new ChordLexer(init);
        System.out.println("Shortest: " + init.getShortestRhythmValue());
        System.out.println("Normalized: " + nr.getQuantum());
        nr.normaliseRhythm();
        nr.normalisePhraseLength();
        ArrayList<ArrayList<VerticalBand>> vb = nr.sequenceChords();
        Score s = new Score();
        for (ArrayList<VerticalBand> av : vb) {
            Part p = new Part();
            CPhrase cph = new CPhrase();
            for (int j = av.size(); j > 0; j--) {
                cph.addChord(TypeConversion.convertIntegers(av.get(j-1).getPitches()), av.get(j-1).getRythm());
            }
            p.addCPhrase(cph);
            p.setTempo(tempo);
            Play.midi(p);
            //s.add(p);
        }
        //Play.midi(s.getPartArray()[1]);
    }
}
