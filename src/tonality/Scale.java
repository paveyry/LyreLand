package tonality;

import java.util.ArrayList;
import java.util.Arrays;

public class Scale {
    private int tonic_;
    private Tonality.Mode mode_; // Minor or Major ?
    private ArrayList<Integer> notes_;

    // ---------- Constructors ----------
    public Scale(int tonic, Tonality.Mode mode, int octaveNumber) {
        tonic_ = tonic;
        mode_ = mode;
        setScale(octaveNumber);
    }

    public boolean isInScale(int pitch) {
        boolean result = false;
        for (int i = 0; i < notes_.size() && !result; i++) {
            result = (notes_.get(i) % 12 == pitch % 12);
        }
        return result;
    }

    // Convert the array pitch composing the scale into
    // a string witch can latter be written in a file
    public String pitchToString() {
        String result = "[";
        for (int i = 0; i < notes_.size(); i++) {
            if (i < notes_.size() - 1)
                result += notes_.get(i) + ", ";
            else
                result += notes_.get(i) + "]";
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int n : notes_)
            sb.append(n).append(" ");
        sb.append(" ]");
        return sb.toString();
    }

    public ArrayList<Integer> getScale(){
        return notes_;
    }

    public ArrayList<Integer> getNotes_() {
        return notes_;
    }

    public void setScale (int octaveNumber) {
        notes_ = createScale(tonic_, mode_, octaveNumber);
    }

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
