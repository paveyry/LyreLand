package analysis;

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
    private String fileName_;
    private Score score_;

    // Basic Meta data of the score.
    private String title_;
    private Tonality tonality_;
    private double barUnit_;
    private int beatsPerBar_;
    private int partNb_;
    private ArrayList<ArrayList<ChordDegree>> degreeList_;

    // Extracted data
    private Scale scale_;

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
            //degreeList_ = (new ChordLexer(score_.copy(), barUnit_, beatsPerBar_, tonality_)).sequenceDegree();
        } catch (Exception e) {
            System.out.println("Error: ScoreAnalyser midi file can't be found");
        }
    }
    public void processDegreeList() {
        ChordLexer chl = new ChordLexer(score_.copy(), barUnit_, beatsPerBar_, tonality_);
        degreeList_ = chl.sequenceDegree();
        for (ArrayList<ChordDegree> chd : degreeList_)
            Collections.reverse(chd);
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

    public ArrayList<ArrayList<ChordDegree>> getDegreeList() { return degreeList_; }
}
