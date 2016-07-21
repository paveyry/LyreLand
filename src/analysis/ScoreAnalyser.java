package analysis;

import analysis.bars.BarLexer;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.ChordDegreeExtractor;
import analysis.metadata.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import tonality.Scale;
import tonality.Tonality;

import java.util.ArrayList;

/**
 * Class for Score analysis. Executes all the step of the analysis on a specific score.
 * This class can be serialized in XML using the XStream library to create an input file for the training.
 */
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
    private double quantum_;
    private ChordDegreeExtractor chordDegreeExtrator_;

    // Extracted data
    private transient Scale scale_;

    /**
     * Constructor for ScoreAnalyser
     * @param midiFile Path to MIDI file to analyse
     */
    public ScoreAnalyser(String midiFile) {
        score_ = new Score();
        try {
            Read.midi(score_, midiFile);
            fileName_ = midiFile.substring(midiFile.lastIndexOf('/') + 1, midiFile.length());
            title_ = score_.getTitle();
            tonality_ = MetadataExtractor.getTonality(score_.getKeySignature(), score_.getKeyQuality());
            scale_ = MetadataExtractor.computeScale(tonality_);
            barUnit_ = MetadataExtractor.computeBarUnit(score_.getDenominator());
            beatsPerBar_ = score_.getNumerator();
            partNb_ = score_.getPartArray().length;
            quantum_ = MetadataExtractor.findQuantum(score_);
            barLexer_ = new BarLexer(score_, tonality_, quantum_);
            chordDegreeExtrator_ = new ChordDegreeExtractor(barLexer_);
            degreeList_ = chordDegreeExtrator_.getDegreeSequence();
        }
        catch (Exception e) {
            System.err.println("Error: ScoreAnalyser midi file can't be found");
        }
    }

    /**
     * Display the important extracted data after analysis.
     */
    public void printScoreInfo() {
        StringBuilder sb = new StringBuilder("--------------------- Score MetaData ------------------------\n");
        sb.append("Processed File: ").append(fileName_).append("\n");
        sb.append("Score's Tonality: ").append(tonality_.toString()).append("\n");
        sb.append("Score's Scale: ").append(scale_.toString()).append(" = [ ");
        for (int n : scale_.getScale())
            sb.append(Tonality.pitchToFrenchString(n, true)).append(" ");
        sb.append("]\n");
        sb.append("Score's Bar Unit: ").append(+ barUnit_).append("\n");
        sb.append("Score's Beat Per Bar: ").append(beatsPerBar_).append("\n");
        sb.append("Score's Bar Number: ").append(barLexer_.getBarNumber()).append("\n");
        sb.append("Score's Bar Rhythmic Mean: ").append(barLexer_.getScoreRhythmicMean()).append("\n");
        sb.append("------------------------ DegreeList ------------------------\n");
        sb.append(chordDegreeExtrator_.getDegreeSequence()).append("\n\n");
        System.out.println(sb);
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

    public ArrayList<ChordDegree> getDegreeList() {
        return degreeList_;
    }

    public double getQuantum() {
        return quantum_;
    }

    public BarLexer getBarLexer() {
        return barLexer_;
    }
}
