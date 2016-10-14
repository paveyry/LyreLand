package website.java.app;

import analysis.harmonic.Tonality;
import generation.Generator;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;

import static spark.Spark.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Routes {
    public static void setRoutes(Hashtable<String, Generator> generators) {
        Model model = new Model().addGenres();
        get("/", (req, res) -> ViewUtil.render("/views/index.vm", model.getMap()));
        generationRoute(generators);
    }

    // TO DELETE
    private static Tonality getATonality(long seed) {
        ArrayList<Tonality> tonalities = new ArrayList<>();
        tonalities.add(new Tonality(0, 0));
        tonalities.add(new Tonality(0, 1));
        tonalities.add(new Tonality(1, 0));
        tonalities.add(new Tonality(-1, 0));
        tonalities.add(new Tonality(4, 0));
        tonalities.add(new Tonality(4, 1));
        tonalities.add(new Tonality(6, 1));
        Random r = new Random(seed);
        return tonalities.get(r.nextInt(tonalities.size()));
    }

    private static void generationRoute(Hashtable<String, Generator> generators) {
        post("/generate", (request, response) -> {
            String genre = request.queryParams("genre");
            String seedString = request.queryParams("seed");
            long seed = (seedString != null && seedString.length() > 0) ? Long.parseLong(seedString) : 437489379;

            Path path = Paths.get("/tmp/generated-" + seed + ".mid");

            System.out.println(path.toString());

            generators.get(genre).writeHarmonicBase(getATonality(seed), new Random(seed).nextInt(7) + 10, path.toString(), seed);

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
