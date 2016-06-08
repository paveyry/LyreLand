package analysis.harmonic;

public class ChordDegree {
    private int degree_;
    private int inversionIndex_;
    private boolean seventhChord_;

    /**
     * Constructor for the ChordDegree class
     * @param degree Degree of the chord
     * @param seventhChord Specify whether it is a seventh chord or not
     * @param inversionIndex Specify which chord inversion the chord is
     */
    public ChordDegree(int degree, boolean seventhChord, int inversionIndex) {
        this.degree_ = degree;
        this.seventhChord_ = seventhChord;
        this.inversionIndex_ = inversionIndex;
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
}
