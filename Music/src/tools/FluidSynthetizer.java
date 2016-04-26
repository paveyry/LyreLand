package tools;

import java.io.IOException;

public class FluidSynthetizer {
    private static final String soundfonts = "../assets/soundfonts/sgm2.sf2";

    public static void midToWav(String midInputFile, String wavOutputFile) {
        try {
            Process fluidSynth = Runtime.getRuntime().exec("fluidsynth -F " + wavOutputFile
                    + " " + soundfonts + " " + midInputFile);
            fluidSynth.waitFor();
            int exitValue = fluidSynth.exitValue();

            if (exitValue == 0)
                System.out.println(wavOutputFile + "was successfully synthesized.");
            else
                System.err.println("An error occured while synthesizing " + wavOutputFile);

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
