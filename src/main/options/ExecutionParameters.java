package main.options;

import tools.Misc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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
     * Specify if we create the training set (analyze .mid files and create input files for training)
     */
    public static boolean analyze = false;

    /**
     * Specify if the training-set generation is parallelized
     */
    public static boolean parallel = false;

    /**
     * Specify the number of thread for parallel computing
     */
    public static int threads = 4;

    /**
     * Specify if we train the program (if we fill and save the Markov chains)
     */
    public static boolean train = false;

    /**
     * Specify if we generate a song
     */
    public static boolean generate = false;

    /**
     * Specify if we generate a WAV
     */
    public static boolean generateWav = false;

    /**
     * Specify the seed for generation
     */
    public static String seed = "lyreland" + new Random().nextInt(800000);

    /**
     * Specify the path to input mid Files
    */
    public static Path midDirPath = Paths.get(Misc.getJarPath() + "../assets/midi/");

    /**
     * Specify the path to the training set (for writing or reading)
     */
    public static Path trainingSetPath = Paths.get(Misc.getJarPath() + "training/training-set");

    /**
     * Specify the path to the trained data (created after training and used for generation)
     */
    public static Path trainedDataPath = Paths.get(Misc.getJarPath() + "training/trained-data");

    /**
     * Specify the path to the generated files without the extension. Default value is `generated.mid` and `generated.wav`
     */
    public static String outputPath = Misc.getJarPath() + "../generated";


}
