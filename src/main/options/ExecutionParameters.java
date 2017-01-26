package main.options;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class contains static public variables that
 * specify runtime behavior.
 * Initialization is the default value
 */
public class ExecutionParameters {
    /**
     * Specify if the verbose mode is activated
     */
    public static boolean verbose = false;

    /**
     * Specify if we train the program (if we fill and save the Markov chains)
     */
    public static boolean train = false;

    /**
     * Specify the seed for generation
     */
    public static int seed = 3657263;

    /**
     * Specify the path to the training set (for writing or reading)
     */
    public static Path LSTMTrainingSetPath = Paths.get("assets/abc");

    /**
     * Specify the path to the trained data (created after training and used for generation)
     */
    public static Path trainedLSTMPath = Paths.get("assets/training/trained-lstm");
}
