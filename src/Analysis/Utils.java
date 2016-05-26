package Analysis;

import jm.music.data.Note;

import java.util.ArrayList;

/**
 * Created by olivier on 26/05/16.
 */
public class Utils {
    // Normalize a double rythm value according to
    // midi rythm reading.
    // E.G 1.251 -> 1.5.
    public static double normalizeRythmValue(double r) {
        // truncate three decimals and convert to int.
        int result = ((int)(r * 1000));
        // Rounded to the superior 250 multiple.
        if (result % 250 != 0)
            result = ((result / 250) + 1) * 250;
        return (double)result / 1000;
    }

    // Counteract the midi rythm staking effect of two following
    // similar notes : (C4, 4.0) and (C4, 2.0) will be transformed
    // into one note (C4, 6.0).
    // We want to redivide the notes according to bar beat value.
    public static ArrayList<Note> normalizeNoteLength(Note n, int beatPerBar) {
        double rythm = normalizeRythmValue(n.getRhythmValue());
        ArrayList<Note> notes = new ArrayList<Note>();
        while (rythm > beatPerBar) {
            Note temp = n.copy();
            temp.setRhythmValue((double)beatPerBar);
            notes.add(temp);
            rythm -= beatPerBar;
        }
        Note temp = n.copy();
        temp.setRhythmValue(rythm);
        notes.add(temp);
        return notes;
    }
}
