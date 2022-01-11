package ru.gb.note.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Repo repository = InMemoryRepoImpl.getInstance();
    private EditText title;
    private EditText description;
    private Button saveNote;
    private Integer id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update);
        saveNote.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
    }

    @Override
    public void onClick(View v) {
        if(id == null || id == -1){
            repository.create(new Note(title.getText().toString(), description.getText().toString()));
        }else {
            Note note = new Note(id, title.getText().toString(), description.getText().toString());
            repository.update(note);
        }
        finish();
    }
}
