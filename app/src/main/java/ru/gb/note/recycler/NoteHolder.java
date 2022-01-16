package ru.gb.note.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.note.R;
import ru.gb.note.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private Note note;
    private TextView importance;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        importance = itemView.findViewById(R.id.note_importance);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClick(note);

            }
        });
    }

    void bind(Note note) {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        importance.setText(note.getImportance());
    }
}
