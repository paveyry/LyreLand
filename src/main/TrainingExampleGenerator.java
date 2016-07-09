package main;

import analysis.ScoreAnalyser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class TrainingExampleGenerator implements Runnable {
    private Path filepath_;

    public TrainingExampleGenerator(Path filepath) {
        filepath_ = filepath;
    }

    public void run() {
        ScoreAnalyser sa = new ScoreAnalyser(filepath_.toString());
        try {
            // Process the destination path to keep the directory tree of the input midi dir inside the trainig set dir
            Path dest = ExecutionParameters.trainingSetPath.resolve(ExecutionParameters.midDirPath.relativize(filepath_));

            // If the destination path does not exist, create it
            File dir = new File(dest.getParent().toString());
            if (!dir.exists())
                dir.mkdirs();

            // Serialize the ScoreAnalyzer
            String destString = dest.toString();
            FileOutputStream fos = new FileOutputStream(destString.substring(0, destString.length() - 4) + ".xml");
            XStream xstream = new XStream(new DomDriver());
            fos.write(xstream.toXML(sa).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
