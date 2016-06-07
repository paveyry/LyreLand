package analysis.harmonic;

public class ChordDegree {
    private int degree_;
    private boolean seventhChord_;

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
}
