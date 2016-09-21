package main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import main.options.ExecutionParameters;
import main.options.OptionManager;
import tools.filemanagement.TrainingCategoryLearner;
import tools.filemanagement.TrainingExampleGenerator;
import training.GenreLearner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    /**
     * Main method.
     * @param args Program arguments
     */
    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(args);
        optionManager.parse();

        if (ExecutionParameters.analyze) {
            processAnalysis();
        }

        if (ExecutionParameters.train) {
            processTraining();
        }

        if (ExecutionParameters.generate) {
            ArrayList<GenreLearner> trainedData = getTrainedData();
            // Generate a music using the main.options.ExecutionParameters.seed and the trained data located in
            // main.options.ExecutionParameters.trainedDataPath into Execution.Parameters.outputPath + ".mid"|".wav"
        }

    }

    /**
     * Launch analysis for each file in ExecutionParameters.midDirPath and create training set
     * in main.options.ExecutionParameters.trainingSetPath directory.
     */
    public static void processAnalysis() {
        try {
            System.out.println("Analyzing MIDI files to generated training set...");

            // Create our threadpool
            ExecutorService threadPool = Executors.newFixedThreadPool(ExecutionParameters.threads);

            // Disable stdout and stderr if verbose mode is disabled
            PrintStream out = System.out;
            if (!ExecutionParameters.verbose) {
                System.setOut(new PrintStream(new OutputStream() {
                    @Override public void write(int b) throws IOException {}
                }));
            }

            // Store starting time to mesure analysis execution duration
            double startTime = System.currentTimeMillis();

            // For each midi file in data set, create the corresponding XML file in the training set
            Files.walk(ExecutionParameters.midDirPath)
                    .filter(Files::isRegularFile)
                    .filter(s -> s.toString().endsWith(".mid"))
                    .sorted((o1, o2) -> {
                        File f1 = new File(o1.toString());
                        File f2 = new File(o2.toString());
                        return (int) (f2.length() - f1.length());
                    })
                    .forEach(s -> {
                        TrainingExampleGenerator teg = new TrainingExampleGenerator(s);
                        if (ExecutionParameters.parallel)
                            threadPool.execute(teg);
                        else
                            teg.run();
                    });

            // Close the threadpool and wait for the end of execution
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);

            // Reenable stdout
            if (!ExecutionParameters.verbose)
                System.setOut(out);

            // Calculate and display the execution time
            double endTime = System.currentTimeMillis();
            System.out.println("Analysis successfully processed (" + (endTime - startTime) + " ms)");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch training from the training set in main.options.ExecutionParameters.trainingSetPath and create
     * trained data in main.options.ExecutionParameters.trainedDataPath directory.
     */
    public static void processTraining() {
        try {
            System.out.println("Training from analyzed data...");
            // Create our threadpool
            ExecutorService threadPool = Executors.newFixedThreadPool(ExecutionParameters.threads);

            // Store starting time to mesure training execution duration
            double startTime = System.currentTimeMillis();

            // For each category in data set, create the corresponding XML files in the trained data dir
            File dir = new File(ExecutionParameters.trainingSetPath.toString());
            File[] subDirs = dir.listFiles();
            if (subDirs != null) {
                for (File subDir : subDirs) {
                    if (subDir.isDirectory()) {
                        TrainingCategoryLearner learner = new TrainingCategoryLearner(subDir);
                        if (ExecutionParameters.parallel)
                            threadPool.execute(learner);
                        else
                            learner.run();
                    }
                }
            }

            // Close the threadpool and wait for the end of execution
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);

            // Calculate and display the execution time
            double endTime = System.currentTimeMillis();
            System.out.println("Training successfully processed (" + (endTime - startTime) + " ms)");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<GenreLearner> getTrainedData() {
        System.out.println("Deserializing trained data...");
        ArrayList<GenreLearner> genres = new ArrayList<>();
        XStream xstream = new XStream(new DomDriver());

        // For each category in data set, create the corresponding XML files in the trained data dir
        File dir = new File(ExecutionParameters.trainedDataPath.toString());
        File[] subDirs = dir.listFiles();
        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (subDir.isDirectory()) {
                    Path dataFile = subDir.toPath().resolve("trained_data.xml");
                    genres.add((GenreLearner) xstream.fromXML(dataFile.toFile()));
                }
            }
        }
        System.out.println("Trained data successfully deserialized!");
        return genres;
    }
}
