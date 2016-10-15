package tools.filemanagement;

import analysis.ScoreAnalyser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;
import training.GenreLearner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Runnable class that processes learning on all the training examples for a specific music genre
 * and generates a XML file with the learned data in the trained-data directory.
 */
public class TrainingCategoryLearner implements Runnable {
    private File categoryDir_;

    /**
     * Constructor
     * @param categoryDir Directory of the music genre
     */
    public TrainingCategoryLearner(File categoryDir) {
        categoryDir_ = categoryDir;
    }

    /**
     * Run method. Execute training, process the output path and serialize data in XML
     */
    public void run() {
        // Instantiate the learner for the whole category
        GenreLearner learner = new GenreLearner(categoryDir_.getName());

        final int[] nbFiles = {0};

        // Iterate over training examples
        try {
            Files.walk(categoryDir_.toPath())
                    .filter(Files::isRegularFile)
                    .forEach(s -> {
                            if (s.toString().endsWith(".xml")) {
                                // Deserialize ScoreAnalyzers
                                XStream xstream = new XStream(new DomDriver());
                                ScoreAnalyser sa = (ScoreAnalyser) xstream.fromXML(s.toFile());
                                learner.learnExample(sa);
                                ++nbFiles[0];
                            }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process path to trained data file
        Path relPath = ExecutionParameters.trainingSetPath.relativize(categoryDir_.toPath());
        Path outputDir = ExecutionParameters.trainedDataPath.resolve(relPath);
        Path outputPath = outputDir.resolve("trained_data.xml");

        // If the destination path does not exist, create it
        File dir = new File(outputDir.toString());
        if (!dir.exists())
            dir.mkdirs();

        // Serialize learner to XML
        XStream xstream = new XStream(new DomDriver());
        try {
            FileOutputStream fos = new FileOutputStream(outputPath.toFile());
            fos.write(xstream.toXML(learner).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Learned " + categoryDir_.getName() + " from " + nbFiles[0] + " files.");
    }
}
