package analysis.harmonic;

import tonality.Tonality;

public class ChordDegreeProcessor {
    private Integer tonality_;

    public ChordDegreeProcessor(int tonality) {
        this.tonality_ = tonality;
        Tonality.Mode m = Tonality.Mode.MELODICMINOR;
    }
}
