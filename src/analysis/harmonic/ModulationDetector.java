package analysis.harmonic;

import analysis.bars.Bar;
import analysis.bars.BarLexer;
import java.util.*;

/**
 * This Class aim to find all tonality possible for all the bars of the score.
 */
public class ModulationDetector {
    private Tonality previous_;
    private ArrayList<Tonality> previousList_;
    private BarLexer barLexer_;

    /**
     * Constructor of ModulationDetector
     * @param generalTonality generalTonality
     * @param barlexer barlexer
     */
    public ModulationDetector(Tonality generalTonality, BarLexer barlexer) {
        previous_ = generalTonality;
        previousList_ = new ArrayList<>();
        previousList_.add(previous_);
        barLexer_ = barlexer;
    }

    public ArrayList<Tonality> computeTonalities() {
        ArrayList<Tonality> barModulations = new ArrayList<>();
        for (Bar bar : barLexer_.getBars()) {
            Set<Tonality> tonalities = new HashSet<>();
            for (int i = 0; i < 15; ++i) {
                if (previous_.getKeySignature() + i < 7)
                    tonalities.addAll(computeBarTonality(bar, previous_.getKeySignature() + i,
                                                              previous_.getKeyQuality()));
                if (previous_.getKeySignature() - i > -7)
                    tonalities.addAll(computeBarTonality(bar, previous_.getKeySignature() - i,
                                                              previous_.getKeyQuality()));
            }
            ArrayList<Tonality> tonalities1 = new ArrayList<>();
            tonalities1.addAll(tonalities); // Set to ArrayList (easier to iterate on values)
            barModulations.add(findTonalityModulation(tonalities1, bar.getBarPitches()));
        }
        return barModulations;
    }


    // UTILS FUNCTIONS

    private Tonality findTonalityModulation(ArrayList<Tonality> tonalities, ArrayList<Integer> notes) {
        if (tonalities.contains(previous_) || tonalities.size() == 0)
            return previous_;
        else {
            ArrayList<Double> tonalityProbabilities = new ArrayList<>();
            for (int i = 0; i < tonalities.size(); ++i)
                tonalityProbabilities.add(tonalityModulationProbability(previous_, tonalities.get(i), notes));

            double max = Collections.max(tonalityProbabilities);
            ArrayList<Tonality> mostProbable = new ArrayList<>();
            for (int i = 0; i < tonalityProbabilities.size(); ++i)
                if (tonalityProbabilities.get(i) == max)
                    mostProbable.add(tonalities.get(i));
            previous_ = computeBestSuited(mostProbable);
            if (!previousList_.contains(previous_))
                previousList_.add(previous_);
            return previous_;
        }
    }

    private Tonality computeBestSuited(ArrayList<Tonality> mostProbable) {
        if (mostProbable.size() == 1)
            return mostProbable.get(0);
        else {
            for (int i = 0; i < mostProbable.size(); ++i) {
                if (previousList_.contains(mostProbable.get(i)))
                    return mostProbable.get(i);
            }
            int minDistance = 100;
            for (int i = 0; i < mostProbable.size(); ++i)
                if (distance(previous_, mostProbable.get(i)) < minDistance)
                    minDistance = distance(previous_, mostProbable.get(i));
            ArrayList<Tonality> closestTonalities = new ArrayList<>();
            for (int i = 0; i < mostProbable.size(); ++i)
                if (distance(previous_, mostProbable.get(i)) == minDistance)
                    closestTonalities.add(mostProbable.get(i));
            if (closestTonalities.size() == 1)
                return closestTonalities.get(0);
            else
                for (int i = 0; i < closestTonalities.size(); ++i)
                    if (closestTonalities.get(i).getMode() == previous_.getMode())
                        return closestTonalities.get(i);
            return closestTonalities.get(0);
        }
    }

