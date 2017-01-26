package website.java.app;

import analysis.harmonic.Tonality;
import generation.Generator;
import lstm.LSTMTrainer;
import org.apache.commons.compress.utils.IOUtils;
import sun.nio.ch.IOUtil;
import tools.AbcToMidi;
import tools.FluidSynthetizer;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;

import static spark.Spark.*;

import java.io.FileInputStream;
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
            String action = request.queryParams("action");

            String s = generators.get(genre).generate();

            byte[] data = null;

            if (action.equals("abc"))
                data = s.getBytes();
            else if (action.equals("mid") || action.equals("wav")) {
                Random r = new Random();
                Integer i = Math.abs(r.nextInt());
                AbcToMidi.abcToMidi(s, i.toString() + ".mid");
                if (action.equals("mid"))
                    data = IOUtils.toByteArray(new FileInputStream(i.toString() + ".mid"));
                else {
                    FluidSynthetizer.midToWav(i.toString() + ".mid", i.toString() + ".wav");
                    data = IOUtils.toByteArray(new FileInputStream(i.toString() + ".wav"));
                }
            }

            HttpServletResponse raw = response.raw();
            response.header("Content-Disposition", "attachment; filename=generated." + action);
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
