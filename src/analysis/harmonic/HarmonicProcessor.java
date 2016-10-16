package analysis.harmonic;

import analysis.bars.Bar;
import analysis.bars.BarLexer;

import java.util.ArrayList;

/**
 * Class that finds all the tonality modulations throughout a score
 */
public class HarmonicProcessor {

    private Tonality tonality_;
    private BarLexer barLexer_;
    private ArrayList<ChordDegree> degreeList_;

    /**
     * HarmonicProcessor constructor
     * @param tonality Main tonality of the score
     * @param barLexer Sequence of bars of the score
     */
    public HarmonicProcessor(Tonality tonality, BarLexer barLexer) {
        tonality_ = tonality;
        barLexer_ = barLexer;
        //Bar bar = barLexer_.getBars().get(11);
        //Tonality t = new Tonality(-1, 1);
        //System.out.println(t); fixme: Use for debug purpose, suppress afterwards.
        //System.out.println(computeBarTonality(bar, t.getKeySignature(), t.getKeyQuality()));
        //System.out.println(closeTonalitySearch(bar, 0, 0, 1));
        //deepTonalitySearch();
        ChordDegreeSequenceExtractor chordDegreeExtrator_ = new ChordDegreeSequenceExtractor(barLexer_, tonality_);
        degreeList_ = chordDegreeExtrator_.getDegreeSequence();
    }

    /**
     * This function is call by the HarmonicProcessor constructor. Its aim is to assign
     * a tonality at each bar of the score.
     */
    private void deepTonalitySearch() {
        int offset = 1;
        for (int i = 0; i < barLexer_.getBars().size(); ++i) {
            Bar bar = barLexer_.getBars().get(i);
            if (i > 0) {
                Tonality previousTonality = barLexer_.getBars().get(i - 1).getTonality();
                Tonality result = computeBarTonality(bar, previousTonality.getKeySignature(),
                                                          previousTonality.getKeyQuality());
                if (result != null) {
                    System.out.println(result + "|| BarNumber: " + i);
                    bar.setTonality(result);
                    continue;
                }
            }
            Tonality result = computeBarTonality(bar, tonality_.getKeySignature(),
                                                      tonality_.getKeyQuality());
            if (result == null) {
                int stop = 0;
                while (result == null && stop < 8) {
                    result = closeTonalitySearch(bar, tonality_.getKeySignature(),
                                                      tonality_.getKeyQuality(), offset);
                    offset = (result == null) ? offset + 1 : 1;
                    ++stop;
                }
            }
            System.out.println(result + "|| BarNumber: " + i);
            bar.setTonality(result);
        }
    }

    /**
     * This function is able to determine if a bar's tonality is the one given in parameter or
     * one of its minor/major relatives.
     * @param bar
     * @param keySignature
     * @param keyQuality
     * @return the matching tonality or null if none is found.
     */
    private Tonality computeBarTonality(Bar bar, int keySignature, int keyQuality) {
        Tonality tonality = new Tonality(keySignature, keyQuality);
        ArrayList<Integer> barPitches = bar.getBarPitches();
        ArrayList<Tonality> tonalities = new ArrayList<>();
        tonalities.add(tonality);
        tonalities.addAll(computeRelativeTonalities(tonality));

        int i = 0;
        boolean isMatching = false;
        while(!isMatching && i < tonalities.size()) {
            isMatching = this.isMatching(tonalities.get(i), barPitches);
            i = (isMatching) ? i : i + 1;
        }
        if (isMatching)
            return tonalities.get(i);
        else
            return null;
    }

    /**
     * This function is used is the initial computeBarTonality fails. It is called on
     * every other closes tonalities existing that are harmonically close
     * from the original tonality.
     * @param bar
     * @param keySignature
     * @param keyQuality
     */
    private Tonality closeTonalitySearch(Bar bar, int keySignature, int keyQuality, int offset) {
        Tonality result = null;
        if (result == null && keySignature < 7 )
            result = computeBarTonality(bar, keySignature + offset, keyQuality);
        if (result == null && keySignature > -7)
            result = computeBarTonality(bar, keySignature - offset, keyQuality);
        return result;
    }

    /**
     * Compute all the relative tonalities of the given tonality
     * @param tonality
     * @return List of relative Tonalities
     */
    private ArrayList<Tonality> computeRelativeTonalities(Tonality tonality) {
        ArrayList<Tonality> relativesTonality = new ArrayList<>();
        Tonality harmonicMinor = new Tonality(tonality.getKeySignature(), 1);
        harmonicMinor.setMode_(Tonality.Mode.HARMONICMINOR);
        Tonality melodicMinor = new Tonality(tonality.getKeySignature(), 1);
        melodicMinor.setMode_(Tonality.Mode.MELODICMINOR);

        relativesTonality.add(harmonicMinor);
        relativesTonality.add(melodicMinor);
        relativesTonality.add(new Tonality(tonality.getKeySignature(), (tonality.getKeyQuality() == 0) ? 1 : 0));
        return relativesTonality;
    }

    /**
     * This function determine is a list of notes is matching a given tonality.
     * @param tonality
     * @param notes
     * @return true is the tonality match se given set of notes. False otherwise
     */
    private boolean isMatching(Tonality tonality, ArrayList<Integer> notes) {
        ArrayList<Integer> tonalityScale = new Scale(tonality, 1).getScale();
        boolean result = true;
        // This case concern only Minor Harmonic and Melodic tonalities
        if (tonality.getKeyQuality() == 1 && tonality.getMode() != Tonality.Mode.MINOR) {
            switch (tonality.getMode()) {
                case HARMONICMINOR:
                    result = notes.contains(tonalityScale.get(tonalityScale.size() - 1) % 12);
                    break;
                case MELODICMINOR:
                    result = notes.contains(tonalityScale.get(tonalityScale.size() - 1) % 12) &&
                             notes.contains(tonalityScale.get(tonalityScale.size() - 2) % 12);
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; result && i < notes.size() ; ++i) {
            result = tonalityScale.contains(notes.get(i));
        }
        return result;
    }

    /**
     * Getter for degreeList
     * @return degreeList of the score.
     */
    public ArrayList<ChordDegree> getDegreeList() {
        return degreeList_;
    }

}
