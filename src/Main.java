import Analysis.ScoreAnalyser;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
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

    public static void main(String[] args) {
        System.out.println("Hello LyreLand!");
        //demoScale(60, "major", 1, 0.5);

        ScoreAnalyser sa = new ScoreAnalyser("/home/olivier/Documents/LyreLand/Analysis_Music/Blue_Bird/Blue_Bird.mid");
        sa.printScoreBasicInformations();
        sa.getScale().showScale();
    }
}
