package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FluidSynthetizer {
    private static final String soundfonts = Misc.getJarPath() + "../assets/soundfonts/sgm2.sf2";

    public static void midToWav(String midInputFile, String wavOutputFile) {
        try {
            Process fluidSynth = Runtime.getRuntime().exec("fluidsynth -F " + wavOutputFile
                    + " " + soundfonts + " " + midInputFile);

            BufferedReader bre = new BufferedReader(new InputStreamReader(fluidSynth.getErrorStream()));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bre.readLine()) != null) {
                sb.append(line);
            }
            if (sb.length() > 0) {
                System.err.println("An error occurred while synthesizing " + wavOutputFile + ":");
                System.err.println(sb.toString());
            }
            else {
                System.out.println("The file " + wavOutputFile + " was successfully synthesized.");
            }

            fluidSynth.waitFor();

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
