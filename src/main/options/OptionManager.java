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

            if (cmd.hasOption("a"))
                ExecutionParameters.analyze = true;

            if (cmd.hasOption("p")) {
                ExecutionParameters.parallel = true;
                ExecutionParameters.threads = Integer.parseInt(cmd.getOptionValue("p"));
            }

            if (cmd.hasOption("t"))
                ExecutionParameters.train = true;

            if (cmd.hasOption("g"))
                ExecutionParameters.generate = true;

            if (cmd.hasOption("w"))
                ExecutionParameters.generateWav = true;

            if (cmd.hasOption("s"))
                ExecutionParameters.seed = cmd.getOptionValue("s");

            if (cmd.hasOption("o"))
                ExecutionParameters.outputPath = cmd.getOptionValue("o");

            if (cmd.hasOption("midi_input_path"))
                ExecutionParameters.midDirPath = Paths.get(cmd.getOptionValue("midi_input_path"));

            if (cmd.hasOption("training_set_path"))
                ExecutionParameters.trainingSetPath = Paths.get(cmd.getOptionValue("training_set_path"));

            if (cmd.hasOption("trained_data_path"))
                ExecutionParameters.trainedDataPath = Paths.get(cmd.getOptionValue("trained_data_path"));

        } catch (ParseException e) {
            System.err.println("Wrong main.options");
            displayHelp();
        }
    }

    private void setOptions() {
        options_.addOption("v", "verbose", false, "Enable verbose mode");
        options_.addOption("h", "help", false, "Display help message.");
        options_.addOption("a", "analyze", false, "Activate MIDI analysis to generate the training set.");
        options_.addOption("p", "parallel", true, "Activate parallel computing with N threads for MIDI analysis.");
        options_.addOption("t", "train", false, "Activate training from training set.");
        options_.addOption("g", "generate", false, "Activate MIDI generation.");
        options_.addOption("w", "generate-wav", false, "Activate WAV generation.");
        options_.addOption("s", "seed", true, "Specify the seed for Music generation");
        options_.addOption("o", "output_name", true, "Specify the filename of the generated files without the" +
                "extension. The default value is `generated`, which means the generated files will be called " +
                "`generated.wav` and `generated.mid`.");
        options_.addOption("midi_input_path", true, "Specify the path to the MIDI input files directory.");
        options_.addOption("training_set_path", true, "Specify the path to the training-set directory.");
        options_.addOption("trained_data_path", true, "Specify the path to the trained data directory.");
    }

    private void displayHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("LyreLand", options_);
        System.exit(0);
    }
}
