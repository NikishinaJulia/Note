package ru.gb.note.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.note.R;
import ru.gb.note.data.Note;
import ru.gb.note.data.PopupMenuItemClickListener;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {

    private PopupMenuItemClickListener clickListener;

    public void setOnPopupMenuClickListener(PopupMenuItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private List<Note> notes = new ArrayList<>();

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void delete(List<Note> all, int position) {
        this.notes = all;
        notifyItemRemoved(position);
    }

    public interface OnNoteClickListener{
        void onNoteClick(Note note);
    }

    private OnNoteClickListener listener;

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view, listener, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note =  notes.get(position);
        holder.bind(note);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
