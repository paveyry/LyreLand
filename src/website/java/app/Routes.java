package website.java.app;

import analysis.harmonic.Tonality;
import generation.Generator;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;

import static analysis.harmonic.Tonality.Mode.MAJOR;
import static spark.Spark.*;
import static jm.constants.Pitches.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Routes {
    public static void setRoutes(Hashtable<String, Generator> generators) {
        Model model = new Model().addGenres();
        get("/", (req, res) -> ViewUtil.render("/views/index.vm", model.getMap()));
        generationRoute(generators);
    }

    private static void generationRoute(Hashtable<String, Generator> generators) {
        post("/generate", (request, response) -> {
            String genre = request.queryParams("genre");
            String seed = request.queryParams("seed");

            Path path = Paths.get("/tmp/generated-" + seed + ".mid");

            System.out.println(path.toString());

            generators.get(genre).writeHarmonicBase(new Tonality(C4, MAJOR, 0, 0, 0), 15, path.toString());
            System.out.println(path.toString());

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
