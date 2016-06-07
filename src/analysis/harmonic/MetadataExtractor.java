package analysis.harmonic;

import analysis.containers.CircularArrayList;
import tonality.Scale;

import java.util.Arrays;
import java.util.List;

import static jm.constants.Pitches.*;

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
    public static String getTonality(int keySignature, int keyQuality) {
        if (keySignature == 0) {
            return (keyQuality == 0)? "C Major":"A minor";
        }
        else {
            boolean isSharp = (keySignature > 0) ? true : false;
            String mode = (keyQuality == 0) ? "Major" : "Minor";
            String cle = (isSharp)?"#":"f";
            String tonic;
            CircularArrayList<String> order = new CircularArrayList<String>(); // Sharp or flat order
            if (isSharp) {
                List<String> sharpList = Arrays.asList(new String[]{"F", "C", "G", "D", "A", "E", "B"});
                order.addAll(sharpList);
                if (mode.equals("Major"))
                    tonic = order.get(keySignature + 1);
                else
                    tonic = order.get(keySignature - 3);
            } else {
                List<String> flatList = Arrays.asList(new String[]{"B", "E", "A", "D", "G", "C", "F"});
                order.addAll(flatList);
                if (mode.equals("Major"))
                    tonic = order.get((keySignature * -1) - 2);
                else
                    tonic = order.get((keySignature * -1) - 3);
            }
            return tonic + " " + cle + " " + mode;
        }
    }

    // This function use _tonality to create the corresponding Scale object
    public static Scale computeScale(String tonality) {
        String[] splited = tonality.split(" ");
        int tonic = 0;
        switch (splited[0]) {
            case "A": tonic = A4;
                break;
            case "B": tonic = B4;
                break;
            case "C": tonic = C4;
                break;
            case "D": tonic = D4;
                break;
            case "E": tonic = E4;
                break;
            case "F": tonic = F4;
                break;
            case "G": tonic = G4;
                break;
        }
        // Case C Major || A Minor
        if (splited.length == 2)
            return (new Scale(tonic, splited[1], 1));
        else {
            if (splited[2].equals("Minor")) {
                int val = (splited[1].equals("#")) ? 1 : -1;
                tonic += val;
                return (new Scale(tonic, splited[2], 1));
            }
            return (new Scale(tonic, splited[2], 1));
        }
    }
}
