package analysis;

import analysis.bars.BarLexer;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.ChordDegreeSequenceExtractor;
import analysis.harmonic.HarmonicProcessor;
import analysis.metadata.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;

import java.util.ArrayList;

/**
 * Class for Score analysis. Execute all the steps of the analysis on a specific score.
 * This class can be serialized in XML using the XStream library to create an input file for the training.
 */
public class ScoreAnalyser {
    private transient String fileName_;
    private transient Score score_;

    // Data of the score.
    private transient String title_;
    private Tonality tonality_;
    private double barUnit_;
    private int beatsPerBar_;
    private int tempo_;
    private int partNb_;
    private ArrayList<ChordDegree> degreeList_;
    private BarLexer barLexer_;
    private double quantum_;
    private transient ChordDegreeSequenceExtractor chordDegreeExtrator_;
    private transient Scale scale_;
    private int barNumber_;

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
            tempo_ = (int)score_.getTempo();
            partNb_ = score_.getPartArray().length;
            quantum_ = MetadataExtractor.findQuantum(score_);
            barLexer_ = new BarLexer(score_, quantum_);
            degreeList_ = new HarmonicProcessor(tonality_, barLexer_).getDegreeList();
            barNumber_ = barLexer_.getBarNumber();
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
        sb.append("------------------------ DegreeList ------------------------\n");
        sb.append(degreeList_).append("\n\n");
        System.out.println(sb);
    }

    // GETTERS / SETTERS

    /**
     * Getter for the Title class attribute.
     * @return
     */
    public String getTitle() {
        return title_;
    }

    /**
     * Getter for the Score class attribute.
     * @return
     */
    public Score getScore() {
        return score_;
    }

    /**
     * Getter for the Tonality class attribute.
     * @return tonality_
     */
    public Tonality getTonality() {
        return tonality_;
    }

    /**
     * Getter for the Scale class attribute.
     * @return scale_
     */
    public Scale getScale() {
        return scale_;
    }

    /**
     * Getter for the BarUnit class attribute.
     * @return barUnit_
     */
    public double getBarUnit() {
        return barUnit_;
    }

    /**
     * Getter for the BeatPerBar class attribute.
     * @return beatsPerBar
     */
    public int getBeatsPerBar() {
        return beatsPerBar_;
    }

    /**
     * Getter for the Tempo class attribute.
     * @return tempo_
     */
    public double getTempo() {
        return tempo_;
    }

    /**
     * Getter for the PartNumber class attribute.
     * @return partNB
     */
    public int getPartNb() {
        return partNb_;
    }

    /**
     * Getter for the DegreeList class attribute.
     * @return degreeList_
     */
    public ArrayList<ChordDegree> getDegreeList() {
        return degreeList_;
    }

    /**
     * Getter for the Quantum class attribute.
     * @return quantum_
     */
    public double getQuantum() {
        return quantum_;
    }

    /**
     * Getter for the BarLexer class attribute.
     * @return barLexer_
     */
    public BarLexer getBarLexer() {
        return barLexer_;
    }

    /**
     * Getter for the BarNumber class attribute.
     * @return barNumber_
     */
    public int getBarNumber() {
        return barNumber_;
    }

}
