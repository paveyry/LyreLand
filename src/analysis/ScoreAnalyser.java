package analysis;

import analysis.bars.BarLexer;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.ChordLexer;
import analysis.harmonic.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import tonality.Scale;
import tonality.Tonality;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreAnalyser {
    private transient String fileName_;
    private transient Score score_;

    // Basic Meta data of the score.
    private transient String title_;
    private Tonality tonality_;
    private double barUnit_;
    private int beatsPerBar_;
    private int partNb_;
    private ArrayList<ChordDegree> degreeList_;
    private BarLexer barLexer_;

    // Extracted data
    private transient Scale scale_;

    public ScoreAnalyser(String midiFile) {
        score_ = new Score();
        try {
            Read.midi(score_, midiFile);
            fileName_ = midiFile.substring(midiFile.lastIndexOf('/') + 1, midiFile.length());
            System.out.println("Processed File: " + fileName_);
            title_ = score_.getTitle();
            tonality_ = MetadataExtractor.getTonality(score_.getKeySignature(), score_.getKeyQuality());
            scale_ = MetadataExtractor.computeScale(tonality_);
            barUnit_ = MetadataExtractor.computeBarUnit(score_.getDenominator());
            beatsPerBar_ = score_.getNumerator();
            partNb_ = score_.getPartArray().length;
            barLexer_ = new BarLexer(score_, tonality_);
            degreeList_ = barLexer_.getDegreeSequence();
        }
        catch (Exception e) {
            System.err.println("Error: ScoreAnalyser midi file can't be found");
        }
    }

    public String getFileName() {
        return fileName_;
    }

    public String getTitle() {
        return title_;
    }

    public Score getScore() {
        return score_;
    }

    public Tonality getTonality() {
        return tonality_;
    }

    public Scale getScale() {
        return scale_;
    }

    public double getBarUnit() {
        return barUnit_;
    }

    public int getBeatsPerBar() {
        return beatsPerBar_;
    }

    public int getPartNb() {
        return partNb_;
    }

    public ArrayList<ChordDegree> getDegreeList() { return degreeList_; }
}
