package analysis.harmonic;

import analysis.bars.BarLexer;
import tonality.Tonality;

import java.util.ArrayList;

public class HarmonicProcessor {

    private Tonality tonality_;
    private BarLexer barLexer_;
    private ArrayList<ChordDegree> degreeList_;

    public HarmonicProcessor(Tonality tonality, BarLexer barLexer) {
        tonality_ = tonality;
        barLexer_ = barLexer;
        ChordDegreeExtractor chordDegreeExtrator_ = new ChordDegreeExtractor(barLexer_, tonality_);
        degreeList_ = chordDegreeExtrator_.getDegreeSequence();
    }

    /*
        Things to do .
        Bar method to gather pitches. Bar.getBarPitches().
        Do the method that analyse if the current tonality of the bar is the one from before
        Or the original of the score.
        Do the function that check for every other tonality if the first function fails.
     */

    public ArrayList<ChordDegree> getDegreeList() {
        return degreeList_;
    }



}
