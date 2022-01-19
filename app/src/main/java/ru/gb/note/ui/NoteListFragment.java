package ru.gb.note.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.PopupMenuItemClickListener;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class NoteListFragment extends Fragment implements NotesAdapter.OnNoteClickListener, PopupMenuItemClickListener {

    private RecyclerView list;

    private NotesAdapter adapter;

    private Repo repository = InMemoryRepoImpl.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_note, container, false);

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnPopupMenuClickListener(this);

        list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list.setAdapter(adapter);

        adapter.setOnNoteClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onNoteClick(Note note) {
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            onNoteClickLand(note);
        } else {
            onNoteClickPort(note);
        }
    }

    public void updateList(){
        adapter.notifyDataSetChanged();
    }

    private void onNoteClickPort(Note note) {
        if(requireActivity().getSupportFragmentManager().getBackStackEntryCount()>1){
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    private void onNoteClickLand(Note note) {
        if(requireActivity().getSupportFragmentManager().getBackStackEntryCount()>1){
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.edit_fragment, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }


    @Override
    public void click(int command, Note note, int position) {
        switch (command){
            case R.id.context_delete:
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);
                return;

            case R.id.context_modify:
                onNoteClick(note);
                return;


        }
    }
}
