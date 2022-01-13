package ru.gb.note.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class EditNoteFragment extends Fragment implements View.OnClickListener {

    private Repo repository = InMemoryRepoImpl.getInstance();
    private EditText title;
    private EditText description;
    private Button saveNote;
    private Integer id = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        saveNote = view.findViewById(R.id.edit_note_update);
        saveNote.setOnClickListener(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Note note = (Note) arguments.getSerializable(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(id == null || id == -1){
            repository.create(new Note(title.getText().toString(), description.getText().toString()));
        }else {
            Note note = new Note(id, title.getText().toString(), description.getText().toString());
            repository.update(note);
        }
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            ((NoteListFragment) requireActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.list_fragment))
                    .updateList();
            requireActivity().getSupportFragmentManager().popBackStack();
        }else{
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public static EditNoteFragment newInstance(Note note) {

        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

}
