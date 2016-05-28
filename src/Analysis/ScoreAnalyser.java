package Analysis;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import tonality.Scale;

import java.util.*;

import static jm.constants.Pitches.*;

/**
 * Created by olivier on 24/05/16.
 */
public class ScoreAnalyser {
    private String _fileName;
    private Score _score;

    // Basic Meta data of the score.
    private String _title;
    private String _tonality;
    private int _mesureUnit;
    private int _beatsPerBar;

    // Extracted informations
    private Scale _scale;

    public ScoreAnalyser(String midiFile) {
        _score = new Score();
        try {
            Read.midi(_score, midiFile);
            _title = _score.getTitle();
            _tonality = getTonality(_score.getKeySignature(), _score.getKeyQuality());
            _scale = this.computeScale();
            _mesureUnit = _score.getDenominator();
            _beatsPerBar = _score.getNumerator();
        }
        catch (Exception e) {
            System.out.println("Error: ScoreAnalyser midi file can't be found");
        }
    }

    public void printScoreBasicInformations() {
        System.out.println("Music title: " + _title);
        System.out.println("Music tonality: " + _tonality);
        System.out.println("beat per bar: " + _beatsPerBar + "/"+ _mesureUnit);
    }

    // Private functions.

    // This function extract the bar unit according to _score.get(Denominator)
    // EG: 4 -> 1.0 | 2 -> 2.0 | 8 -> 0.5 | 1 -> 4.0
    private double computeBarUnit() {
        int val = _score.getDenominator();
        switch (val) {
            case 1: return 4.0;
            case 2: return 2.0;
            case 4: return 1.0;
            case 8: return 0.5;
        }
        return 1.0;
    }

    // Return the tonality of the score following format :
    // Tonic Alteration(sharp, flat or none) Mode --> E.g A sharp Major
    private String getTonality(int keySignature, int keyQuality) {
        if (keySignature == 0) {
            return (keyQuality == 0)? "C Major":"A minor";
        }
        else {
            boolean isSharp = (keySignature > 0) ? true : false;
            String mode = (keyQuality == 0) ? "Major" : "Minor";
            String cle = (isSharp)?"#":"f";
            String tonic;
            CircularArrayList<String> order = new CircularArrayList<String>(); // Sharp or flat order
            if (isSharp) {
                List<String> sharpList = Arrays.asList(new String[]{"F", "C", "G", "D", "A", "E", "B"});
                order.addAll(sharpList);
                if (mode.equals("Major"))
                    tonic = order.get(keySignature + 1);
                else
                    tonic = order.get(keySignature - 3);
            } else {
                List<String> flatList = Arrays.asList(new String[]{"B", "E", "A", "D", "G", "C", "F"});
                order.addAll(flatList);
                if (mode.equals("Major"))
                    tonic = order.get((keySignature * -1) - 2);
                else
                    tonic = order.get((keySignature * -1) - 3);
            }
            return tonic + " " + cle + " " + mode;
        }
    }

    // This function use _tonality to create the corresponding Scale object
    private Scale computeScale() {
        String[] splited = _tonality.split(" ");
        int tonic = 0;
        switch (splited[0]) {
            case "A": tonic = A4;
                break;
            case "B": tonic = B4;
                break;
            case "C": tonic = C4;
                break;
            case "D": tonic = D4;
                break;
            case "E": tonic = E4;
                break;
            case "F": tonic = F4;
                break;
            case "G": tonic = G4;
                break;
        }
        // Case C Major || A Minor
        if (splited.length == 2)
            return (new Scale(tonic, splited[1], 1));
        else {
            if (splited[2].equals("Minor")) {
                int val = (splited[1].equals("#")) ? 1 : -1;
                tonic += val;
                return (new Scale(tonic, splited[2], 1));
            }
            return (new Scale(tonic, splited[2], 1));
        }
    }

    public void normaliseRythm() {
        Arrays.asList(_score.getPartArray()).stream().forEach(p -> Arrays.asList(p.getPhraseArray()).stream().forEach(ph ->
            Arrays.asList(ph.getNoteArray()).forEach(n -> Utils.normalizeRythmValue(n))));
    }

    // Same function but with print
    public void normaliseRythmPrint() {
        Arrays.asList(_score.getPartArray()).stream().forEach(p -> Arrays.asList(p.getPhraseArray()).stream().forEach(ph -> {
                Arrays.asList(ph.getNoteArray()).forEach(n -> {Utils.normalizeRythmValue(n);
                    System.out.print("(" + n.getRhythmValue() + " " + n.getPitch() + ") ");});
            System.out.println(System.lineSeparator());}));
    }

    public void normalisePhraseLength() {
        // Get the start of the music
        double startTime = _score.getPartArray()[0].getPhraseArray()[0].getStartTime();
        Phrase firstphrase = _score.getPart(0).getPhrase(0);
        for (Part p : _score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                if (startTime > ph.getStartTime()) {
                    startTime = ph.getStartTime();
                    firstphrase = ph;
                }
            }
        }
        // Deal with the firstphrase
        int maxsize = 0;
        for (Part p : _score.getPartArray()) {
            for (Phrase ph : p.getPhraseArray()) {
                if (ph != firstphrase)
                    continue;
                Vector<Note> vector = new Vector<>();
                for (Note n : _score.getPart(0).getPhrase(0).getNoteArray()) {
                    double d = n.getRhythmValue();
                    double quantum = Utils.getQuantum();
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
        for (Part p : _score.getPartArray()) {
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
                    double quantum = Utils.getQuantum();
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

    public void checknpl() {
        System.out.println(" CHECK ");
        int size = _score.getPartArray()[0].getPhraseArray()[0].size();
        for (Part p : _score.getPartArray()) {
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

    // FIXME: Store the result and remove the print.
    public void sequenceChords() {
        for (Part p : _score.getPartArray()) {
            // Get the number of notes in the longuest Phrases.
            int max = 0;
            for(Phrase phr : p.getPhraseArray()) {
                max = (phr.length() > max) ? phr.length() : max;
            }
            for (int i = 0; i < max; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for(Phrase phr : p.getPhraseArray()) {
                    int index = phr.length() - 1 - i;
                    if (index >= 0) {
                        if (phr.getNote(phr.length() - 1 - i).getPitch() != Note.REST)
                            temp.add(phr.getNote(phr.length() - 1 - i).getPitch());
                    }
                }
                if (temp.size() != 0) {
                    Collections.sort(temp);
                    for (int j = 0; j < temp.size(); j++) {
                        System.out.print(temp.get(j) + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    // ---------- GETTERS ----------
    public String getFileName() { return _fileName; }

    public Score getScore() { return _score; }

    public String getTonality() { return _tonality; }

    public Scale getScale() { return _scale; }

    // ---------- SETTERS ----------
    // None for now
}
