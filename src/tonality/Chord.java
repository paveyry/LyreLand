package tonality;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by olivier on 16/05/16.
 */
public class Chord {
    private int _root;
    private int _mediant;
    private int _dominante;

    public Chord (int root, String s) {
        _root = root;
        _mediant = root + (s.equals("major")? 4 : 3);
        _dominante = root + 7;
    }

    // ---------- Getters ----------
    public List<Integer> getChord() {
        return Arrays.asList(_root, _mediant, _dominante);
    }

    // FIXME: We can add function for the different kinds of chords (eg. augmented etc.)
}