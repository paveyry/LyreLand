package Analysis;

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
    private ScoreAnalyser _scoreAnalyser;
    private ArrayList<String> _data;

    public DataWriter(ScoreAnalyser sa) {
        _scoreAnalyser = sa;
        _data = new ArrayList<>();
        fillData();
    }

    private void fillData() {
        _data.add("---------- Metadata ----------");
        _data.add("Title: " + _scoreAnalyser.getTitle());
        _data.add("Music tonality: " + _scoreAnalyser.getTonality());
        _data.add("Scale: "+ _scoreAnalyser.getScale().pitchToString());
        _data.add("Bar unit: " + _scoreAnalyser.getBarUnit());
        _data.add("Number of beat per bar: " + _scoreAnalyser.getBeatsPerBar());
        _data.add("Part Number: " + _scoreAnalyser.getPartNb());
        _data.add("Used instruments: ");
        for (int i = 0; i < _scoreAnalyser.getScore().getPartArray().length; i++) {
            _data.add("     (Part " + i + ") " + _scoreAnalyser.getScore().getPart(i).getInstrument());
        }
    }

    public void writeData(String s) {
        Path path = Paths.get(s);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String data : _data) {
                writer.write(data + "\n");
            }
        }
        catch (Exception e) {
            System.out.println("Error: DataWriter class writeData" + s);
        }
    }

}
