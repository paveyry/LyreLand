package tools.filemanagement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lstm.LSTMTrainer;
import main.options.ExecutionParameters;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class TrainedLSTMsDeserializer {
    public static HashMap<String, LSTMTrainer> getTrainedLSTMs() {
        HashMap<String, LSTMTrainer> learners = new HashMap<>();
        XStream xstream = new XStream(new DomDriver());

        // For each category in data set, create the corresponding XML files in the trained data dir
        File dir = new File(ExecutionParameters.trainedLSTMPath.toString());
        File[] subDirs = dir.listFiles();

        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (subDir.isDirectory()) {
                    Path dataFile = subDir.toPath().resolve("trained_lstm.bin");
                    LSTMTrainer trainer = LSTMTrainer.deserialize(dataFile.toString(),
                            ExecutionParameters.LSTMTrainingSetPath + "/" + subDir.getName() + "/database.abc");
                    learners.put(subDir.getName(), trainer);
                }
            }
        }
        return learners;
    }
}
