package website.java.app.util;

import tools.Misc;

public class ResourceManager {
    public static String getResourceDir() {
        String path = Misc.getJarPath();
        return  path.substring(0, path.lastIndexOf("target")) + "src/website/resources/";
    }
}
