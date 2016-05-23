package Analysis;

import java.util.Arrays;
import java.util.List;

/**
 * Created by olivier on 23/05/16.
 */
public class Utils {

    // This function take Score.keySignature and Score.keyQuality in parameter
    // and returns the tonality of the Score with the following syntax :
    // A # Major or A f Minor.
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
}
