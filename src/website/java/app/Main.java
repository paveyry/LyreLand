package website.java.app;
import analysis.harmonic.Tonality;
import generation.Generator;
import tools.filemanagement.TrainedDataDeserializer;
import training.GenreLearner;
import training.tools.Pair;
import website.java.app.util.ResourceManager;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringJoiner;

import static analysis.harmonic.Tonality.Mode.MAJOR;
import static jm.constants.Pitches.C4;
import static spark.Spark.*;
import static tools.filemanagement.TrainedDataDeserializer.createGenerators;

public class Main {
    private static Hashtable<String, Generator> generators_ = new Hashtable<>();

    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation(ResourceManager.getResourceDir());

        generators_ = createGenerators();

        get("/", (req, res) -> ViewUtil.render("/views/index.vm", new HashMap<>()));

        get("/result", (request, response) -> {
            String genre = request.queryParams("genre");
            String seed = request.queryParams("seed");

            Path path = Paths.get("/tmp/generated-" + seed + ".mid");

            generators_.get(genre).writeHarmonicBase(new Tonality(C4, MAJOR, 0, 0, 0), 15, path.toString());

            byte[] data = null;
            try {
                data = Files.readAllBytes(path);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            HttpServletResponse raw = response.raw();
            response.header("Content-Disposition", "attachment; filename=generated=" + seed + ".mid");
            response.type("application/force-download");
            try {
                raw.getOutputStream().write(data);
                raw.getOutputStream().flush();
                raw.getOutputStream().close();
            } catch (Exception e) {

                e.printStackTrace();
            }
            return raw;


        });
    }


}
