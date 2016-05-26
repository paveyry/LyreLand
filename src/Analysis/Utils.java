package Analysis;

/**
 * Created by olivier on 26/05/16.
 */
public class Utils {
    public static double normalizeRythmValue(double r) {
        // truncate three decimals and convert to int.
        int result = ((int)(r * 1000));
        // Rounded to the superior 250 multiple.
        if (result % 250 != 0)
            System.out.println(result / 250);
            result = ((result / 250) + 1) * 250;
        return (double)result / 1000;
    }
}
