package analysis.harmonic;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import tonality.Tonality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class ChordLexer {

    private double r_ = 0;
    private Score score_;
    private double quantum_;
    private double barUnit_;
    private int beatPerBar_;
    private Tonality tonality_;

    public ChordLexer(Score score, double barUnit, int beatPerBar, Tonality tonality) {
        this.score_ = score;
        this.quantum_ = init_quantun();
        this.barUnit_ = barUnit;
        this.beatPerBar_ = beatPerBar;
        this.tonality_ = tonality;
    }

    // Public method
    public ArrayList<ChordDegree> sequenceDegree() {
        normaliseRhythm();
        normalisePhraseLength();
        return sequenceBars(sequenceChords());
    }

    // Private method
    private double getQuantum() { return quantum_; }

    private double init_quantun() {
        double srm = score_.getShortestRhythmValue();
        if (srm < (0.0625 - 0.03125 / 2))
            return 0.03125;
        if (srm < (0.125 - 0.0625 / 2))
            return 0.0625;
        if (srm < (0.250 - 0.125 / 2))
            return 0.125;
        if (srm < (0.500 - 0.250 / 2))
            return 0.250;
        if (srm < (1 - 0.5 / 2))
            return 0.500;
        if (srm < (2.0 - 1.0 / 2.0))
            return 1.00;
        if (srm < (4.0 - 2.0 / 2.0))
            return 2.00;
        if (srm < (8.0 - 4.0 / 2.0))
            return 4.00;
        return 8.00;
    }

    /**
     * Normalize a double rhythm value according to MIDI rhythm reading
     * E.G 1.251 -> 1.5.
     * @param n Note
     */
    private void normalizeRhythmValue(Note n) {
        // truncate three decimals and convert to int.
        r_ += n.getRhythmValue();
        int result = ((int)(r_ * 10000));
        // Rounded to the superior 250 multiple.
        int mod = ((int)(quantum_ * 10000));
        int rest = result % mod;
        if (rest != 0) {
            rest = rest > mod / 2 ? mod - rest : - rest;
            result = result + rest;
        }
        n.setRhythmValue((double) result / 10000);
        r_ =  (double) - rest / 10000;
    }

    private void normaliseRhythm() {
        Arrays.asList(score_.getPartArray()).stream().forEach(p -> Arrays.asList(p.getPhraseArray()).stream().forEach(ph ->
                Arrays.asList(ph.getNoteArray()).forEach(n -> normalizeRhythmValue(n))));
    }


    private void normalisePhraseLength() {
        // Counteract the midi rythm staking effect of two following
        // similar notes : (C4, 4.0) and (C4, 2.0) will be transformed
        // into one note (C4, 6.0).
        // We want to redivide the notes according to bar beat value.
        for (Part p : score_.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                Vector<Note> vector = new Vector<>();
                for (Note n : ph.getNoteArray()) {
                    double d = n.getRhythmValue();
                    double quantum = getQuantum();
                    while (d >= quantum) {
                        Note tmp = n.copy();
                        tmp.setRhythmValue(quantum);
                        vector.add(tmp);
                        d -= quantum;
                    }
                }
                ph.setNoteList(vector);
            }
        }
    }

    // (An ArrayList<VerticalBand> per Parts.)
    private ArrayList<ArrayList<VerticalBand>> sequenceChords() {
        ArrayList<ArrayList<VerticalBand>> result = new ArrayList<>();
        for (Part p : score_.getPartArray()) {
            ArrayList<VerticalBand> vBand = new ArrayList<>();
            int max = 0;
            // Get the maximum length of the partArray phrases.
            for (Phrase phr : p.getPhraseArray()) {
                max = (phr.length() > max) ? phr.length() : max;
            }
            for (int i = 0; i < max; i++) {
                ArrayList<Integer> pitches = new ArrayList<>();
                double rythm = quantum_;
                ArrayList<Integer> dynamic = new ArrayList<>();
                ArrayList<Double> duration = new ArrayList<>();
                for (Phrase phr: p.getPhraseArray()) {
                    int index = phr.length() - 1 - i;
                    // Phrases don't have the same lenght
                    if (index >= 0) {
                        if (phr.getNote(phr.length() - 1 - i).getPitch() != Note.REST &&
                                !pitches.contains(phr.getNote(phr.length() - 1 - i).getPitch())) {
                            pitches.add(phr.getNote(phr.length() - 1 - i).getPitch());
                            dynamic.add(phr.getNote(phr.length() - 1 - i).getDynamic());
                            duration.add(phr.getNote(phr.length() - 1 - i).getDuration());
                        }
                    }
                }
                VerticalBand temp = new VerticalBand(pitches, rythm, duration, dynamic);
                if (temp.getPitches().size() > 0)
                    vBand.add(temp);
            }
            result.add(vBand);
        }
        return result;
    }

    private ArrayList<ChordDegree> sequenceBars(ArrayList<ArrayList<VerticalBand>> sequencedChords) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ArrayList<VerticalBand> bar = new ArrayList<>();
        double barCount = 0.0;
        for (ArrayList<VerticalBand> vbArray : sequencedChords) {
            for (int i = vbArray.size() - 1; i >=0; i--) {
                VerticalBand temp = vbArray.get(i);
                if (barCount + temp.getRythm() < (beatPerBar_ * barUnit_)) {
                    bar.add(temp);
                    barCount += temp.getRythm();
                }
                else if (barCount + temp.getRythm() == (beatPerBar_ * barUnit_)) {
                    bar.add(temp);
                    result.addAll(processBarDegree(bar, beatPerBar_, 1));
                    bar.clear();
                    barCount = 0.0;
                }
                else {
                    double difference = (beatPerBar_ * barUnit_) - barCount;
                    VerticalBand barSeparation = new VerticalBand(temp.getPitches(), difference,
                                                                  temp.getDuration(), temp.getDynamic());
                    bar.add(barSeparation);
                    result.addAll(processBarDegree(bar, beatPerBar_, 1));
                    temp.setRythm(temp.getRythm() - difference);
                    barCount = temp.getRythm();
                    bar.clear();
                    bar.add(temp);
                }
            }
        }
        return result;
    }

    private ArrayList<ChordDegree> processBarDegree(ArrayList<VerticalBand> bar, int beatCounter, int barFraction) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ArrayList<Integer> pitches = new ArrayList<>();
        for (VerticalBand v : bar)
            pitches.addAll(v.getPitches());
        ChordDegree temp = new ChordDegreeProcessor(tonality_).chordToDegree(pitches, barFraction);
        if (temp.getDegree() == 0) {
            if (beatCounter == 1)
                result.add(temp);
            else {
                int quotient = 0;
                if (beatCounter % 2 == 0) {
                    beatCounter /= 2;
                    quotient = 2;
                }
                else if (beatCounter % 3 == 0) {
                    beatCounter /= 3;
                    quotient = 3;
                }
                int index = (int)(barUnit_ / quantum_) * beatCounter;
                // Adding starting from the end to respect the main traversal.
                result.addAll(processBarDegree((ArrayList<VerticalBand>) bar.subList(index, bar.size()),
                        beatCounter, barFraction * quotient));
                result.addAll(processBarDegree((ArrayList<VerticalBand>) bar.subList(0, index),
                        beatCounter, barFraction * quotient));
            }
        }
        else
            result.add(temp);
        return result;
    }
}
