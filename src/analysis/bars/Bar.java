package analysis.bars;

import java.util.ArrayList;

public class Bar {
    private ArrayList<BarNote> notes_;

    public Bar() {
        this.notes_ = new ArrayList<>();
    }

    public Bar(ArrayList<BarNote> notes_) {
        this.notes_ = notes_;
    }

    public void addNote(BarNote note) {
        notes_.add(note);
    }

    public ArrayList<BarNote> getNotes() {
        return notes_;
    }
}
