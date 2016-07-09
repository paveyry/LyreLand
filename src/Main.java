import analysis.ScoreAnalyser;
import analysis.harmonic.ChordDegreeProcessor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import jm.music.data.*;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import options.ExecutionParameters;
import options.OptionManager;
import tonality.Scale;
import tonality.Tonality;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(args);
        optionManager.parse();

        if (ExecutionParameters.analyze) {
            // Launch analysis for each file in ExecutionParameters.midDirPath and create training set
            // in options.ExecutionParameters.trainingSetPath directory.
            try {
                double startTime = System.currentTimeMillis();
                ExecutorService service = Executors.newFixedThreadPool(10);
                Files.walk(Paths.get(ExecutionParameters.midDirPath))
                        .filter(Files::isRegularFile)
                        .filter(s -> s.toString().endsWith(".mid"))
                        .forEach(s -> /*service.execute(() -> */{
                            ScoreAnalyser sa = new ScoreAnalyser(s.toString());
                            sa.processDegreeList();
                            try {
                                FileOutputStream fos = new FileOutputStream(s.toString().substring(0, s.toString().length() - 4) + ".xml");
                                XStream xstream = new XStream(new DomDriver());
                                fos.write(xstream.toXML(sa).getBytes());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }/*)*/);
                service.shutdown();
                service.awaitTermination(1, TimeUnit.HOURS);
                double endTime = System.currentTimeMillis();
                System.out.println("EXECUTION TIME: " + (endTime - startTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ExecutionParameters.train) {
            // Launch training from the training set in options.ExecutionParameters.trainingSetPath and create
            // trained data in options.ExecutionParameters.trainedDataPath directory.
        }
        if (ExecutionParameters.generate) {
            // Generate a music using the options.ExecutionParameters.seed and the trained data located in
            // options.ExecutionParameters.trainedDataPath into Execution.Parameters.outputPath + ".mid"|".wav"
        }

    }
}
