package analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olivier on 16/05/16.
 */
public class VerticalBand {
    // This class is used to store Chords informations
    // extracted by the Chord Extractor class.
    // (The dynamic and the articulations are stored
    // for later use.)

    private ArrayList<Integer> pitches_;
    private double rythm_; // Only one rythm for the all band.
    private ArrayList<Double> duration_;
    private ArrayList<Integer> dynamic_;

    public VerticalBand(ArrayList<Integer> pitches, double rythm,
                        ArrayList<Double> duration, ArrayList<Integer> dynamic) {
        pitches_ = pitches;
        rythm_ = rythm;
        duration_ = duration;
        dynamic_ = dynamic;
    }

    // This function return true if the pitches, dynamics
    // and articulations are equal.
    public boolean isEqual(VerticalBand ac) {
        if (this.pitches_.equals(ac.getPitches()))
            if (this.dynamic_.equals(ac.getDynamic()))
                if (this.duration_.equals(ac.getDuration()))
                    return true;
        return false;
    }

    public void printVerticalBand(){
        System.out.print("Pitches: [ ");
        for (Integer i: pitches_)
            System.out.print(i + " ");
        System.out.print("] || Rhythm = " + rythm_ + " || Duration: [ ");
        for (Double i : duration_)
            System.out.print(i + " ");
        System.out.print("] || Dynamic: [ ");
        for (Integer i : dynamic_)
            System.out.print(i + " ");
        System.out.println("]");
    }

    // ---------- Getters ----------
    public ArrayList<Integer> getPitches() { return pitches_; }
    public double getRythm() { return rythm_; }
    public ArrayList<Integer> getDynamic() { return dynamic_; }
    public ArrayList<Double> getDuration() { return duration_; }

    // ---------- Setters ----------
    public void setRythm(double r) {
        rythm_ = r;
    }


    // Covert ArrayList<Integer> to int[] (in case we need it ...)
    public int[] cI(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}