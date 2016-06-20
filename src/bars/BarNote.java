package bars;

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
}
