package ru.gb.note.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImpl implements Repo{

    private static InMemoryRepoImpl repo;

    public static Repo getInstance() {
        if (repo == null) {
            repo = new InMemoryRepoImpl();
        }
        return repo;

    }

    private InMemoryRepoImpl() {

    }

    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public Note read(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId() == id) {
                notes.remove(i);
            break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }

    @Override
    public void setData(List<Note> notes) {
        this.notes = new ArrayList<>(notes);
    }
}
