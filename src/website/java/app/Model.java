package website.java.app;

import lstm.LSTMTrainer;

import java.util.ArrayList;
import java.util.HashMap;


public class Model {
    HashMap<String, Object> map_;

    public Model() {
        map_ = new HashMap<>();
    }

    public HashMap<String, Object> getMap() {
        return map_;
    }

    public Model addGenres(ArrayList<String> genres) {
        map_.put("genres", genres);
        return this;
    }
}
