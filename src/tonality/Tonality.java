package tonality;

import analysis.containers.CircularArrayList;
import jm.constants.Pitches;

import java.util.Arrays;

import static jm.constants.Pitches.*;

/**
 * Class that describes a tonality.
 */
public class Tonality {
    /**
     * Enum for the mode
     */
    public enum Mode {
        MAJOR,
        MINOR,
        HARMONICMINOR,
        MELODICMINOR
    }

    private Integer tonic_;
    private Mode mode_;
    private Boolean isSharp_; // Already applied on the tonic pitch. Just useful to distinguish C sharp from D flat

    /**
     * Tonality constructor
     * @param tonic Tonic note pitch
     * @param mode Mode
     * @param isSharp Specify if the key signature contains sharps or flats (the pitch is not impacted by this value)
     */
    public Tonality(int tonic, Mode mode, boolean isSharp) {
        this.tonic_ = tonic;
        this.mode_ = mode;
        this.isSharp_ = isSharp;
    }

    /**
     * Tonality constructor that processes tonality from key signature and quality
     * @param keySignature Key Signature of the Score
     * @param keyQuality Key Quality of the Score
     */
    public Tonality(int keySignature, int keyQuality)
    {
        isSharp_ = false;

        if (keySignature == 0) {
            if (keyQuality == 0) {
                tonic_ = C0;
                mode_ = Mode.MAJOR;
            }
            else {
                tonic_ = A0;
                mode_ = Mode.MINOR;
            }
            return;
        }

        mode_ = (keyQuality == 0) ? Tonality.Mode.MAJOR : Tonality.Mode.MINOR;

        CircularArrayList<Integer> order = new CircularArrayList<>(); // Sharp or flat order
        if (keySignature > 0) {
            order.addAll(Arrays.asList(F4, C4, G4, D4, A4, E4, B4));
            tonic_ = (mode_ == Mode.MAJOR) ? order.get(keySignature - 1) : order.get(keySignature - 1) - 3;

            for (int i = 0; i < keySignature; ++i)
                if (order.get(i) % 12 == tonic_ % 12) {
                    ++tonic_;
                    isSharp_ = true;
                }
        }
        else {
            order.addAll(Arrays.asList(B4, E4, A4, D4, G4, C4, F4));
            tonic_ = (mode_ == Mode.MAJOR) ? order.get((keySignature * -1) - 2) : order.get((keySignature * -1) - 2) - 3;

            for (int i = 0; i < keySignature; ++i)
                if (order.get(i) % 12 == tonic_ % 12)
                    --tonic_;
        }
    }

    /**
     * Getter for Mode
     * @return Mode
     */
    public Mode getMode() {
        return this.mode_;
    }

    /**
     * Getter for Tonic
     * @return Tonic note pitch
     */
    public Integer getTonic() {
        return this.tonic_;
    }

    /**
     * Convert Tonality to string
     * @return Formatted string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (mode_ == Mode.HARMONICMINOR)
            sb.append("Harmonic ");
        else if (mode_ == Mode.MELODICMINOR)
            sb.append("Melodic ");

        sb.append(pitchToString(tonic_, isSharp_)).append(" ");
        if (mode_ == Mode.MAJOR)
            sb.append("Major");
        else
            sb.append("Minor");
        return sb.toString();
    }

    /**
     * Convert a note pitch to a human readable string with English notation (A, B, C...)
     * @param pitch Pitch to convert
     * @param isSharp Specify if the key signature contains sharps or flats (the pitch is not impacted by this value)
     * @return English notation of the note
     */
    public static String pitchToString(int pitch, boolean isSharp) {
        pitch = pitch % 12;
        switch (pitch) {
            case Pitches.C0 % 12:
                return "C";
            case Pitches.CS0 % 12:
                return isSharp ? "C#" : "Db";
            case Pitches.D0 % 12:
                return "D";
            case Pitches.DS0 % 12:
                return isSharp ? "D#" : "Eb";
            case Pitches.E0 % 12:
                return "E";
            case Pitches.F0 % 12:
                return "F";
            case Pitches.FS0 % 12:
                return isSharp ? "F#" : "Gb";
            case Pitches.G0 % 12:
                return "G";
            case Pitches.GS0 % 12:
                return isSharp ? "G#" : "Ab";
            case Pitches.A0 % 12:
                return "A";
            case Pitches.AS0 % 12:
                return isSharp ? "A#" : "Bb";
            case Pitches.B0 % 12:
                return "B";
        }
        return null;
    }
    /**
     * Convert a note pitch to a human readable string with French notation (Do, Re, Mi...)
     * @param pitch Pitch to convert
     * @param isSharp Specify if the key signature contains sharps or flats (the pitch is not impacted by this value)
     * @return French notation of the note
     */
    public static String pitchToFrenchString(int pitch, boolean isSharp) {
        pitch = pitch % 12;
        switch (pitch) {
            case Pitches.C0 % 12:
                return "Do";
            case Pitches.CS0 % 12:
                return isSharp ? "Do#" : "Reb";
            case Pitches.D0 % 12:
                return "Re";
            case Pitches.DS0 % 12:
                return isSharp ? "Re#" : "Mib";
            case Pitches.E0 % 12:
                return "Mi";
            case Pitches.F0 % 12:
                return "Fa";
            case Pitches.FS0 % 12:
                return isSharp ? "Fa#" : "Solb";
            case Pitches.G0 % 12:
                return "Sol";
            case Pitches.GS0 % 12:
                return isSharp ? "Sol#" : "Lab";
            case Pitches.A0 % 12:
                return "La";
            case Pitches.AS0 % 12:
                return isSharp ? "La#" : "Sib";
            case Pitches.B0 % 12:
                return "Si";
        }
        return null;
    }
}
