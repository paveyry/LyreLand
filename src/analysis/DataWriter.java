package analysis;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by olivier on 01/06/16.
 */
public class DataWriter {
    // This class writes the data of the ScoreAnalyser
    // to a file to fill the database.
    // FIXME: Need to find a good format (easy to read human/program)
    private ScoreAnalyser scoreAnalyser_;
    private ArrayList<String> data_;

    public DataWriter(ScoreAnalyser sa) {
        scoreAnalyser_ = sa;
        data_ = new ArrayList<>();
        fillData();
    }

    private void fillData() {
        data_.add("---------- Metadata ----------");
        data_.add("Title: " + scoreAnalyser_.getTitle());
        data_.add("Music tonality: " + scoreAnalyser_.getTonality());
        data_.add("Scale: "+ scoreAnalyser_.getScale().pitchToString());
        data_.add("Bar unit: " + scoreAnalyser_.getBarUnit());
        data_.add("Number of beat per bar: " + scoreAnalyser_.getBeatsPerBar());
        data_.add("Part Number: " + scoreAnalyser_.getPartNb());
        data_.add("Used instruments: ");
        for (int i = 0; i < scoreAnalyser_.getScore().getPartArray().length; i++) {
            data_.add("     (Part " + i + ") " + scoreAnalyser_.getScore().getPart(i).getInstrument());
        }
    }

    public void writeData(String s) {
        Path path = Paths.get(s);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String data : data_) {
                writer.write(data + "\n");
            }
        }
        catch (Exception e) {
            System.out.println("Error: DataWriter class writeData" + s);
        }
    }

}
