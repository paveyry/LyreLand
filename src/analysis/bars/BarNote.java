package analysis.bars;

public class BarNote {
    private double startTime_;
    private double duration_;
    private int pitch_;

    public BarNote(double startTime, double duration, int pitch) {
        this.startTime_ = startTime;
        this.duration_ = duration;
        this.pitch_ = pitch;
    }

    public double getStartTime() {
        return startTime_;
    }

    public double getDuration() {
        return duration_;
    }

    public int getPitch() {
        return pitch_;
    }

    public void setStartTime(double startTime_) {
        this.startTime_ = startTime_;
    }

    public void setDuration(double duration_) {
        this.duration_ = duration_;
    }

    public void setPitch(int pitch_) {
        this.pitch_ = pitch_;
    }
}
