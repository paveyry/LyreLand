package analysis.phrase;

import analysis.bars.BarLexer;
import analysis.harmonic.ChordDegree;

import java.util.ArrayList;

// fixme: this code is in pause waiting for detection of change in tonality within a score.
/*      CadenceExtractor lol = new CadenceExtractor(bal, sa.getDegreeList());
        ArrayList<Cadence> test = lol.extractCadences();
        System.out.println(test.size());
        for (Cadence cadence : test)
        System.out.println(cadence.toString());*/

public class CadenceExtractor {

    private double scoreRhythmicMean_;
    private BarLexer barLexer_;
    private ArrayList<ChordDegree> degreeList_;

    public CadenceExtractor (BarLexer barlexer, ArrayList<ChordDegree> degreeList) {
        barLexer_ = barlexer;
        scoreRhythmicMean_ = barlexer.getScoreRhythmicMean();
        degreeList_ = degreeList;
    }

    /**
     * This function return true if the chordDegree degree given in parameter
     * correspond to a type of cadence. (Fixme: Deceptive Cadence are not supported for now)
     * @return True or False.
     */
    private boolean isCadence(int first, int second) {
        return ((first == 1 && second == 5) || (first == 5 && second == 1) ||
                (first == 4 && second == 1));
    }

    /** Detect and extract the cadences of a score based on the rhythmic duration
        and the degrees list
    */
    public ArrayList<Cadence> extractCadences() {
        ArrayList<Cadence> result = new ArrayList<>();
        double fractionCounter = 0.0;
        int beginBar = 0;
        int endBar = 0;

        // Iterate through the degree list to extract the wanted cadences.
        for (int i = 1; i < degreeList_.size(); ++i) {
            ChordDegree firstChord = degreeList_.get(i - 1);
            ChordDegree secondChord = degreeList_.get(i);

            fractionCounter += (1/firstChord.getBarFractionDen_());
            if (fractionCounter == 1.0) {
                ++endBar;
                fractionCounter = 0;
            }
            if (isCadence(firstChord.getDegree(), secondChord.getDegree())) {
                Cadence cadence = new Cadence(firstChord, secondChord, beginBar, endBar);
                result.add(cadence);
                beginBar = endBar;
            }
        }
        return result;
    }
}
