package tonality;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains the pitches of a tonal scale.
 */
public class Scale {
    private int tonic_;
    private Tonality.Mode mode_; // Minor or Major ?
    private ArrayList<Integer> notes_;

    /**
     * Scale constructor
     * @param tonic Tonic note pitch
     * @param mode Mode (Major, Minor, Melodic Minor, Harmonic minor)
     * @param octaveNumber Number of octaves in the notes array contained in the scale
     */
    public Scale(int tonic, Tonality.Mode mode, int octaveNumber) {
        tonic_ = tonic;
        mode_ = mode;
        setScale(octaveNumber);
    }

    /**
     * Scale constructor
     * @param tonality Tonality for the scale
     * @param octaveNumber Number of octaves in the notes array contained in the scale
     */
    public Scale(Tonality tonality, int octaveNumber) {
        tonic_ = tonality.getTonic();
        mode_ = tonality.getMode();
        setScale(octaveNumber);
    }

    /**
     * Check if a specific pitch is in the scale
     * @param pitch Pitch to find
     * @return Result of the search
     */
    public boolean isInScale(int pitch) {
        for (int i = 0; i < 7; i++)
            if (notes_.get(i) % 12 == pitch % 12)
                return true;
        return false;
    }

    /**
     * This function is used in ScaleTests to evaluate if given a tonality.
     * The resulting scale is true.
     * @param scale is an Arraylist of Integer containing a Scale of 1 octave
     * @return boolean
     */
    public boolean checkScale(ArrayList<Integer> scale)
    {
        if (this.notes_.size() != scale.size())
            return false;
        boolean result = true;
        for (int i = 0; i < this.notes_.size() && result; ++i)
            if (this.notes_.get(i) % 12 != scale.get(i) % 12)
                result = false;
        return result;
    }

    /**
     * Convert Scale to String
     * @return Formatted String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int n : notes_)
            sb.append(n).append(" ");
        sb.append("]");
        return sb.toString();
    }

    /**
     * Getter for the Scale notes
     * @return ArrayList of the scale notes pitches
     */
    public ArrayList<Integer> getScale(){
        return notes_;
    }

    /**
     * Reset the Scale with the specified octave number (previous tonic and mode are kept)
     * @param octaveNumber Number of octaves contained in the scale
     */
    public void setScale (int octaveNumber) {
        notes_ = createScale(tonic_, mode_, octaveNumber);
    }

    /**
     * Create the arraylist of note pitches of the scale
     * @param tonic Tonic
     * @param mode Mode
     * @param octaveNumber Number of octaves contained in the scale
     * @return ArrayList of note pitches of the created scale
     */
    private ArrayList<Integer> createScale(int tonic, Tonality.Mode mode, int octaveNumber) {
        // We have to check what we ask don't exceed G9 = 127 (highest pitch)
        if (tonic + (octaveNumber * 12) > 127)
            return null;

        ArrayList<Integer> notes = new ArrayList<>();
        for (int i = 0; i < octaveNumber; i++) {
            switch (mode) {
                case MAJOR:
                    notes.addAll(Arrays.asList(tonic, tonic + 2, tonic + 4, tonic + 5, tonic + 7, tonic + 9, tonic + 11));
                    break;
                case MINOR:
                    notes.addAll(Arrays.asList(tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 10));
                    break;
                case HARMONICMINOR:
                    notes.addAll(Arrays.asList(tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 8, tonic + 11));
                    break;
                case MELODICMINOR:
                    notes.addAll(Arrays.asList(tonic, tonic + 2, tonic + 3, tonic + 5, tonic + 7, tonic + 9, tonic + 11));
                    break;
                default:
                    break;
            }
            tonic += 12;
        }
        return notes;
    }
}
