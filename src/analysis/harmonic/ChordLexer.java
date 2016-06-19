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

    // Fixme : return a ArrayList<ChordDegree> (all instruments)
    private ArrayList<ChordDegree> sequenceBars(ArrayList<ArrayList<VerticalBand>> sequencedChords) {
        // The variable result is the list of degree of the score based
        // on every instrument parts.
        ArrayList<ChordDegree> result = new ArrayList<>();
        // The variable bars represent the bars of all the instrument which will
        // later be used by processBarDegrees function.
        ArrayList<ArrayList<VerticalBand>> bars = new ArrayList<>();
        // Bar represent the bar of each instrument, used to fill bars.
        ArrayList<VerticalBand> bar = new ArrayList<>();
        double barCount = 0.0;

        ArrayList<Integer> indexes = new ArrayList<>();
        for (ArrayList<VerticalBand> part : sequencedChords)
            indexes.add(0);
        while (!indexes.isEmpty()) {
            for (int i = 0; i < indexes.size(); i++) {
                while ((barCount < beatPerBar_ * barUnit_) && indexes.get(i) < sequencedChords.get(i).size()) {
                    VerticalBand temp = sequencedChords.get(i).get(indexes.get(i));
                    if (barCount + temp.getRythm() < (beatPerBar_ * barUnit_)) {
                        bar.add(temp);
                        barCount += temp.getRythm();
                        indexes.set(i, indexes.get(i) + 1);
                    }
                    else if (barCount + temp.getRythm() == (beatPerBar_ * barUnit_)) {
                        bar.add(temp);
                        bars.add(bar);
                        bar = new ArrayList<>();
                        barCount += temp.getRythm();
                    }
                }
                barCount = 0.0;
                if (indexes.get(i) >= sequencedChords.get(i).size()) {
                    indexes.remove(i);
                    i--;
                }
            }
            result.addAll(processBarDegree(bars, beatPerBar_, 1));
            bars.clear();
        }
        return result;
        /*ArrayList<Integer> sizes = new ArrayList<>();
        for (int i = 0; i < sequencedChords.size(); i++)
            sizes.add(sequencedChords.get(i).size());*/

        // Iterate while all the bars of all the parts have not been processed.
        /*while (sizes.size() != 0) {
            // Iterate through each part to collect the bars from the end of the score.
            for (int i = 0; i < sizes.size(); i++) {
                int j = 1;
                // Iterate to collect enough VerticalBand to fill a Bar for a certain Part.
                while (barCount < beatPerBar_ * barUnit_ && j <= sizes.get(i)) {
                    VerticalBand temp = sequencedChords.get(i).get(sizes.get(i) - j);
                    if (barCount + temp.getRythm() < (beatPerBar_ * barUnit_)) {
                        bar.add(temp);
                        barCount += temp.getRythm();
                    }
                    else if (barCount + temp.getRythm() == (beatPerBar_ * barUnit_)) {
                        bar.add(temp);
                        bars.add(bar);
                        bar = new ArrayList<>();
                        barCount += temp.getRythm();
                    }
                    j++;
                }
                barCount = 0.0;
                // If we have reach the end of a part, it is removed from sizes
                // to stop iterating over it.
                sizes.set(i, sizes.get(i) - j);
                if (sizes.get(i) <= 0) {
                    sizes.remove(i);
                    i--;
                }
            }
            result.addAll(processBarDegree(bars, beatPerBar_, 1));
            bars.clear();
        }*/
    }

    private ArrayList<ChordDegree> processBarDegree(ArrayList<ArrayList<VerticalBand>> bars, int beatCounter, int barFraction) {
        ArrayList<ChordDegree> result = new ArrayList<>();
        ArrayList<Integer> pitches = new ArrayList<>();

        // fusedBar is temporary and used only by ChordDegreeProcessor.
        // Fusion of same chords in the bar and adding them to the pitches
        // That will be given to ChordDegreeProcessor.
        for (ArrayList<VerticalBand> bar :  bars) {
            ArrayList<VerticalBand> fusedBar = new ArrayList<>();
            for (VerticalBand v : bar)
                fusedBar.add(v.clone());
            for (int i = 0; i < fusedBar.size() - 2; i++) {
                if (fusedBar.get(i).equals(fusedBar.get(i + 1))) {
                    fusedBar.get(i).setRythm(fusedBar.get(i).getRythm() + fusedBar.get(i + 1).getRythm());
                    fusedBar.remove(i + 1);
                }
            }
            for (VerticalBand v : fusedBar)
                pitches.addAll(v.getPitches());
        }

        ChordDegree temp = new ChordDegreeProcessor(tonality_).chordToDegree(pitches, barFraction);
        if (temp.getDegree() == 0) {
            if (beatCounter == 1)
                result.add(temp);
            else {
                int quotient = 0;
                if (beatCounter % 2 == 0) {
                    beatCounter /= 2;
                    quotient = 2;
                    int index = 0;
                    for (ArrayList<VerticalBand> bar : bars) {
                        if (bar.size() > index)
                            index = bar.size();
                    }
                    index /= 2;
                    ArrayList<ArrayList<VerticalBand>> subBars1 = new ArrayList<>();
                    ArrayList<ArrayList<VerticalBand>> subBars2 = new ArrayList<>();
                    for (ArrayList<VerticalBand> bar : bars) {
                        subBars1.add(new ArrayList<>(bar.subList(0, index)));
                        subBars2.add(new ArrayList<>(bar.subList(index, bar.size())));
                    }
                    result.addAll(processBarDegree(subBars1, beatCounter, barFraction * quotient));
                    result.addAll(processBarDegree(subBars2, beatCounter, barFraction * quotient));
                }
                else if (beatCounter % 3 == 0) {
                    beatCounter /= 3;
                    quotient = 3;
                    int index = 0;
                    for (ArrayList<VerticalBand> bar : bars) {
                        if (bar.size() > index)
                            index = bar.size();
                    }
                    index /= 3;
                    ArrayList<ArrayList<VerticalBand>> subBars1 = new ArrayList<>();
                    ArrayList<ArrayList<VerticalBand>> subBars2 = new ArrayList<>();
                    ArrayList<ArrayList<VerticalBand>> subBars3 = new ArrayList<>();
                    for (ArrayList<VerticalBand> bar : bars) {
                        subBars1.add(new ArrayList<>(bar.subList(0, index)));
                        subBars2.add(new ArrayList<>(bar.subList(index, index * 2)));
                        subBars3.add(new ArrayList<>(bar.subList(index * 2, bar.size())));
                    }
                    result.addAll(processBarDegree(subBars1, beatCounter, barFraction * quotient));
                    result.addAll(processBarDegree(subBars2, beatCounter, barFraction * quotient));
                    result.addAll(processBarDegree(subBars3, beatCounter, barFraction * quotient));
                }
            }
        }
        else
            result.add(temp);
        return result;
    }
}
