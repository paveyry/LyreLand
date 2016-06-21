package analysis.harmonic;

import analysis.containers.CircularArrayList;
import jm.constants.Pitches;
import tonality.Scale;
import tonality.Tonality;

import java.util.ArrayList;

public class ChordDegreeProcessor {
    private Tonality tonality_;
    private CircularArrayList<Integer> scale_;
    private ArrayList<Integer[]> chords_;

    /**
     * Constructor for the ChordDegreeProcessor class. Defines chords and scales for specified tonality
     * @param tonality Tonality used for the chord degree analysis
     */
    public ChordDegreeProcessor(Tonality tonality) {
        this.tonality_ = tonality;
        this.scale_ = new CircularArrayList<>();
        this.scale_.addAll(new Scale(tonality_.getTonic(), tonality_.getMode(), 1).getScale());
        this.chords_ = generateChordDegrees();
    }

    /**
     * Finds the degree of a chord
     * @param chord Analyzed chord
     * @return Degree (between 1 and 7) with a boolean specifying whether it is a seventh chord
     *         `null` if the chord does not match a degree.
     */
    public ChordDegree chordToDegree(ArrayList<Integer> chord, int barFractionDen) {
        double[] percentage = new double[chords_.size()];
        int chordsize = 0;

        for (Integer n : chord)
            if (n != null && n >= 0)
                chordsize++;

        for (int i = 0; i < chords_.size(); ++i) {
            for (int j = 0; j < chord.size(); ++j) {
                if (chord.get(j) == null || chord.get(j) < 0)
                    continue;
                for (int k = 0; k < chords_.get(i).length - 1; ++k) {
                    if (chords_.get(i)[k] % 12 == chord.get(j) % 12) {
                        percentage[i] += 1.0;
                        break;
                    }
                }
            }
            percentage[i] /= chordsize;
        }

        double max = 0.0;
        int degree = 0;
        boolean seventhChord = false;

        for (int i = 0; i < percentage.length; ++i) {
            if (percentage[i] > max) {
                max = percentage[i];
                degree = i + 1;
            }
        }

        double seventhPercentage = 0.0;

        for (int i = 0; i < chord.size(); ++i) {
            if (degree > 0 && chord.get(i) % 12 == chords_.get(degree - 1)[3] % 12)
                ++seventhPercentage;
        }
        seventhPercentage /= chord.size();

        if (seventhPercentage >= 0.05)
            seventhChord = true;

        if (max < 0.50)
            return new ChordDegree(0, false, barFractionDen);

        // Logging the chord and the detected degree
        /*StringBuilder sb = new StringBuilder("DEGREELOG: ");
        for (Integer i : chord)
            if (i != null && i != Pitches.REST)
                sb.append(Tonality.pitchToFrenchString(i, true)).append(" ");
        System.out.println(sb.append(" -> ").append(new ChordDegree(degree, seventhChord, barFractionDen)).toString());*/

        return new ChordDegree(degree, seventhChord, barFractionDen);
    }

    /**
     * Create the perfect seventh chord for each chord degree of the stored tonality
     * @return ArrayList of 7 chords
     */
    private ArrayList<Integer[]> generateChordDegrees() {
        ArrayList<Integer[]> chords = new ArrayList<>();
        for (int i = 0; i < scale_.size(); ++i) {
            chords.add(new Integer[]{scale_.get(i), scale_.get(i + 2), scale_.get(i + 4), scale_.get(i + 6)});
        }
        return chords;
    }
}