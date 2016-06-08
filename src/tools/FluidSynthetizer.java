package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FluidSynthetizer {
    private static String soundfonts = Misc.getJarPath() + "../assets/soundfonts/SGM-V2.01.sf2";

    public static void midToWav(String midInputFile, String wavOutputFile) {
        try {
            System.out.println("Synthesizing " + wavOutputFile + "...");

            // Run fluidsynth to generate the wave file using the mid and the soundfont collection
            Process fluidSynth = Runtime.getRuntime().exec("fluidsynth -F " + wavOutputFile
                    + " " + soundfonts + " " + midInputFile);

            // Get the error stream
            BufferedReader bre = new BufferedReader(new InputStreamReader(fluidSynth.getErrorStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bre.readLine()) != null)
                sb.append(line).append(System.lineSeparator());

            // Display error message with the fluidsynth if execution failed
            if (sb.length() > 0)
                System.err.println("An error occurred while synthesizing "
                        + wavOutputFile + ":" + System.lineSeparator()
                        + sb.toString());
            // Display success message if everything went well
            else
                System.out.println("The file " + wavOutputFile + " was successfully synthesized.");

            // Synchronize with the execution of Fluidsynth
            fluidSynth.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setSoundfonts(String pathToSoundfonts) {
        FluidSynthetizer.soundfonts = pathToSoundfonts;
    }
}
