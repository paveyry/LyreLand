package analysis.harmonic;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import java.util.ArrayList;

//Note n = new Note(C4, CROTCHET, MF, PAN_CENTRE, CROTCHET * LEGATO);

public class ChordExtractor {

    public static void printAllResults(ArrayList<ArrayList<VerticalBand>> a) {
        for (ArrayList<VerticalBand> b: a)
            for (VerticalBand an :  b)
                an.printVerticalBand();
    }

    public static void printResultFromBeginning(ArrayList<ArrayList<VerticalBand>> a, int n) {
        for (ArrayList<VerticalBand> b: a)
            for (int j = b.size(); j > 0 && n > 0; j--) {
                b.get(j - 1).printVerticalBand();
                n--;
            }
    }

    public static void printResultFromEnd(ArrayList<ArrayList<VerticalBand>> a, int n) {
        for (ArrayList<VerticalBand> b: a)
            for (int j = 0; j < b.size() && n > 0; j++) {
                b.get(j).printVerticalBand();
                n--;
            }
    }
}
