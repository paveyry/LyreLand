package website.java.app;
import generation.Generator;
import website.java.app.util.ResourceManager;
import java.util.Hashtable;

import static spark.Spark.*;
import static tools.filemanagement.TrainedDataDeserializer.createGenerators;
import static website.java.app.Routes.setRoutes;

public class Main {
    private static Hashtable<String, Generator> generators_ = new Hashtable<>();

    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation(ResourceManager.getResourceDir());

        generators_ = createGenerators();

        setRoutes(generators_);
    }


}
