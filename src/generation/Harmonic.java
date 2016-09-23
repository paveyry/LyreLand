package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import training.probability.MarkovDegree;

import java.util.ArrayList;

public class Harmonic {
    private Tonality tonality_;
    private MarkovDegree markovDegree_;

    public Harmonic(Tonality tonality, MarkovDegree markovDegree) {
        tonality_ = tonality;
        markovDegree_ = markovDegree;
    }

    /**
     * This function generate an harmonic base using the MarkovDegree transition
     * matrix.
     * @param degreeNumber number of degree we want to generate for the harmonic base.
     * @return an ArrayList of ChordDegree.
     */
    public ArrayList<ChordDegree> generateHarmonicBase(int degreeNumber, long seed) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ChordDegree depth1 = null;
        ChordDegree depth2 = null;
        for(int i = 0; i < degreeNumber; ++i) {
            ChordDegree newChord = markovDegree_.getDegree(depth2, depth1, seed);
            result.add(newChord);
            depth2 = depth1;
            depth1 = newChord;
        }
        System.out.println(result);
        return result;
    }

    /**
     * This function takes a ChordDegree as Input and transform it into an Array of pitches
     * that can later be used to generate a CPhrase for instance.
     * @param degree
     * @param offset is an int value uses to calibrate the generate hight of the chord
     *               since every pitch in the scale is modulo 12 ...
     * @return an array of integer.
     */
    public int[] getChord(ChordDegree degree, int offset) {
        ArrayList<Integer> scale = new Scale(tonality_, 2).getScale();
        int value = degree.getDegree();
        if (!degree.isSeventhChord())
            return new int[]{scale.get(value) + offset, scale.get(value + 2) + offset,
                             scale.get(value + 4) + offset};
        else
            return new int[]{scale.get(value) + offset, scale.get(value + 2) + offset,
                             scale.get(value + 4) + offset, scale.get(value + 6) + offset};
    }
}
