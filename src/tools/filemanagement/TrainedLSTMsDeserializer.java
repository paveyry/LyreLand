package tools.filemanagement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lstm.LSTMTrainer;
import main.options.ExecutionParameters;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainedLSTMsDeserializer {
    public static HashMap<String, LSTMTrainer> getTrainedLSTMs(ArrayList<String> genres) {
        HashMap<String, LSTMTrainer> learners = new HashMap<>();

        if (genres == null)
            genres = getGenres();

        for (String genre : genres) {
            Path dataFile = new File(ExecutionParameters.trainedLSTMPath + "/" + genre + "/trained_lstm.bin").toPath();
            LSTMTrainer trainer = LSTMTrainer.deserialize(dataFile.toString(),
                                  ExecutionParameters.LSTMTrainingSetPath + "/" + genre + "/database.abc");
            learners.put(genre, trainer);
        }
        return learners;
    }
    public static ArrayList<String> getGenres() {
        ArrayList<String> genres = new ArrayList<>();

        File dir = new File(ExecutionParameters.trainedLSTMPath.toString());
        File[] subDirs = dir.listFiles();

        if (subDirs != null)
            for (File subDir : subDirs)
                if (subDir.isDirectory())
                    genres.add(subDir.getName());

        return genres;
    }
}
