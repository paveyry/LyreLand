package analysis.harmonic;

import analysis.containers.CircularArrayList;
import tonality.Scale;
import tonality.Tonality;
import static jm.constants.Pitches.*;

import java.util.Arrays;

public class MetadataExtractor {

    // This function extract the bar unit according to _score.get(Denominator)
    // EG: 4 -> 1.0 | 2 -> 2.0 | 8 -> 0.5 | 1 -> 4.0
    public static double computeBarUnit(int denominator) {
        switch (denominator) {
            case 1: return 4.0;
            case 2: return 2.0;
            case 4: return 1.0;
            case 8: return 0.5;
        }
        return 1.0;
    }

    // Return the tonality of the score following format :
    // Tonic Alteration(sharp, flat or none) Mode --> E.g A sharp Major
    public static Tonality getTonality(int keySignature, int keyQuality) {
        if (keySignature == 0)
            return (keyQuality == 0)
                    ? new Tonality(C0, Tonality.Mode.MAJOR, false)
                    : new Tonality(A0, Tonality.Mode.MINOR, false);

        boolean keySignatureIsSharp = keySignature > 0;
        Tonality.Mode mode = (keyQuality == 0) ? Tonality.Mode.MAJOR : Tonality.Mode.MINOR;
        Integer tonic;

        boolean isSharp = false;

        CircularArrayList<Integer> order = new CircularArrayList<>(); // Sharp or flat order
        if (keySignatureIsSharp) {
            order.addAll(Arrays.asList(F4, C4, G4, D4, A4, E4, B4));
            if (mode == Tonality.Mode.MAJOR)
                tonic = order.get(keySignature + 1);
            else
                tonic = order.get(keySignature - 3);

            for (int i = 0; i < keySignature; ++i)
                if (order.get(i) % 12 == tonic % 12) {
                    ++tonic;
                    isSharp = true;
                }
        }
        else {
            order.addAll(Arrays.asList(B4, E4, A4, D4, G4, C4, F4));
            if (mode == Tonality.Mode.MAJOR)
                tonic = order.get((keySignature * -1) - 2);
            else
                tonic = order.get((keySignature * -1) - 3);

            for (int i = 0; i < keySignature; ++i)
                if (order.get(i) % 12 == tonic % 12)
                    --tonic;
        }

        return new Tonality(tonic, mode, isSharp);
    }

    // This function use _tonality to create the corresponding Scale object
    public static Scale computeScale(Tonality tonality) {
        Integer tonic = tonality.getTonic();
        Tonality.Mode mode = tonality.getMode();
        return (new Scale(tonic, mode, 1));
    }
}
