package main;

import lstm.ABCIterator;
import abc.midi.BasicMidiConverter;
import abc.notation.Tune;
import abc.parser.AbcFileParser;
import abc.parser.AbcTuneBook;
import abcynth.PlayerApp;
import lstm.LSTMTrainer;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.Misc;
import website.java.app.util.ResourceManager;

import java.io.IOException;
import java.util.Random;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class LSTMTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void lstmTest() throws IOException {
        LSTMTrainer trainer = null;
        trainer = LSTMTrainer.deserialize(Misc.getProjectPath() + "lstm-epoch-98-lr0.04", "assets/abc/nottingham/database.abc");
        System.out.println(trainer.generate();
        //trainer.generateSamples(1, 1, 25);
    }

}
