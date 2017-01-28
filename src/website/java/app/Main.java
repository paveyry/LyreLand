package website.java.app;
import lstm.LSTMTrainer;
import tools.filemanagement.TrainedLSTMsDeserializer;
import website.java.app.util.ResourceManager;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.*;
import static tools.filemanagement.TrainedLSTMsDeserializer.getTrainedLSTMs;
import static website.java.app.Routes.setRoutes;

public class Main {
    private static HashMap<String, LSTMTrainer> generators_ = new HashMap<>();

    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation(ResourceManager.getResourceDir());

        ArrayList<String> genres = TrainedLSTMsDeserializer.getGenres();

        setRoutes(genres, args.length < 1 || !args[0].contains("no"));
    }
}