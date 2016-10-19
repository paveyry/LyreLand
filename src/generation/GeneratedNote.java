package generation;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;

import java.util.ArrayList;

/**
 * Class containing the data of the generated Notes/Chords
 */
public class GeneratedNote {
    private double rhythm_;
    private ArrayList<Integer> chordPitches_;
    private int chordSize_;
    private ChordDegree degree_;
    private Tonality tonality_;

    /**
     * Constructor
     * @param degree ChordDegree containing the note in the Harmonic phrase
     * @param chordSize Number of notes (different pitches) to generate in the Note
     * @param rhythm Duration of the Note
     */
    public GeneratedNote(ChordDegree degree, int chordSize, double rhythm)
    {
        rhythm_ = rhythm;
        chordSize_ = chordSize;
        degree_ = degree;
        chordPitches_ = null;
    }

    public void setChordPitches(ArrayList<Integer> chordPitches_) {
        this.chordPitches_ = chordPitches_;
    }

    public double getRhythm() {
        return rhythm_;
    }

    public ArrayList<Integer> getChordPitches() {
        return chordPitches_;
    }

    public int getChordSize() {
        return chordSize_;
    }

    public ChordDegree getDegree() {
        return degree_;
    }
}
