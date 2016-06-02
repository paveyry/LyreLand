package tonality;

import jm.music.data.Note;

import java.util.Arrays;
import java.util.List;

/**
 * Created by olivier on 16/05/16.
 */
public class Chord {
    private int root_;
    private int mediant_;
    private int dominante_;
    private Note[] notes_;
    private int[] pitchs_;
    private double rythm_;

    public Chord (int root, String s) {
        root_ = root;
        mediant_ = root + (s.equals("major")? 4 : 3);
        dominante_ = root + 7;
    }

    // ---------- Getters ----------
    public List<Integer> getChord() {
        return Arrays.asList(root_, mediant_, dominante_);
    }

    // FIXME: We can add function for the different kinds of chords (eg. augmented etc.)
}