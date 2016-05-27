package Analysis;

import jm.music.data.Note;

import java.util.ArrayList;

/**
 * Created by olivier on 26/05/16.
 */
public class Utils {

    private static double r = 0;
    private static double time = 0.0625;

    // Normalize a double rythm value according to
    // midi rythm reading.
    // E.G 1.251 -> 1.5.
    public static void normalizeRythmValue(Note n) {
        // truncate three decimals and convert to int.
        r += n.getRhythmValue();
        int result = ((int)(r * 10000));
        // Rounded to the superior 250 multiple.
        int mod = ((int)(time * 10000));
        int rest = result % mod;
        if (rest != 0) {
            rest = rest > mod / 2 ? mod - rest : - rest;
            result = result + rest;
        }
        n.setRhythmValue((double) result / 10000);
        r =  (double) - rest / 10000;
    }
}
