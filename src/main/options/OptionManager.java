package main.options;

import org.apache.commons.cli.*;

import java.nio.file.Paths;

/**
 * Class for option parsing using apache.commons.cli library
 */
public class OptionManager {
    private String[] args_;
    private Options options_;

    /**
     * Constructor
     * @param args Program arguments
     */
    public OptionManager(String[] args) {
        this.args_ = args;
        this.options_ = new Options();
        setOptions();
    }

    public void parse() {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options_, args_);

            if (cmd.hasOption("h"))
                displayHelp();

            if (cmd.hasOption("v"))
                ExecutionParameters.verbose = true;

            if (cmd.hasOption("t"))
                ExecutionParameters.train = true;

            if (cmd.hasOption("s"))
                ExecutionParameters.seed = Integer.parseInt(cmd.getOptionValue("s"));

            if (cmd.hasOption("training_set_path"))
                ExecutionParameters.LSTMTrainingSetPath = Paths.get(cmd.getOptionValue("training_set_path"));

            if (cmd.hasOption("trained_data_path"))
                ExecutionParameters.trainedLSTMPath = Paths.get(cmd.getOptionValue("trained_data_path"));

        } catch (ParseException e) {
            System.err.println("Wrong main.options");
            displayHelp();
        }
    }

    private void setOptions() {
        options_.addOption("v", "verbose", false, "Enable verbose mode");
        options_.addOption("h", "help", false, "Display help message.");
        options_.addOption("t", "train", false, "Activate training from training set.");
        options_.addOption("s", "seed", true, "Specify the seed for Music generation");
        options_.addOption("training_set_path", true, "Specify the path to the training-set directory.");
        options_.addOption("trained_data_path", true, "Specify the path to the trained data directory.");
    }

    private void displayHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("LyreLand", options_);
        System.exit(0);
    }
}
