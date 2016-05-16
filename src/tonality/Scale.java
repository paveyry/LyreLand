package tonality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olivier on 16/05/16.
 */
public class Scale {
    private int _tonic;
    private String _type; // Minor or Major ?
    private List<Integer> _notes;

    // ---------- Constructors ----------
    public Scale(int tonic, String s, int octaveNumber) {
        _tonic = tonic;
        _type = s;
        setScale(octaveNumber);
    }

    private List<Integer> createScale(int tonic, String s, int octaveNumber) {
        // We have to check what we ask don't exceed G9 = 127 (highest pitch)
        if (tonic + (octaveNumber * 12) <= 127) {
            ArrayList<Integer> notes = new ArrayList<Integer>();
            for (int i = 0; i < octaveNumber; i++) {
                int[] temp = null;
                switch (s) {
                    case "major":
                        temp = new int[] {tonic, tonic + 2, tonic + 4, tonic + 5, tonic + 7, tonic + 9, tonic + 11};
                    case "minorn": // natural Minor Scale without alterations
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 10};
                    case "minorh": // Harmonic Minor Scale
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 11};
                    case "minorm": // Melodic Minor Scale
                        temp = new int[] {tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 9, tonic + 11};
                    default: break;
                }
                for (int j = 0; j < temp.length; j++)
                    notes.add(temp[j]);
                tonic += 12;
            }
            notes.add(tonic);
            return notes;
        }
        return null;
    }

    // ---------- Getters ----------
    public int getDegree(int degree) {
        if (_notes != null)
            return _notes.get(degree);
        return 0;
    }

    private List<Integer> getScale(){
        return _notes;
    }

    // ---------- Setters ----------
    public void setScale (int octaveNumber) {
        _notes = createScale(_tonic, _type, octaveNumber);
    }

}
