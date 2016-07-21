package analysis.phrase;

import analysis.harmonic.ChordDegree;

public class Cadence {

    private int beginBar_;
    private int endBar_;

    private ChordDegree firstDegree_;
    private ChordDegree lastDegree_;

    public Cadence(ChordDegree firstDegree, ChordDegree lastDegree, int beginBar, int endBar) {
        beginBar_ = beginBar;
        endBar_ = endBar;

        firstDegree_ = firstDegree;
        lastDegree_ = lastDegree;
    }

    /**
     * This function returns the length of the Cadence in term of bar number.
     * @return endBar_ - beginBar;
     */
    public int cadenceLenght() {
        return endBar_ - beginBar_;
    }

    /**
     * This function transform a given Cadence into a string representation
     * giving its nature and the begining and lasting mesure of the corresponding
     * Score.
     * @return The Cadence's string representation.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        switch (firstDegree_.getDegree()) {
            case 1 :
                if (lastDegree_.getDegree() == 5)
                    sb.append("Demi Cadence : ");
                break;
            case 4 :
                if (lastDegree_.getDegree() == 1)
                    sb.append("Plagale Cadence : ");
                break;
            case 5 :
                if (lastDegree_.getDegree() == 1)
                    sb.append("Perfect Cadence : ");
                else
                    sb.append("Deceptive Cadence : ");
                break;
        }
        sb.append(beginBar_).append(" -> ").append(endBar_).append(")").append("\n");
        return sb.toString();
    }
}
