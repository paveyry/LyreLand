import org.apache.commons.cli.*;

public class OptionManager {
    private String[] args_;
    private Options options_;

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
                ExecutionParameters.verboseMode = true;

            if (cmd.hasOption("a"))
                ExecutionParameters.analyze = true;

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

            if (cmd.hasOption("midi-input-path"))
                ExecutionParameters.midDirPath = cmd.getOptionValue("midi-input-path");

            if (cmd.hasOption("training-set-path"))
                ExecutionParameters.trainingSetPath = cmd.getOptionValue("training-set-path");

            if (cmd.hasOption("trained-data-path"))
                ExecutionParameters.trainedDataPath = cmd.getOptionValue("trained-data-path");

        } catch (ParseException e) {
            System.err.println("Wrong options");
            displayHelp();
        }
    }

    private void setOptions() {
        options_.addOption("h", "help", false, "Display help message.");
        options_.addOption("v", "verbose", false, "Activate verbose mode.");
        options_.addOption("a", "analyze", false, "Activate MIDI analysis to generate the training set.");
        options_.addOption("t", "train", false, "Activate training from training set.");
        options_.addOption("g", "generate", false, "Activate MIDI generation.");
        options_.addOption("w", "generate-wav", false, "Activate WAV generation.");
        options_.addOption("s", "seed", true, "Specify the seed for Music generation");
        options_.addOption("o", "output-name", true, "Specify the filename of the generated files without the" +
                "extension. The default value is `generated`, which means the generated files will be called " +
                "`generated.wav` and `generated.mid`.");
        options_.addOption("midi-input-path", true, "Specify the path to the MIDI input files directory.");
        options_.addOption("training-set-path", true, "Specify the path to the training-set directory.");
        options_.addOption("trained-data-path", true, "Specify the path to the trained data directory.");
    }

    private void displayHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("Main", options_);
        System.exit(0);
    }
}
