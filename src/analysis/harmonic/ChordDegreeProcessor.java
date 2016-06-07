package analysis.harmonic;

import analysis.containers.CircularArrayList;
import tonality.Scale;
import tonality.Tonality;

import java.util.ArrayList;

public class ChordDegreeProcessor {
    private Tonality tonality_;
    private CircularArrayList<Integer> scale_;
    private ArrayList<Integer[]> chords_;

    public ChordDegreeProcessor(Tonality tonality) {
        this.tonality_ = tonality;
        this.scale_ = new CircularArrayList<>();
        this.scale_.addAll(new Scale(tonality.getTonic(), tonality.getMode(), 1).getScale());
        this.chords_ = generateChordDegrees();
    }

    /**
     * Finds the degree of a chord
     * @param chord the analyzed chord
     * @return The degree (between 1 and 7) with a boolean specifying whether it is a seventh chord
     *          null if the chord does not match a degree.
     */
    public ChordDegree chordToDegree(Integer[] chord) {
        double[] percentage = new double[chords_.size()];
        for (int i = 0; i < chords_.size(); ++i) {
            for (int j = 0; j < chord.length; ++j) {
                for (int k = 0; k < chords_.get(i).length; ++k) {
                    if (chords_.get(i)[k].equals(chord[j])) {
                        percentage[i] += 1.0;
                        break;
                    }
                }
            }
            percentage[i] /= chord.length;
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

        for (int i = 0; i < chord.length; ++i) {
            if (degree > 0 && chord[i] == chords_.get(i - 1)[3])
                seventhChord = true;
        }

        if (max > 0.6)
            return new ChordDegree(degree, seventhChord);
        else
            return null;
    }

    private ArrayList<Integer[]> generateChordDegrees() {
        ArrayList<Integer[]> chords = new ArrayList<>();
        for (int i = 0; i < scale_.size(); ++i) {
            chords.add(new Integer[]{scale_.get(i), scale_.get(i + 2), scale_.get(i + 4), scale_.get(i + 6)});
        }
        return chords;
    }
}