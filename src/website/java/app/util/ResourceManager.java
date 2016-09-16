package website.java.app.util;

import tools.Misc;

public class ResourceManager {
    public static String getResourceDir() {
        return  Misc.getProjectPath() + "src/website/resources/";
    }
}
