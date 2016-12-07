package website.java.app;

import analysis.harmonic.Tonality;
import generation.Generator;
import lstm.LSTMTrainer;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;

import static spark.Spark.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class Routes {
    public static void setRoutes(HashMap<String, LSTMTrainer> generators) {
        Model model = new Model().addGenres(generators);
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

    private static void generationRoute(HashMap<String, LSTMTrainer> generators) {
        post("/generate", (request, response) -> {
            String genre = request.queryParams("genre");
            //String seedString = request.queryParams("seed");
            //long seed = (seedString != null && seedString.length() > 0) ? Long.parseLong(seedString) : 437489379;

            String s = generators.get(genre).generate();
            System.out.println(s);
            System.out.println("KJDHJHDJKHDKJHDJHDJHDKJDH");
            byte[] data = s.getBytes();

            HttpServletResponse raw = response.raw();
            response.header("Content-Disposition", "attachment; filename=generated.abc");
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
