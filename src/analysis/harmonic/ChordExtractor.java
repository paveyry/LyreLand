package analysis.harmonic;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import java.util.ArrayList;

//Note n = new Note(C4, CROTCHET, MF, PAN_CENTRE, CROTCHET * LEGATO);

public class ChordExtractor {

    // Fixme: need to not add same pitch in a single VerticalBand.
    // (An ArrayList<VerticalBand> per Parts.)
    public static ArrayList<ArrayList<VerticalBand>> sequenceChords(Score score) {
        ArrayList<ArrayList<VerticalBand>> result = new ArrayList<>();
        for (Part p : score.getPartArray()) {
            ArrayList<VerticalBand> vBand = new ArrayList<>();
            int max = 0;
            // Get the maximum length of the partArray phrases.
            for (Phrase phr : p.getPhraseArray()) {
                max = (phr.length() > max) ? phr.length() : max;
            }
            for (int i = 0; i < max; i++) {
                ArrayList<Integer> pitches = new ArrayList<>();
                double rythm = NormalizeRhythm.getQuantum();
                ArrayList<Integer> dynamic = new ArrayList<>();
                ArrayList<Double> duration = new ArrayList<>();
                for (Phrase phr: p.getPhraseArray()) {
                    int index = phr.length() - 1 - i;
                    // Phrases don't have the same lenght
                    if (index >= 0) {
                        if (phr.getNote(phr.length() - 1 - i).getPitch() != Note.REST &&
                                !pitches.contains(phr.getNote(phr.length() - 1 - i).getPitch())) {
                            pitches.add(phr.getNote(phr.length() - 1 - i).getPitch());
                            dynamic.add(phr.getNote(phr.length() - 1 - i).getDynamic());
                            duration.add(phr.getNote(phr.length() - 1 - i).getDuration());
                        }
                    }
                }
                VerticalBand temp = new VerticalBand(pitches, rythm, duration, dynamic);
                if (vBand.size() != 0 && (vBand.get(vBand.size()-1).equals(temp)))
                    vBand.get(vBand.size()-1).setRythm(vBand.get(vBand.size()-1).getRythm() + rythm);
                else
                    // To not add empty VerticalBand
                    if (temp.getPitches().size() > 0)
                        vBand.add(temp);
            }
            result.add(vBand);
        }
        return result;
    }

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
