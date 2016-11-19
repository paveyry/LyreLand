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
        LSTMTrainer trainer = new LSTMTrainer(Misc.getProjectPath() + "assets/abc/data.abc", 3657263);
        trainer.train();
        trainer.serialize(Misc.getProjectPath() + "serialized_lstm.bin");
    }

}
