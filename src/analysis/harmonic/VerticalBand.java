package analysis.harmonic;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store Chords information extracted by the ChordExtractor class.
 * The dynamic and articulations are stored for later use.
 */
public class VerticalBand {
    private ArrayList<Integer> pitches_;
    private double rhythm_; // Only one rythm for the all band.
    private ArrayList<Double> duration_;
    private ArrayList<Integer> dynamic_;

    /**
     * Constructor
     * @param pitches Pitches of the notes in the Band
     * @param rhythm Rhythm of the Band
     * @param duration Articulation of each note
     * @param dynamic Dynamic (nuance) of each note
     */
    public VerticalBand(ArrayList<Integer> pitches, double rhythm,
                        ArrayList<Double> duration, ArrayList<Integer> dynamic) {
        pitches_ = pitches;
        rhythm_ = rhythm;
        duration_ = duration;
        dynamic_ = dynamic;
    }

    /**
     * Check equality with another VerticalBand
     * @param o Compared VerticalBand
     * @return Equality boolean: false if different or if o is not a VerticalBand
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VerticalBand))
            return false;
        VerticalBand ac = (VerticalBand) o;
        if (this.pitches_.equals(ac.getPitches()))
            if (this.dynamic_.equals(ac.getDynamic()))
                if (this.duration_.equals(ac.getDuration()))
                    return true;
        return false;
    }

    /**
     * Convert a VerticalBand to a String
     * @return Generated String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pitches: [ ");
        for (Integer i : pitches_)
            sb.append(i).append(" ");
        sb.append("] || Rhythm = ").append(rhythm_).append(" || Duration: [ ");
        for (Double i : duration_)
            sb.append(i).append(" ");
        sb.append("] || Dynamic: [ ");
        for (Integer i : dynamic_)
            sb.append(i).append(" ");
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Print the object on the standard output
     */
    public void printVerticalBand(){
        System.out.println(this.toString());
    }

    public ArrayList<Integer> getPitches() {
        return pitches_;
    }

    public double getRythm() {
        return rhythm_;
    }

    public ArrayList<Integer> getDynamic() {
        return dynamic_;
    }

    public ArrayList<Double> getDuration() {
        return duration_;
    }

    public void setRythm(double r) {
        rhythm_ = r;
    }
}