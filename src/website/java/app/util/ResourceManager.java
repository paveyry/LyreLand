package website.java.app.util;

import tools.Misc;

public class ResourceManager {
    public static String getResourceDir() {
        return  Misc.getProjectPath() + "target/classes/website/resources/";
    }

    public static String getGenDir() {
        return Misc.getProjectPath() + "generation/";
    }
}
