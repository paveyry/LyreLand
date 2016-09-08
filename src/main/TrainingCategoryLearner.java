package main;

import analysis.ScoreAnalyser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;
import training.GenreLearner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class TrainingCategoryLearner implements Runnable {
    private File categoryDir_;

    public TrainingCategoryLearner(File categoryDir) {
        categoryDir_ = categoryDir;
    }

    public void run() {
        // Instantiate the learner for the whole category
        GenreLearner learner = new GenreLearner(categoryDir_.getName());

        // Iterate over training examples
        File[] trainingExamples = categoryDir_.listFiles();
        if (trainingExamples != null) {
            for (File trainingExample : trainingExamples) {
                if (trainingExample.toString().endsWith(".xml")) {
                    // Deserialize ScoreAnalyzers
                    XStream xstream = new XStream(new DomDriver());
                    ScoreAnalyser sa = (ScoreAnalyser) xstream.fromXML(trainingExample);
                    learner.learnExample(sa);
                }
            }
        }

        // Process path to trained data file
        Path relPath = ExecutionParameters.trainingSetPath.relativize(categoryDir_.toPath());
        Path outputDir = ExecutionParameters.trainedDataPath.resolve(relPath);
        Path outputPath = outputDir.resolve("trained_data.xml");

        // Serialize learner to XML
        XStream xstream = new XStream(new DomDriver());
        try {
            FileOutputStream fos = new FileOutputStream(outputPath.toFile());
            fos.write(xstream.toXML(learner).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
