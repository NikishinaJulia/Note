package ru.gb.note.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {

    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        fillRepo();

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnNoteClickListener(this);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(Constants.NOTE, note);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_create:
                Intent EditNoteIntent = new Intent(this, EditNoteActivity.class);
                Note note = new Note("","");
                EditNoteIntent.putExtra(Constants.NOTE, note);
                startActivity(EditNoteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}