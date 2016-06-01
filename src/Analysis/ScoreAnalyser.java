package Analysis;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import tonality.Scale;

import java.util.*;

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
    private int _partNb;

    // Extracted informations
    private Scale _scale;

    public ScoreAnalyser(String midiFile) {
        _score = new Score();
        try {
            Read.midi(_score, midiFile);
            _title = _score.getTitle();
            _tonality = MetadataExtractor.getTonality(_score.getKeySignature(), _score.getKeyQuality());
            _scale = MetadataExtractor.computeScale(_tonality);
            _barUnit = MetadataExtractor.computeBarUnit(_score.getDenominator());
            _beatsPerBar = _score.getNumerator();
            _partNb = _score.getPartArray().length;
        } catch (Exception e) {
            System.out.println("Error: ScoreAnalyser midi file can't be found");
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
                Collections.sort(temp);
                for (int j = 0; j < temp.size(); j++) {
                    System.out.print(temp.get(j) + ", ");
                }
                System.out.println();
            }
        }
    }

    // ---------- GETTERS ----------
    public String getFileName() { return _fileName; }

    public String getTitle() { return _title; }

    public Score getScore() { return _score; }

    public String getTonality() { return _tonality; }

    public Scale getScale() { return _scale; }

    public double getBarUnit() { return _barUnit; }

    public int getBeatsPerBar() { return _beatsPerBar; }

    public int getPartNb() { return _partNb; }

    // ---------- SETTERS ----------
    // None for now
}
