package ru.gb.note.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class MainNotesActivity extends AppCompatActivity {

    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        if (savedInstanceState == null) {
            fillRepo();
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, new NoteListFragment());
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        /*if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            onNoteClickLand(new Note("",""));
        }*/
    }


/*    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }*/

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_create:
                if (getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_LANDSCAPE) {
                    onNoteClickLand(new Note("",""));
                } else {
                    onNoteClickPort(new Note("",""));
                }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onNoteClickPort(Note note) {
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, detail);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void onNoteClickLand(Note note) {
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.edit_fragment, detail);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}