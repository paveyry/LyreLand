package training;

import analysis.ScoreAnalyser;
import analysis.harmonic.ChordDegree;
import training.MarkovChain.MarkovDegree;

import java.util.ArrayList;

public class GenreLearner {
    private String categoryName_;
    private MarkovDegree markovDegree_;

    /**
     * Class for Music genre learning. Execute all the learning steps on a specific genre.
     * This class can be serialized in XML using the XStream library to create a data file for the generation.
     * @param categoryName Name of the music genre
     */
    public GenreLearner(String categoryName) {
        categoryName_ = categoryName;
        markovDegree_ = new MarkovDegree();
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
            markovDegree_.addDegree(chordDegree);
    }

    /**
     * This function is called once the markov chains are complete to normalize
     * each line of each matrix. Hence after every data sequence have been added
     * into the markov matrix.
     */
    public void closeLearning() {
        markovDegree_.closeLearning();
    }

    /**
     * Getter for the categoryName class attribute.
     * @return
     */
    public String getCategoryName() {
        return categoryName_;
    }

    /**
     * Getter for the markovDegree class attribute.
     * @return
     */
    public MarkovDegree getMarkovDegree() {
        return markovDegree_;
    }

}
