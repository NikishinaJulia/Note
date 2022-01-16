package ru.gb.note.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class EditNoteFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Repo repository = InMemoryRepoImpl.getInstance();
    private EditText title;
    private EditText description;
    private TextView dateChange;
    private Button pickDate;
    private Button saveNote;
    private Integer id = -1;
    private Spinner importance;
    private Object currentValue;
    private String currentValueDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        importance = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.importance, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);
        saveNote = view.findViewById(R.id.edit_note_update);
        saveNote.setOnClickListener(this);
        importance.setOnItemSelectedListener(this);

        dateChange =  view.findViewById(R.id.date_change);

        pickDate = view.findViewById(R.id.pick_date);
        pickDate.setOnClickListener((View v)-> {
            showDatePickerDialog(v);
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            Note note = (Note) arguments.getSerializable(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            String[] stringArray = view.getResources().getStringArray(R.array.importance);
            for (int i = 0, stringArrayLength = stringArray.length; i < stringArrayLength; i++) {
                String value = stringArray[i];
                if(value.equals(note.getImportance())){
                    importance.setSelection(i);
                }
            }
        }


        super.onViewCreated(view, savedInstanceState);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this, currentValueDate);
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar instance = Calendar.getInstance();
        instance.set(year,month,dayOfMonth);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(instance.getTime());
        currentValueDate = formatted;
        dateChange.setText(formatted);
    }

    @Override
    public void onClick(View v) {
        if(id == null || id == -1){
            repository.create(new Note(title.getText().toString(), description.getText().toString(), currentValue.toString(), currentValueDate));
        }else {
            Note note = new Note(id, title.getText().toString(), description.getText().toString(), currentValue.toString(), currentValueDate);
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

  /*  private String getImportanceValue(View v) {
        int selectedItemPosition = importance.getSelectedItemPosition();
        String[] stringArray = v.getResources().getStringArray(R.array.importance);
        return stringArray[selectedItemPosition];
    }*/

    public static EditNoteFragment newInstance(Note note) {

        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentValue = parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        currentValue = parent.getItemAtPosition(0);
    }


}
