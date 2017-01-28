package website.java.app;

import lstm.LSTMTrainer;
import org.apache.commons.compress.utils.IOUtils;
import tools.AbcToMidi;
import tools.FluidSynthetizer;
import website.java.app.util.ViewUtil;

import javax.servlet.http.HttpServletResponse;

import static spark.Spark.*;
import static tools.filemanagement.TrainedLSTMsDeserializer.getTrainedLSTMs;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Routes {
    private static HashMap<String, LSTMTrainer> generators_;
    public static void setRoutes(ArrayList<String> genres, boolean backend) {
        Model model = new Model().addGenres(genres);
        get("/", (req, res) -> ViewUtil.render("/views/index.vm", model.getMap()));
        if (backend)
            generationRoute(genres);
        else
            post("/generate", (req, res) -> ViewUtil.render("/views/nobackend.vm", model.getMap()));
    }

    private static byte[] generate(String genre, String action) {
        byte[] data = null;
        try {
            String s = generators_.get(genre).generate();

            Random r = new Random();
            Integer i = Math.abs(r.nextInt());
            AbcToMidi.abcToMidi(s, i.toString() + ".mid");

            // This will check if conversion worked
            data = IOUtils.toByteArray(new FileInputStream(i.toString() + ".mid"));

            if (action.equals("abc"))
                data = s.getBytes();
            else if (action.equals("wav")) {
                FluidSynthetizer.midToWav(i.toString() + ".mid", i.toString() + ".wav");
                data = IOUtils.toByteArray(new FileInputStream(i.toString() + ".wav"));
                new File(i.toString() + ".wav").delete();
            }
            new File(i.toString() + ".mid").delete();

            return data;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void generationRoute(ArrayList<String> genres) {
        generators_ = getTrainedLSTMs(genres);
        post("/generate", (request, response) -> {
            String genre = request.queryParams("genre");
            String action = request.queryParams("action");

            byte[] data = null;

            while (data == null)
                data = generate(genre, action);

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
