package analysis.metadata;

import jm.music.data.Score;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;

public class MetadataExtractor {
    /**
     * Extract the bar unit using score_.get(Denominator)
     * EG: 4 -> 1.0 | 2 -> 2.0 | 8 -> 0.5 | 1 -> 4.0
     * @param denominator Lower number of the time signature
     * @return Bar unit
     */
    public static double computeBarUnit(int denominator) {
        switch (denominator) {
            case 1: return 4.0;
            case 2: return 2.0;
            case 4: return 1.0;
            case 8: return 0.5;
        }
        return 1.0;
    }

    /**
     * Return the tonality of the score following format :
     * Tonic Alteration(sharp, flat or none) Mode --> E.g A sharp Major
     * @param keySignature Number of sharps or flats at key
     * @param keyQuality Major or Minor
     * @return Tonality of the song
     */
    public static Tonality getTonality(int keySignature, int keyQuality) {
        return new Tonality(keySignature, keyQuality);
    }

    /**
     * Determine Scale for a specified tonality
     * @param tonality Specified tonality
     * @return Scale
     */
    public static Scale computeScale(Tonality tonality) {
        Integer tonic = tonality.getTonic();
        Tonality.Mode mode = tonality.getMode();
        return new Scale(tonic, mode, 1);
    }

    /**
     * Determine the quantum unit of a given score
     * @param score The score whose quantum need to be found
     * @return quantum
     */
    public static double findQuantum(Score score) {
        double srm = score.getShortestRhythmValue();
        double quantum = 0.0;
        if (srm < (0.0625 - 0.03125 / 2))
            quantum = 0.03125;
        else if (srm < (0.125 - 0.0625 / 2))
            quantum = 0.0625;
        else if (srm < (0.250 - 0.125 / 2))
            quantum =  0.125;
        else if (srm < (0.500 - 0.250 / 2))
            quantum = 0.250;
        else if (srm < (1 - 0.5 / 2))
            quantum =  0.500;
        else if (srm < (2.0 - 1.0 / 2.0))
            quantum = 1.00;
        else if (srm < (4.0 - 2.0 / 2.0))
            quantum = 2.00;
        else if (srm < (8.0 - 4.0 / 2.0))
            quantum = 4.00;
        else
            quantum = 8.00;
        return quantum;
    }
}
