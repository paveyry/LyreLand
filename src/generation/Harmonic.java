package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;
import jm.music.data.CPhrase;
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
     * @param barNumber number of bar we want to generate for the harmonic base.
     * @return an ArrayList of ChordDegree.
     */
    public ArrayList<ChordDegree> generateHarmonicBase(int barNumber, Random generator) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ArrayList<ChordDegree> temp = new ArrayList<>();
        ChordDegree depth1 = null;
        ChordDegree depth2 = null;
        double sumDen = 0.0;
        for(int i = 0; i < barNumber - 1; ++i) {
            ChordDegree tempDepth1 = depth1;
            ChordDegree tempDepth2 = depth2;
            while (sumDen < 1.0) {
                ChordDegree newChord = markovDegree_.getRandomValue(Arrays.asList(tempDepth1, tempDepth2), generator);
                temp.add(newChord);
                sumDen += 1.0 / (double)newChord.getBarFractionDen();
                tempDepth1 = tempDepth2;
                tempDepth2 = newChord;
            }
            if (sumDen == 1.0) {
                result.addAll(temp);
                depth1 = tempDepth1;
                depth2 = tempDepth2;
            }
            else
                --i;
            sumDen = 0.0;
            temp.clear();

        }
        //List<ChordDegree> ending = endings_.getValue(generator);
        //result.addAll(ending);
        ChordDegree last = result.get(result.size() - 1);
        ChordDegree previousToLast = result.get(result.size() - 2);
        List<ChordDegree> ending;
        int i = 0;
        do {
            ending = endings_.getValue(generator);
        } while (i++ < 100 && (ending.get(0) != previousToLast || ending.get(1) != last));
        if (i >= 101)
            return generateHarmonicBase(barNumber, generator);
        result.add(ending.get(2));
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

    public CPhrase generateAccompaniement(ArrayList<ChordDegree> harmonicBase, double barUnit, int beatPerBar) {
        CPhrase chords = new CPhrase();
        for (ChordDegree c : harmonicBase)
            chords.addChord(getChord(c, 60), barUnit * beatPerBar / c.getBarFractionDen());
        return chords;
    }
}
