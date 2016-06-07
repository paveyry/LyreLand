package tonality;

import jm.constants.Pitches;

public class Tonality {
    public enum Mode {
        MAJOR,
        MINOR,
        HARMONICMINOR,
        MELODICMINOR
    }

    private Integer tonic_;
    private Mode mode_;
    private Boolean isSharp_; // Already applied on the tonic pitch. Just useful to distinguish C sharp from D flat

    public Tonality(int tonic, Mode mode, boolean isSharp) {
        this.tonic_ = tonic;
        this.mode_ = mode;
        this.isSharp_ = isSharp;
    }

    public Mode getMode() {
        return this.mode_;
    }

    public Integer getTonic() {
        return this.tonic_;
    }

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
}
