package analysis;

import analysis.harmonic.MetadataExtractor;
import jm.music.data.Score;
import jm.util.Read;
import tonality.Scale;

public class ScoreAnalyser {
    private String fileName_;
    private Score score_;

    // Basic Meta data of the score.

    private String title_;
    private String tonality_;
    private double barUnit_;
    private int beatsPerBar_;
    private int partNb_;

    // Extracted informations
    private Scale scale_;

    public ScoreAnalyser(String midiFile) {
        score_ = new Score();
        try {
            Read.midi(score_, midiFile);
            title_ = score_.getTitle();
            tonality_ = MetadataExtractor.getTonality(score_.getKeySignature(), score_.getKeyQuality());
            scale_ = MetadataExtractor.computeScale(tonality_);
            barUnit_ = MetadataExtractor.computeBarUnit(score_.getDenominator());
            beatsPerBar_ = score_.getNumerator();
            partNb_ = score_.getPartArray().length;
        } catch (Exception e) {
            System.out.println("Error: ScoreAnalyser midi file can't be found");
        }
    }

    // ---------- GETTERS ----------
    public String getFileName() { return fileName_; }

    public String getTitle() { return title_; }

    public Score getScore() { return score_; }

    public String getTonality() { return tonality_; }

    public Scale getScale() { return scale_; }

    public double getBarUnit() { return barUnit_; }

    public int getBeatsPerBar() { return beatsPerBar_; }

    public int getPartNb() { return partNb_; }

    // ---------- SETTERS ----------
    // None for now
}
