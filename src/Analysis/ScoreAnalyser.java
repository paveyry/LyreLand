package Analysis;

import jm.music.data.Score;
import jm.util.Read;

import java.util.Arrays;
import java.util.List;

/**
 * Created by olivier on 24/05/16.
 */
public class ScoreAnalyser {
    private String _fileName;
    private Score _score;

    // Meta data of the score.
    private String _title;
    private String _tonality;
    private int _mesureUnit;
    private int _timePerBar;

    public ScoreAnalyser(String midiFile) {
        _score = new Score();
        try {
            Read.midi(_score, midiFile);
            _title = _score.getTitle();
            _tonality = getTonality(_score.getKeySignature(), _score.getKeyQuality());
            _mesureUnit = _score.getDenominator();
            _timePerBar = _score.getNumerator();
        }
        catch (Exception e) {
            System.out.println("Error : ScoreAnalyser midi file can't be found");
        }
    }

    public void printScoreBasicInformations() {
        System.out.println("Music title: " + _title);
        System.out.println("Music tonality: " + _tonality);
    }

    // Return the tonality of the score following format :
    // Tonic Alteration(sharp, flat or none) Mode --> E.g A sharp Major
    public String getTonality(int keySignature, int keyQuality) {
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

    // ---------- GETTERS ----------
    public String getFileName() { return _fileName; }

    public Score getScore() { return _score; }

    public String getTonality() { return _tonality; }

    // ---------- SETTERS ----------
    // None for now
}
