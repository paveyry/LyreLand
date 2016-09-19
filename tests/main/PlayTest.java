package main;

import analysis.ScoreAnalyser;

import analysis.harmonic.ChordDegree;
import analysis.harmonic.Tonality;
import generation.Generator;
import jm.music.data.Score;
import jm.util.Read;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import training.GenreLearner;

public class PlayTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void playTest() {
        /*String midifile = tools.Misc.getJarPath() + "../assets/midi/baroque/Bach-Prelude1.mid";
        Score init = new Score();
        Read.midi(init, midifile);
        ScoreAnalyser sa = new ScoreAnalyser(midifile);
        sa.printScoreInfo();*/
        Generator gene = new Generator("baroque");
        Tonality tonality = new Tonality(0, 0); // CMajor
        gene.readHarmonicBase(tonality, 12); // tonalily, numberofdegree
        //Play.midi(init);
    }
}
