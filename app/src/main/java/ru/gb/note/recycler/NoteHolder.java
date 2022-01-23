package ru.gb.note.recycler;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.note.R;
import ru.gb.note.data.Note;
import ru.gb.note.data.PopupMenuItemClickListener;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private TextView title;
    private TextView description;
    private Note note;
    private TextView importance;
    private TextView date;
    private ImageView noteMenu;

    private PopupMenu popupMenu;
    private PopupMenuItemClickListener clickListener;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener, PopupMenuItemClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        importance = itemView.findViewById(R.id.note_importance);
        date = itemView.findViewById(R.id.note_date);
        noteMenu = itemView.findViewById(R.id.note_menu);

        popupMenu = new PopupMenu(itemView.getContext(), noteMenu);
        popupMenu.inflate(R.menu.context);

        noteMenu.setOnClickListener(v -> popupMenu.show());

        popupMenu.setOnMenuItemClickListener(this);
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
        date.setText(note.getDate());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {   case R.id.context_modify:
                clickListener.click(R.id.context_modify, note, getAdapterPosition());
                    return true;
            case R.id.context_delete:
                clickListener.click(R.id.context_delete, note, getAdapterPosition());
                    return true;
            default:
                return false;
        }

    }
}
