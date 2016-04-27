package tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Misc {
    public static String getJarPath() {
        String path = Misc.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decoded = null;
        String dir = null;

        try {
            decoded = URLDecoder.decode(path, "UTF-8");
            int lastSlash = decoded.lastIndexOf("/");

            if (decoded.substring(lastSlash - 7, lastSlash).compareTo("classes") == 0)
                dir = decoded.substring(0, lastSlash - 7);
            else
                dir = decoded.substring(0, lastSlash + 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return dir;
    }
}
