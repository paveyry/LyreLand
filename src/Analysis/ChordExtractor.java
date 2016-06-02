package Analysis;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by olivier on 02/06/16.
 */

//Note n = new Note(C4, CROTCHET, MF, PAN_CENTRE, CROTCHET * LEGATO);

public class ChordExtractor {

    // FIXME: Store the result and remove the print.
    public static void sequenceChords(Score score) {
        for (Part p : score.getPartArray()) {
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
}
