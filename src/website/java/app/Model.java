package website.java.app;

import training.GenreLearner;

import java.util.ArrayList;
import java.util.HashMap;

import static tools.filemanagement.TrainedDataDeserializer.getTrainedData;

public class Model {
    HashMap<String, Object> map_;

    public Model() {
        map_ = new HashMap<>();
    }

    public HashMap<String, Object> getMap() {
        return map_;
    }

    public Model addGenres() {
        ArrayList<GenreLearner> learners = getTrainedData();
        ArrayList<String> genres = new ArrayList<>();
        for (GenreLearner learner : learners)
            genres.add(learner.getCategoryName());
        map_.put("genres", genres);
        return this;
    }
}
