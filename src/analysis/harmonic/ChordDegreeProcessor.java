package analysis.harmonic;

import analysis.containers.CircularArrayList;
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
    public ChordDegree chordToDegree(ArrayList<Integer> chord) {
        double[] percentage = new double[chords_.size()];
        for (int i = 0; i < chords_.size(); ++i) {
            for (int j = 0; j < chord.size(); ++j) {
                for (int k = 0; k < chords_.get(i).length - 1; ++k) {
                    if (chords_.get(i)[k] % 12 == chord.get(j) % 12) {
                        percentage[i] += 1.0;
                        break;
                    }
                }
            }
            percentage[i] /= chord.size();
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

        for (int i = 0; i < chord.size(); ++i) {
            if (degree > 0 && chord.get(i) % 12 == chords_.get(degree - 1)[3] % 12)
                seventhChord = true;
        }


        if (!((max > 0.51 && seventhChord) || max >= 0.75))
            return null;

        return new ChordDegree(degree, seventhChord);
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