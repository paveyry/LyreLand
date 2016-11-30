package main;

import lstm.ABCIterator;
import lstm.LSTMTrainer;
import main.options.ExecutionParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.Misc;
import website.java.app.util.ResourceManager;

import java.io.IOException;

public class LSTMTest {
    @BeforeClass
    public static void initClass() {
        ExecutionParameters.verbose = false;
    }

    @Test
    public void lstmTest() throws IOException {
        LSTMTrainer trainer = null;
        trainer = LSTMTrainer.deserialize(Misc.getProjectPath() + "lstm-epoch-98-lr0.04");
        trainer.generateSample(1, 1);
    }

}
