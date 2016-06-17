package analysis.harmonic;

public class ChordDegree {
    private int degree_;
    private boolean seventhChord_;
    private int barFractionDen_;

    /**
     * Constructor for the ChordDegree class
     * @param degree Degree of the chord
     * @param seventhChord Specify whether it is a seventh chord or not
     */
    public ChordDegree(int degree, boolean seventhChord, int barFractionDen) {
        this.degree_ = degree;
        this.seventhChord_ = seventhChord;
        this.barFractionDen_ = barFractionDen;
    }

    public int getDegree() {
        return degree_;
    }

    public boolean isSeventhChord() {
        return seventhChord_;
    }

    public int getBarFractionDen_() {
        return barFractionDen_;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ChordDegree))
            return false;
        if (degree_ == ((ChordDegree) other).degree_ && seventhChord_== ((ChordDegree) other).seventhChord_)
            return true;
        return false;
    }
}
