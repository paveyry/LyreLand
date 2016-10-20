package training;

import analysis.ScoreAnalyser;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import training.probability.MarkovMatrix;
import training.probability.ProbabilityVector;
import training.rhythmic.RhythmicLearner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GenreLearner {
    private String categoryName_;
    private MarkovMatrix<ChordDegree> markovDegree_;
    private ProbabilityVector<List<ChordDegree>> endingsVector_;
    private ProbabilityVector<Tonality> tonalityVector_;
    private ProbabilityVector<Double> barUnitVector_;
    private ProbabilityVector<Integer> beatPerBarVector_;
    private ProbabilityVector<Integer> barNumberVector_;
    private ProbabilityVector<Double> tempoVector_;
    private HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> rhythmMatrices_;
    private MelodicLearner melodicLearner_;

    /**
     * Class for Music genre learning. Execute all the learning steps on a specific genre.
     * This class can be serialized in XML using the XStream library to create a data file for the generation.
     * @param categoryName Name of the music genre
     */
    public GenreLearner(String categoryName) {
        categoryName_ = categoryName;
        markovDegree_ = new MarkovMatrix<>(2);
        endingsVector_ = new ProbabilityVector<>("ending_degrees");
        tonalityVector_ = new ProbabilityVector<>("tonality");
        barUnitVector_ = new ProbabilityVector<>("barUnit");
        beatPerBarVector_ = new ProbabilityVector<>("beatPerBar");
        barNumberVector_ = new ProbabilityVector<>("barNumber");
        tempoVector_ = new ProbabilityVector<>("tempo");
        rhythmMatrices_ = new HashMap<>();
        melodicLearner_ = new MelodicLearner();
    }

    /**
     * Learn data from a specific training example.
     * This method calls all the appropriate methods from attribute learners to fill them with the data
     * from this example
     * @param scoreAnalyser The training example
     */
    public void learnExample(ScoreAnalyser scoreAnalyser) {
        ArrayList<ChordDegree> degreeList = scoreAnalyser.getDegreeList();
        for(ChordDegree chordDegree : degreeList)
            if (chordDegree.getDegree() != 0)
                markovDegree_.addEntry(chordDegree);
        markovDegree_.resetContext();
        if (degreeList.get(degreeList.size() - 3 ).getDegree()!=0 &&
                degreeList.get(degreeList.size() - 2).getDegree() != 0
                && degreeList.get(degreeList.size() - 1).getDegree() != 0)
            endingsVector_.addEntry(Arrays.asList(degreeList.get(degreeList.size() - 3),
                                                                 degreeList.get(degreeList.size() - 2),
                                                                 degreeList.get(degreeList.size() - 1)));
        tonalityVector_.addEntry(scoreAnalyser.getTonality());
        barUnitVector_.addEntry(scoreAnalyser.getBarUnit());
        beatPerBarVector_.addEntry(scoreAnalyser.getBeatsPerBar());
        barNumberVector_.addEntry(scoreAnalyser.getBarNumber());
        tempoVector_.addEntry(scoreAnalyser.getTempo());
        RhythmicLearner.learn(scoreAnalyser, rhythmMatrices_);
        melodicLearner_.learnExample(scoreAnalyser);
    }

    // GETTERS / SETTERS

    /**
     * Getter for the categoryName class attribute.
     * @return categoryName_
     */
    public String getCategoryName() {
        return categoryName_;
    }

    /**
     * Getter for the markovDegree class attribute.
     * @return markovDegree_
     */
    public MarkovMatrix<ChordDegree> getMarkovDegree() {
        return markovDegree_;
    }

    /**
     * Getter for the tonalityVector class attribute.
     * @return tonalityVector_
     */
    public ProbabilityVector<Tonality> getTonalityVector() {
        return tonalityVector_;
    }

    /**
     * Getter for the barUnitVector class attribute.
     * @return barUnitVector_
     */
    public ProbabilityVector<Double> getBarUnitVector() {
        return barUnitVector_;
    }

    /**
     * Getter for the beatPerBar class attribute.
     * @return beatPerBarVector_
     */
    public ProbabilityVector<Integer> getBeatPerBarVector() {
        return beatPerBarVector_;
    }

    /**
     * Getter for the barNumber class attribute.
     * @return barNumberVector
     */
    public ProbabilityVector<Integer> getBarNumberVector() {
        return barNumberVector_;
    }

    /**
     * Getter for the barNumber class attribute.
     * @return tempoVector_
     */
    public ProbabilityVector<Double> getTempoVector() {
        return tempoVector_;
    }

    /**
     * Getter for the endingsVector class attribute
     * @return endingsVector_
     */
    public ProbabilityVector<List<ChordDegree>> getEndingsVector() {
        return endingsVector_;
    }

    /**
     * Getter for the rhythmMatrices class attribute
     * @return rhythmMatrices_
     */
    public HashMap<ArrayList<ChordDegree>, MarkovMatrix<Double>> getRhythmMatrices_() {
        return rhythmMatrices_;
    }
}
