package Analysis;

import jm.music.data.Note;


/**
 * Created by olivier on 26/05/16.
 */
public class Utils {

    private static double r = 0;

    // Normalize a double rythm value according to
    // midi rythm reading.
    // E.G 1.251 -> 1.5.
    public static void normalizeRythmValue(Note n) {
        // truncate three decimals and convert to int.
        r += n.getRhythmValue();
        int result = ((int)(r * 1000));
        // Rounded to the superior 250 multiple.
        int mod = 250;
        int rest = result % mod;
        if (rest != 0) {
            rest = rest > mod / 2 ? mod - rest : - rest;
            result = result + rest;
        }
        n.setRhythmValue((double) result / 1000);
        r =  (double) - rest / 1000;
    }

    // TODO
    // Counteract the midi rythm staking effect of two following
    // similar notes : (C4, 4.0) and (C4, 2.0) will be transformed
    // into one note (C4, 6.0).
    // We want to redivide the notes according to bar beat value.
}
