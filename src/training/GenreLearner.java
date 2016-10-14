package training;

import analysis.ScoreAnalyser;
import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import training.probability.MarkovMatrix;
import training.probability.ProbabilityVector;

import java.util.ArrayList;

public class GenreLearner {
    private String categoryName_;
    private MarkovMatrix<ChordDegree> markovDegree_;
    private ProbabilityVector<Tonality> tonalityVector_;
    private ProbabilityVector<Double> barUnitVector_;
    private ProbabilityVector<Integer> beatPerBarVector_;
    private ProbabilityVector<Integer> barNumberVector_;
    private ProbabilityVector<Double> tempoVector_;

    /**
     * Class for Music genre learning. Execute all the learning steps on a specific genre.
     * This class can be serialized in XML using the XStream library to create a data file for the generation.
     * @param categoryName Name of the music genre
     */
    public GenreLearner(String categoryName) {
        categoryName_ = categoryName;
        markovDegree_ = new MarkovMatrix<>(2);
        tonalityVector_ = new ProbabilityVector<>("tonality");
        barUnitVector_ = new ProbabilityVector<>("barUnit");
        beatPerBarVector_ = new ProbabilityVector<>("beatPerBar");
        barNumberVector_ = new ProbabilityVector<>("barNumber");
        tempoVector_ = new ProbabilityVector<>("tempo");
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
            markovDegree_.addEntry(chordDegree);
        tonalityVector_.addEntry(scoreAnalyser.getTonality());
        barUnitVector_.addEntry(scoreAnalyser.getBarUnit());
        beatPerBarVector_.addEntry(scoreAnalyser.getBeatsPerBar());
        barNumberVector_.addEntry(scoreAnalyser.getBarNumber());
        tempoVector_.addEntry(scoreAnalyser.getTempo());
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

}
