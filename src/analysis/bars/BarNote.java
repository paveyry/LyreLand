package analysis.bars;

/**
 * Class that describes a note with duration values relative to its bar
 */
public class BarNote {
    private double startTime_;
    private double rhythm_;
    private int pitch_;

    /**
     * Constructor for BarNote
     * @param startTime Start time of the note (in beats), from the beginning of the bar
     * @param rhythm Rhythm value of the note
     * @param pitch Pitch the note
     */
    public BarNote(double startTime, double rhythm, int pitch) {
        this.startTime_ = startTime;
        this.rhythm_ = rhythm;
        this.pitch_ = pitch;
    }

    public double getStartTime() {
        return startTime_;
    }

    public double getDuration() {
        return rhythm_;
    }

    public int getPitch() {
        return pitch_;
    }

    public void setStartTime(double startTime) {
        this.startTime_ = startTime_;
    }

    public void setDuration(double rhythm) {
        this.rhythm_ = rhythm;
    }

    public void setPitch(int pitch_) {
        this.pitch_ = pitch_;
    }
}
