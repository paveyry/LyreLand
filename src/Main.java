import analysis.harmonic.VerticalBand;
import jm.music.data.*;
import jm.util.Play;
import jm.util.View;
import jm.util.Write;
import tonality.Scale;
import tonality.Tonality;
import tools.TypeConversion;

import java.util.ArrayList;

public class Main {

    public static void demoScale(int tonic, Tonality.Mode s, int octaveNumber, double rythm){
        Score score = new Score();
        Part p = new Part();
        Phrase phr = new Phrase();
        Scale scale = new Scale(tonic, s, octaveNumber);
        ArrayList<Integer> basicScale = scale.getScale();
        for (int i = 0; i < basicScale.size(); i++) {
            Note n = new Note(basicScale.get(i), rythm);
            System.out.println(basicScale.get(i));
            phr.add(n);
        }
        p.add(phr);
        score.add(p);
        Write.midi(score, "basic_scale.mid"); // create midiFile
        View.show(score);
        Play.mid("basic_scale.mid"); // Play the sound
    }

    public static void playTest(ArrayList<ArrayList<VerticalBand>> vb) {
        Score s = new Score();
        for (ArrayList<VerticalBand> av : vb) {
            Part p = new Part();
            CPhrase cph = new CPhrase();
            for (int j = av.size(); j > 0; j--) {
                cph.addChord(TypeConversion.convertIntegers(av.get(j-1).getPitches()), av.get(j-1).getRythm());
            }
            p.addCPhrase(cph);
            s.add(p);
            Play.midi(p);
        }
    }

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(args);
        optionManager.parse();

        if (ExecutionParameters.analyze) {
            // Launch analysis for each file in ExecutionParameters.midDirPath and create training set
            // in ExecutionParameters.trainingSetPath directory.
        }
        if (ExecutionParameters.train) {
            // Launch training from the training set in ExecutionParameters.trainingSetPath and create
            // trained data in ExecutionParameters.trainedDataPath directory.
        }
        if (ExecutionParameters.generate) {
            // Generate a music using the ExecutionParameters.seed and the trained data located in
            // ExecutionParameters.trainedDataPath into Execution.Parameters.outputPath + ".mid"|".wav"
        }

    }
}
