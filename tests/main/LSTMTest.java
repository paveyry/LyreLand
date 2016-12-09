package main;

import abc.midi.BasicMidiConverter;
import abc.midi.MidiConverterAbstract;
import abc.notation.Tune;
import abc.parser.AbcFileParser;
import abc.parser.AbcTune;
import abc.parser.AbcTuneBook;
import lstm.ABCIterator;
import lstm.LSTMTrainer;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.Misc;
import website.java.app.util.ResourceManager;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.spi.MidiFileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

public class LSTMTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void lstmTest() throws IOException {
        AbcFileParser parser = new AbcFileParser();
        AbcTuneBook b = parser.parse(new File("i.abc"));
        BasicMidiConverter converter = new BasicMidiConverter();
        Tune t = null;
        for (Object i : b.getTunes().keySet()) {
            t = (Tune) b.getTunes().get(i);
        }
        Sequence s = converter.toMidiSequence(t);
        MidiSystem.write(s, 1, new File("i.mid"));
        LSTMTrainer trainer = LSTMTrainer.deserialize(Misc.getProjectPath()
                + "assets/training/trained-lstm/nottingham/trained-lstm.bin",
                "assets/abc/nottingham/database.abc");
        System.out.println(trainer.generate());
        //trainer.generateSamples(1, 1, 25);
    }

}
