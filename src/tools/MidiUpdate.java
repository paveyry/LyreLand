package tools;

import jm.music.data.Score;
import jm.util.Read;
import jm.util.Write;

public class MidiUpdate {
    public static void UpdateMidiTonality(String fileName, int keySignature, int keyQuality) {
        fileName = tools.Misc.getJarPath() + fileName;
        Score init = new Score();
        Read.midi(init, fileName);
        init.setKeySignature(keySignature);
        init.setKeyQuality(keyQuality);
        Write.midi(init, fileName);
    }
}
