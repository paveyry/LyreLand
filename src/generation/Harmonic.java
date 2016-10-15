package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import training.probability.MarkovMatrix;
import training.probability.ProbabilityVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Harmonic {
    private Tonality tonality_;
    private MarkovMatrix<ChordDegree> markovDegree_;
    private ProbabilityVector<List<ChordDegree>> endings_;

    public Harmonic(Tonality tonality, MarkovMatrix<ChordDegree> markovDegree,
                    ProbabilityVector<List<ChordDegree>> endings) {
        tonality_ = tonality;
        markovDegree_ = markovDegree;
        endings_ = endings;
    }

    /**
     * This function generate an harmonic base using the MarkovMatrix<ChordDegree> transition
     * matrix.
     * @param degreeNumber number of degree we want to generate for the harmonic base.
     * @return an ArrayList of ChordDegree.
     */
    public ArrayList<ChordDegree> generateHarmonicBase(int degreeNumber, long seed) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ChordDegree depth1 = null;
        ChordDegree depth2 = null;
        Random generator = new Random(seed + 125);
        for(int i = 0; i < degreeNumber - 2; ++i) {
            ChordDegree newChord = markovDegree_.getRandomValue(Arrays.asList(depth1, depth2), generator);
            result.add(newChord);
            depth1 = depth2;
            depth2 = newChord;
        }
        List<ChordDegree> ending = endings_.getValue(generator);
        result.addAll(ending);
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
