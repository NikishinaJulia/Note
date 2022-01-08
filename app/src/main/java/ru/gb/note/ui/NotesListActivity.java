package ru.gb.note.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.gb.note.R;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity {

    private Repo repository = new InMemoryRepoImpl();
    private RecyclerView list;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        fillRepo();

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    private void fillRepo() {
        repository.create(new Note("Задача 1", "Описание 1 "));
        repository.create(new Note("Задача 2", "Описание 2 "));
        repository.create(new Note("Задача 3", "Описание 3 "));
        repository.create(new Note("Задача 4", "Описание 4 "));
        repository.create(new Note("Задача 5", "Описание 5 "));
        repository.create(new Note("Задача 6", "Описание 6 "));
        repository.create(new Note("Задача 7", "Описание 7 "));
        repository.create(new Note("Задача 8", "Описание 8 "));
        repository.create(new Note("Задача 9", "Описание 9 "));
        repository.create(new Note("Задача 10", "Описание 10 "));
        repository.create(new Note("Задача 11", "Описание 11 "));
        repository.create(new Note("Задача 12", "Описание 12 "));
        repository.create(new Note("Задача 13", "Описание 13 "));
        repository.create(new Note("Задача 14", "Описание 14 "));
        repository.create(new Note("Задача 15", "Описание 15 "));
    }
}