package tools;

import java.io.*;
import java.util.Random;

public class AbcToMidi {

    public static boolean abcToMidi(String abcInput, String midiOutputFile) {
        try {
            Random r = new Random();
            Integer rand = r.nextInt();

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(rand.toString() + ".abc"), "utf-8"))) {
                writer.write(abcInput);
            }

            // Run fluidsynth to generate the wave file using the mid and the soundfont collection
            Process abc2mid = Runtime.getRuntime().exec(Misc.getProjectPath() + "/abc2midi " + rand.toString()
                    + ".abc -o " + midiOutputFile);

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
