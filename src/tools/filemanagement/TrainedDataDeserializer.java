package tools.filemanagement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;
import training.GenreLearner;
import generation.Generator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;

public class TrainedDataDeserializer {
    public static ArrayList<GenreLearner> getTrainedData() {
        ArrayList<GenreLearner> learners = new ArrayList<>();
        XStream xstream = new XStream(new DomDriver());

        // For each category in data set, create the corresponding XML files in the trained data dir
        File dir = new File(ExecutionParameters.trainedDataPath.toString());
        File[] subDirs = dir.listFiles();

        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (subDir.isDirectory()) {
                    Path dataFile = subDir.toPath().resolve("trained_data.xml");
                    GenreLearner genreLearner = (GenreLearner) xstream.fromXML(dataFile.toFile());
                    learners.add(genreLearner);
                }
            }
        }
        return learners;
    }
    public static Hashtable<String, Generator> createGenerators() {
        Hashtable<String, Generator> generators = new Hashtable<>();
        ArrayList<GenreLearner> learners = TrainedDataDeserializer.getTrainedData();
        for (GenreLearner learner : learners)
            generators.put(learner.getCategoryName(), new Generator(learner));
        return generators;
    }
}