    /**
     * This function compute the distance between two tonalities t1 and t2
     * based on the number of sharp/flat.
     * @param t1 t1
     * @param t2 t2
     * @return the distance
     */
    private int distance(Tonality t1, Tonality t2) {
        return Math.abs(t1.getKeySignature() - t2.getKeySignature());
    }

    /**
     * This function return the probability of a modulation between the Tonality t1 and t2
     * given a corpus of notes. The probability is computed using :
     * number of present constitutive notes / all constitutive notes of the modulation.
     * @param t1 t1
     * @param t2 t2
     * @param notes notes
     * @return the modulation probability
     */
    private double tonalityModulationProbability(Tonality t1, Tonality t2, ArrayList<Integer> notes) {
        ArrayList<Integer> constitutiveNotes = computeConstitutiveNotes(t1, t2);
        // The counter is increased only the first time we encounter a certain constitutive note.
        if (constitutiveNotes.size() == 0)
            return 0.0;
        ArrayList<Integer> encountered = new ArrayList<>();
        double counter = 0.0;
        for (int i = 0; i < notes.size(); ++i)
            if (constitutiveNotes.contains(notes.get(i)))
                if (!encountered.contains(notes.get(i))) {
                    ++counter;
                    encountered.add(notes.get(i));
                }
        return (counter / constitutiveNotes.size()) * 100;
    }

    /**
     * This function return the constitutive notes of a modulation form the origin
     * tonality t1 and the Tonality t2
     * @param t1 t1
     * @param t2 t2
     */
    private ArrayList<Integer> computeConstitutiveNotes(Tonality t1, Tonality t2) {
        ArrayList<Integer> result = new ArrayList<>();
        Scale scale1 = new Scale(t1, 1);
        Scale scale2 = new Scale(t2, 1);
        for (Integer i : scale2.getScale())
            if (!scale1.getScale().contains(i))
                result.add(i);
        return result;
    }


    /**
     * This function is able to determine if a bar's tonality is the one given in parameter or
     * one of its minor/major relatives. (returns all matching tonalities in an Array)
     * If no tonality match the bar note sequences, the length of the array is 0.
     * @param bar bar
     * @param keySignature keySignature
     * @param keyQuality keyQuality
     * @return the matching tonality or null if none is found.
     */
    private Set<Tonality> computeBarTonality(Bar bar, int keySignature, int keyQuality) {
        Set<Tonality> results = new HashSet<>();
        Tonality tonality = new Tonality(keySignature, keyQuality);
        ArrayList<Integer> barPitches = bar.getBarPitches();
        ArrayList<Tonality> tonalities = new ArrayList<>();
        tonalities.add(tonality);
        tonalities.addAll(computeRelativeTonalities(tonality));

        for (Tonality t : tonalities)
            if (isMatching(t, barPitches))
                results.add(t);

        return results;
    }

    /**
     * Compute all the relative tonalities of the given tonality
     * @param tonality tonality
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
     * @param tonality tonality
     * @param notes notes
     * @return true is the tonality match the given set of notes. False otherwise
     */
    private boolean isMatching(Tonality tonality, ArrayList<Integer> notes) {
        ArrayList<Integer> tonalityScale = new Scale(tonality, 1).getScale();
        boolean result = true;
        // This case concern only Minor Harmonic and Melodic tonalities
        if (tonality.getKeyQuality() == 1 && tonality.getMode() != Tonality.Mode.MINOR)
            switch (tonality.getMode()) {
                case HARMONICMINOR:
                    result = !notes.contains(tonalityScale.get(tonalityScale.size() - 1) - 1 % 12);
                    break;
                case MELODICMINOR:
                    result = (!(notes.contains(tonalityScale.get(tonalityScale.size() - 1) - 1 % 12)) &&
                            (!notes.contains(tonalityScale.get(tonalityScale.size() - 2) - 1 % 12)));
                    break;
                default:
                    break;
            }
        for (int i = 0; result && i < notes.size() ; ++i)
            result = tonalityScale.contains(notes.get(i));
        return result;
    }
}
