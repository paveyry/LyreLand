package website.java.app;
import lstm.LSTMTrainer;
import website.java.app.util.ResourceManager;

import java.util.HashMap;
import java.util.Hashtable;

import static spark.Spark.*;
import static tools.filemanagement.TrainedLSTMsDeserializer.getTrainedLSTMs;
import static website.java.app.Routes.setRoutes;

public class Main {
    private static HashMap<String, LSTMTrainer> generators_ = new HashMap<>();

    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation(ResourceManager.getResourceDir());

        generators_ = getTrainedLSTMs();

        setRoutes(generators_);
    }


}
