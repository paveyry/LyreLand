package tonality;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;

import java.util.ArrayList;

/**
 * Created by olivier on 16/05/16.
 */
public class Scale {
    private int _tonic;
    private String _type; // Minor or Major ?
    private ArrayList<Integer> _notes;

    // ---------- Constructors ----------
    public Scale(int tonic, String s, int octaveNumber) {
        _tonic = tonic;
        _type = s;
        setScale(octaveNumber);
    }

    // Private functions.

    private ArrayList<Integer> createScale(int tonic, String s, int octaveNumber) {
        // We have to check what we ask don't exceed G9 = 127 (highest pitch)
        if (tonic + (octaveNumber * 12) <= 127) {
            ArrayList<Integer> notes = new ArrayList<Integer>();
            for (int i = 0; i < octaveNumber; i++) {
                int[] temp = null;
                switch (s) {
                    case "Major":
                        temp = new int[] {tonic, tonic + 2, tonic + 4, tonic + 5, tonic + 7, tonic + 9, tonic + 11};
                        break;
                    case "Minor": // natural Minor Scale without alterations
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 10};
                        break;
                    case "Minorh": // Harmonic Minor Scale
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 11};
                        break;
                    case "Minorm": // Melodic Minor Scale
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 9, tonic + 11};
                        break;
                    default: break;
                }
                for (int j = 0; j < temp.length; j++)
                    notes.add(temp[j]);
                tonic += 12;
            }
            return notes;
        }
        return null;
    }

    // Public functions
    public boolean isInScale(int pitch) {
        boolean result = false;
        for (int i = 0; i < _notes.size() && !result; i++) {
            result = (_notes.get(i) % 12 == pitch % 12);
        }
        return result;
    }

    // Convert the array pitch composing the scale into
    // a string witch can latter be written in a file
    public String pitchToString() {
        String result = "[";
        for (int i = 0; i < _notes.size(); i++) {
            if (i < _notes.size() - 1)
                result += _notes.get(i) + ", ";
            else
                result += _notes.get(i) + "]";
        }
        return result;
    }

    // Show the scale on GUI FIXME: need to repair Notate
    public void showScale() {
        Score score = new Score();
        Part p = new Part();
        Phrase phr = new Phrase();
        System.out.print("Scale pitch values : ");
        for (int i = 0; i < _notes.size(); i++) {
            Note n = new Note(_notes.get(i), 1.0);
            System.out.print(_notes.get(i) + " ");
            phr.add(n);
        }
        System.out.println();
        p.add(phr);
        score.add(p);
        View.show(score);
    }

    // ---------- Getters ----------
    public int getDegree(int degree) {
        if (_notes != null && degree <= 6)
            return _notes.get(degree);
        return 0;
    }

    public ArrayList<Integer> getScale(){
        return _notes;
    }

    // ---------- Setters ----------
    public void setScale (int octaveNumber) {
        _notes = createScale(_tonic, _type, octaveNumber);
    }

}
