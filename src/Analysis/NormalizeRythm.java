package analysis;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by olivier on 01/06/16.
 */
public class NormalizeRythm {

    private static double r_ = 0;
    private static double quantum_ = 0.0625;

    // Normalize a double rythm value according to
    // midi rythm reading.
    // E.G 1.251 -> 1.5.
    public static void normalizeRythmValue(Note n) {
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

    public static double getQuantum() {
        return quantum_;
    }


    public static void normaliseRythm(Score score) {
        Arrays.asList(score.getPartArray()).stream().forEach(p -> Arrays.asList(p.getPhraseArray()).stream().forEach(ph ->
                Arrays.asList(ph.getNoteArray()).forEach(n -> normalizeRythmValue(n))));
    }

    // Same function but with print
    public static void normaliseRythmPrint(Score score) {
        Arrays.asList(score.getPartArray()).stream().forEach(p -> Arrays.asList(p.getPhraseArray()).stream().forEach(ph -> {
            Arrays.asList(ph.getNoteArray()).forEach(n -> {normalizeRythmValue(n);
                System.out.print("(" + n.getRhythmValue() + " " + n.getPitch() + ") ");});
            System.out.println(System.lineSeparator());}));
    }

    public static void normalisePhraseLength(Score score) {
        // Get the start of the music
        double startTime = score.getPartArray()[0].getPhraseArray()[0].getStartTime();
        Phrase firstphrase = score.getPart(0).getPhrase(0);
        for (Part p : score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                if (startTime > ph.getStartTime()) {
                    startTime = ph.getStartTime();
                    firstphrase = ph;
                }
            }
        }
        // Deal with the firstphrase
        int maxsize = 0;
        for (Part p : score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                if (ph != firstphrase)
                    continue;
                Vector<Note> vector = new Vector<>();
                for (Note n : score.getPart(0).getPhrase(0).getNoteArray()) {
                    double d = n.getRhythmValue();
                    double quantum = getQuantum();
                    while (d > quantum) {
                        Note tmp = n.copy();
                        tmp.setRhythmValue(quantum);
                        vector.add(tmp);
                        d -= quantum;
                    }
                    Note tmp = n.copy();
                    tmp.setRhythmValue(d);
                    vector.add(tmp);
                }
                ph.setNoteList(vector);
                maxsize = ph.getNoteArray().length;
            }
        }
        for (Part p : score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                if (ph == firstphrase)
                    continue;
                Vector<Note> vector = new Vector<>();
                // Counteract the midi rythm staking effect of two following
                // similar notes : (C4, 4.0) and (C4, 2.0) will be transformed
                // into one note (C4, 6.0).
                // We want to redivide the notes according to bar beat value.
                for (int i = ph.getNoteArray().length - 1; i >= 0; i--) {
                    Note n = ph.getNote(i);
                    double d = n.getRhythmValue();
                    double quantum = getQuantum();
                    while (d > quantum) {
                        Note tmp = n.copy();
                        tmp.setRhythmValue(quantum);
                        vector.add(tmp);
                        d -= quantum;
                    }
                    Note tmp = n.copy();
                    tmp.setRhythmValue(d);
                    vector.add(tmp);
                }
                /*
                // Make all phrase begin at the same time and add silence
                ph.setStartTime(startTime);
                for (int i = vector.size(); i < maxsize; i++) {
                    Note tmp = ph.getNote(0).copy();
                    tmp.setRhythmValue(Utils.getQuantum());
                    tmp.setPitch(REST);
                    vector.add(tmp);
                }
                */
                Collections.reverse(vector);
                ph.setNoteList(vector);
            }
        }
    }

    public static void checknpl(Score score) {
        System.out.println(" CHECK ");
        int size = score.getPartArray()[0].getPhraseArray()[0].size();
        for (Part p : score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                System.out.println("Size : " + ph.size());
                System.out.println("Start : " + ph.getStartTime());
                for (Note n : ph.getNoteArray()) {
                    System.out.print("(" + n.getRhythmValue() + " " + n.getPitch() + ") ");
                }
                System.out.println(System.lineSeparator());
            }
        }
    }
}
