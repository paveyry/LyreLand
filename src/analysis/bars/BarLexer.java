package analysis.bars;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.ChordDegreeProcessor;
import analysis.metadata.MetadataExtractor;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import tonality.Tonality;

import java.util.ArrayList;
import java.util.Arrays;

public class BarLexer {
    private double barDuration_;
    private ArrayList<Bar> bars_;
    private double quantum_;
    private double r_;
    private Tonality tonality_;
    private int barNumber_;
    private double barUnit_;
    private int beatsPerBar_;

    /**
     * Constructor. Lex the score bar by bar
     * @param score The score to lex
     * @param tonality The tonality of the score
     */
    public BarLexer(Score score, Tonality tonality, double quantum) {
        barUnit_ = MetadataExtractor.computeBarUnit(score.getDenominator());
        beatsPerBar_ = score.getNumerator();
        barDuration_ = barUnit_ * beatsPerBar_;
        bars_ = new ArrayList<>();
        tonality_ = tonality;
        barNumber_ = (int)((score.getEndTime() + barDuration_) / barDuration_);
        quantum_ = quantum;
        lexBarsFromScore(score);
    }

    /**
     * Process the sequence of chord degrees in the lexed score
     * @return the degree sequence of the score
     */
    public ArrayList<ChordDegree> getDegreeSequence() {
        preprocessDegreeExtraction();

        ArrayList<ChordDegree> degrees = new ArrayList<>();
        ChordDegreeProcessor cdp = new ChordDegreeProcessor(tonality_);

        for (Bar bar : bars_)
            degrees.addAll(getDegreesInSubBar(bar, beatsPerBar_, 1, 0, beatsPerBar_, cdp));

        return degrees;
    }

    private ArrayList<ChordDegree> getDegreesInSubBar(Bar bar, int num, int divider, int start, int end,
                                                     ChordDegreeProcessor cdp)
    {
        // Concatenate all notes to use in degree detection
        ArrayList<Integer> notes = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            notes.addAll(bar.getNotesByBeat().get(i));
        }

        // Try to find degree
        ChordDegree cd = cdp.chordToDegree(notes, divider);

        // Return degree if found or if we cannot divide more
        if (cd.getDegree() > 0 || num / divider <= 1)
            return new ArrayList<>(Arrays.asList(cd));

        // Find the smallest divider of num / divider
        int sd;
        for (sd = 2; sd <= num; ++sd)
            if ((num / divider) % sd == 0)
                break;

        ArrayList<ChordDegree> degrees = new ArrayList<>();

        // Call recursively on sub-bars
        for (int i = 0; i < sd; ++i)
            degrees.addAll(getDegreesInSubBar(bar, num, divider * sd, start + i * ((end - start) / sd),
                    start + (i + 1) * ((end - start) / sd) - 1, cdp));

        return degrees;
    }

    /**
     * Split the score in a list of bars
     * @param score
     */
    private void lexBarsFromScore(Score score) {
        for (int i = 0; i < barNumber_; ++i)
            bars_.add(i, new Bar());

        for (Part p : score.getPartArray()) {
            for (Phrase phrase : p.getPhraseArray()) {
                for (int i = 0; i < phrase.length(); ++i) {
                    Note note = phrase.getNote(i);
                    double time = phrase.getNoteStartTime(i);
                    double duration = normalizeRhythm(note.getRhythmValue());
                    if (time % barDuration_ + duration > barDuration_) {
                        BarNote newHalfNote = new BarNote(0.0, (time + duration) % barDuration_, note.getPitch());
                        bars_.get((int)(time / barDuration_) + 1).addNote(newHalfNote);
                        duration = barDuration_ - time;
                    }
                    BarNote newNote = new BarNote(time % barDuration_, duration, note.getPitch());
                    bars_.get((int)(time / barDuration_)).addNote(newNote);
                }
            }
        }
    }

    /**
     * Segment rhythms in quantum-long notes and group notes by beat unit
     */
    private void preprocessDegreeExtraction() {
        for (Bar bar : bars_) {
            bar.segmentRhythms(quantum_);
            bar.groupNotesByBeat(beatsPerBar_, barUnit_);
        }
    }

    /**
     * Correct rhythm errors in MIDI by approximating to the closest real rhythm value
     * @param rhythm
     * @return the normalized rhythm
     */
    private double normalizeRhythm(double rhythm) {
        // truncate three decimals and convert to int.
        r_ += rhythm;
        int result = ((int)(r_ * 10000.0));
        // Rounded to the superior quantum_ multiple.
        int mod = ((int)(quantum_ * 10000.0));
        int rest = result % mod;
        if (rest != 0.0) {
            rest = rest > mod / 2 ? mod - rest : - rest;
            result = result + rest;
        }
        r_ =  (double) - rest / 10000.0;
        return (double) result / 10000.0;
    }

    /**
     * Getter for the Bar list
     * @return The list of lexed bars
     */
    public ArrayList<Bar> getBars() {
        return bars_;
    }
    public int getBarNumber() {
        return barNumber_;
    }
}
