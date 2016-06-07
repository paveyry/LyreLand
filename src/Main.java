import analysis.harmonic.ChordExtractor;
import analysis.harmonic.NormalizeRythm;
import analysis.ScoreAnalyser;
import analysis.harmonic.VerticalBand;
import jm.music.data.*;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import tonality.Scale;

import java.util.ArrayList;

public class Main {

    public static void demoScale(int tonic, String s, int octaveNumber, double rythm){
        Score score = new Score();
        Part p = new Part();
        Phrase phr = new Phrase();
        Scale scale = new Scale(tonic, s, octaveNumber);
        ArrayList<Integer> basicScale = scale.getScale();
        for (int i = 0; i < basicScale.size(); i++) {
            Note n = new Note(basicScale.get(i), rythm);
            System.out.println(basicScale.get(i));
            phr.add(n);
        }
        p.add(phr);
        score.add(p);
        Write.midi(score, "basic_scale.mid"); // create midiFile
        View.show(score);
        Play.mid("basic_scale.mid"); // Play the sound
    }

    public static void playTest(ArrayList<ArrayList<VerticalBand>> vb) {
        Score s = new Score();
        for (ArrayList<VerticalBand> av : vb) {
            Part p = new Part();
            CPhrase cph = new CPhrase();
            for (int j = av.size(); j > 0; j--) {
                cph.addChord(av.get(j-1).cI(av.get(j-1).getPitches()), av.get(j-1).getRythm());
            }
            p.addCPhrase(cph);
            s.add(p);
            Play.midi(p);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello LyreLand!");
        //demoScale(60, "major", 1, 0.5);

        ScoreAnalyser sa = new ScoreAnalyser(tools.Misc.getJarPath() + "../assets/midi/dev_midi/Guren_no_Yumiya.mid");
        //ScoreAnalyser sa = new ScoreAnalyser(tools.Misc.getJarPath() + "../assets/midi/dev_midi/Blue_Bird.mid");
        //ScoreAnalyser sa = new ScoreAnalyser(tools.Misc.getJarPath() + "../assets/midi/dev_midi/Hikaru_Nara.mid");
        //ScoreAnalyser sa = new ScoreAnalyser(tools.Misc.getJarPath() + "../assets/midi/dev_midi/This_Game.mid");
        //sa.getScale().showScale();
        // Print the normalised notes
        // use normaliseRythm() if you don't want to print
        NormalizeRythm.normaliseRythm(sa.getScore());
        NormalizeRythm.normalisePhraseLength(sa.getScore());
        //ChordExtractor.sequenceChords(sa.getScore());
        //sa.checknpl();
        //Write.midi(sa.getScore(), "foobar.mid");
        //Play.mid("foobar.mid");
        ArrayList<ArrayList<VerticalBand>> temp = ChordExtractor.sequenceChords(sa.getScore());
        ChordExtractor.printResultFromBeginning(temp, 100);
        playTest(temp);
    }
}
