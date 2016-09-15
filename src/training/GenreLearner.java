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
        System.out.println(markovDegree_.toString());
        markovDegree_.closeLearning();
        //System.out.println(markovDegree_.toString());
        //System.out.println(markovDegree_.getDegree(null, null));
    }


}
