package analysis.bars;

import java.util.ArrayList;

public class Bar {
    private ArrayList<BarNote> notes_;
    private ArrayList<ArrayList<Integer>> notesByBeat_;

    public Bar() {
        this.notes_ = new ArrayList<>();
        this.notesByBeat_ = null;
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

    public void segmentRhythms(double quantum) {
        int size = notes_.size();
        for (int i = 0; i < size; ++i) {
            BarNote barNote = notes_.get(i);
            while (barNote.getDuration() > quantum) {
                double newNoteStartTime = barNote.getStartTime() + barNote.getDuration() - quantum;
                notes_.add(new BarNote(newNoteStartTime, quantum, barNote.getPitch()));
                barNote.setDuration(barNote.getDuration() - quantum);
            }
        }
    }

    public void groupNotesByBeat(int beatsPerBar, double barUnit) {
        notesByBeat_ = new ArrayList<>();
        for (int i = 0; i < beatsPerBar; ++i)
            notesByBeat_.add(new ArrayList<>());
        for (BarNote barNote : notes_) {
            int barBeat = (int) (barNote.getStartTime() / barUnit);
            notesByBeat_.get(barBeat).add(barNote.getPitch());
        }
    }

    public ArrayList<ArrayList<Integer>> getNotesByBeat() {
        return notesByBeat_;
    }
}
