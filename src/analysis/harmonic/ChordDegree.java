package analysis.harmonic;

public class ChordDegree {
    private int degree_;
    private int inversionIndex_;
    private boolean seventhChord_;

    /**
     * Constructor for the ChordDegree class
     * @param degree Degree of the chord
     * @param seventhChord Specify whether it is a seventh chord or not
     */
    public ChordDegree(int degree, boolean seventhChord) {
        this.degree_ = degree;
        this.seventhChord_ = seventhChord;
    }

    public int getDegree() {
        return degree_;
    }

    public boolean isSeventhChord() {
        return seventhChord_;
    }

    public int getInversionIndex() {
        return inversionIndex_;
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
