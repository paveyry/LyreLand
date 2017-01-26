package main;

import lstm.LSTMTrainer;
import main.options.ExecutionParameters;
import main.options.OptionManager;

import java.io.*;

public class Main {

    /**
     * Main method.
     * @param args Program arguments
     */
    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(args);
        optionManager.parse();

        processLSTMTraining();
    }

    private static void processLSTMTraining() {
        try {
            System.out.println("Training from ABC songs...");
            File dir = new File(ExecutionParameters.LSTMTrainingSetPath.toString());
            File[] subDirs = dir.listFiles();
            if (subDirs != null) {
                for (File subDir : subDirs) {
                    if (subDir.isDirectory()) {
                        LSTMTrainer lstmTrainer = new LSTMTrainer(subDir.getAbsolutePath() + "/database.abc",
                                                                  ExecutionParameters.seed);
                        lstmTrainer.train();
                        lstmTrainer.serialize(ExecutionParameters.trainedLSTMPath + subDir.getName());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
