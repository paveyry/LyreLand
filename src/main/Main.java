package main;

import analysis.ScoreAnalyser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;
import main.options.OptionManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(args);
        optionManager.parse();

        // Launch analysis for each file in ExecutionParameters.midDirPath and create training set
        // in main.options.ExecutionParameters.trainingSetPath directory.
        if (ExecutionParameters.analyze) {
            try {
                // Create our threadpool
                ExecutorService threadPool = Executors.newFixedThreadPool(4);

                // Store starting time to mesure analysis execution duration
                double startTime = System.currentTimeMillis();

                // Disable stdout and stderr if verbose mode is disabled
                PrintStream out = System.out;
                if (!ExecutionParameters.verbose) {
                    System.setOut(new PrintStream(new OutputStream() {
                        @Override public void write(int b) throws IOException {}
                    }));
                }

                // For each midi file in data set, create the corresponding XML file in the training set
                Files.walk(ExecutionParameters.midDirPath)
                        .filter(Files::isRegularFile)
                        .filter(s -> s.toString().endsWith(".mid"))
                        .forEach(s -> {
                            TrainingExampleGenerator teg = new TrainingExampleGenerator(s);
                            if (ExecutionParameters.parallel)
                                threadPool.execute(teg);
                            else
                                teg.run();
                        });
                System.err.println("JIHDJSHIUSHDIUHUDIHSIUDHUISHIUDHIUSHDIUHS");


                // Close the threadpool and wait for the end of execution
                threadPool.shutdown();
                threadPool.awaitTermination(1, TimeUnit.HOURS);

                // Reenable stdout
                if (!ExecutionParameters.verbose)
                    System.setOut(out);

                // Calculate and display the execution time
                double endTime = System.currentTimeMillis();
                System.out.println("ANALYSIS EXECUTION TIME: " + (endTime - startTime));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ExecutionParameters.train) {
            // Launch training from the training set in main.options.ExecutionParameters.trainingSetPath and create
            // trained data in main.options.ExecutionParameters.trainedDataPath directory.
        }

        if (ExecutionParameters.generate) {
            // Generate a music using the main.options.ExecutionParameters.seed and the trained data located in
            // main.options.ExecutionParameters.trainedDataPath into Execution.Parameters.outputPath + ".mid"|".wav"
        }

    }
}
