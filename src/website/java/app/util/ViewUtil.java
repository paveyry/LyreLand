package website.java.app.util;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import spark.ModelAndView;
import spark.template.velocity.*;
import tools.Misc;

import java.util.Map;

public class ViewUtil {
    public static String render(String viewPath, Map<String, Object> model) {
        return velocityEngine().render(new ModelAndView(model, viewPath));
    }

    public static VelocityTemplateEngine velocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("file.resource.loader.path", ResourceManager.getResourceDir());

        return new VelocityTemplateEngine(configuredEngine);
    }
}
