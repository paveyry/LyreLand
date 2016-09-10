package website.java.app;
import tools.Misc;
import website.java.app.util.ResourceManager;
import website.java.app.util.ViewUtil;

import java.util.HashMap;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation(ResourceManager.getResourceDir());
        get("/", (req, res) -> ViewUtil.render("/views/index.vm", new HashMap<>()));
    }
}
