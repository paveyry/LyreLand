package tools;

import java.io.*;
import java.util.Random;

public class AbcToMidi {
    private static String os_ = System.getProperty("os.name").toLowerCase();

    public static boolean abcToMidi(String abcInput, String midiOutputFile) {
        try {
            Random r = new Random();
            Integer rand = Math.abs(r.nextInt());

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(rand.toString() + ".abc"), "utf-8"))) {
                writer.write(abcInput);
            }

            String binaryname = os_.contains("mac") ? "abc2midi.mac " : "abc2midi.nux ";

            // Run fluidsynth to generate the wave file using the mid and the soundfont collection
            Process abc2mid = Runtime.getRuntime().exec(Misc.getProjectPath() + "/assets/programs/" + binaryname
                    + rand.toString() + ".abc -o " + midiOutputFile);

            // Get the error stream
            BufferedReader bre = new BufferedReader(new InputStreamReader(abc2mid.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bre.readLine()) != null)
                sb.append(line).append(System.lineSeparator());

            abc2mid.waitFor();
            return !sb.toString().contains("error") && !sb.toString().contains("Error");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
 }
