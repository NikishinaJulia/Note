package ru.gb.note.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.gb.note.R;
import ru.gb.note.data.Constants;
import ru.gb.note.data.InMemoryRepoImpl;
import ru.gb.note.data.Note;
import ru.gb.note.data.Repo;
import ru.gb.note.recycler.NotesAdapter;

public class MainNotesActivity extends AppCompatActivity {

    public static final String NOTE_LIST = "NOTE_LIST";
    public static final String NOTE_ABOUT = "NOTE_ABOUT";
    public static final String NOTE_VALUE = "NOTE_VALUE";
    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        if (savedInstanceState == null) {
            fillRepo();
        }

        initToolbarAndDrawer();

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list_fragment, new NoteListFragment(), NOTE_LIST);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }else {
            Fragment noteAboutFragment = getSupportFragmentManager().findFragmentByTag(NOTE_ABOUT);
            if(noteAboutFragment!=null){
                openAbout();
            }else{
                openHome();
            }

        }
    }

    private void openHome() {
        Fragment noteValueFragment = getSupportFragmentManager().findFragmentByTag(NOTE_VALUE);
        Fragment noteListFragment = getSupportFragmentManager().findFragmentByTag(NOTE_LIST);
        if (noteValueFragment != null) {
            FragmentManager fm = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;

            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                fm.popBackStack();
                fm.executePendingTransactions();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, noteListFragment, NOTE_LIST);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.edit_fragment,
                        EditNoteFragment.newInstance(noteValueFragment.getArguments()),
                        NOTE_VALUE);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            } else {
                fm.popBackStack();
                fm.executePendingTransactions();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment,
                        noteListFragment,
                        NOTE_LIST);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment,
                        EditNoteFragment.newInstance(noteValueFragment.getArguments()),
                        NOTE_VALUE);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        }else {
            FragmentManager fm = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            fm.popBackStack();
            fm.executePendingTransactions();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.list_fragment, new NoteListFragment(), NOTE_LIST);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        // ?????????????? DrawerLayout
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // ?????????????? ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.nav_app_bar_open_drawer_description,
                R.string.nav_app_bar_open_drawer_description);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // ?????????????????? ???????????????????????????? ????????
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_drawer_home:
                    openHome();
                    drawer.close();
                    return true;
                case R.id.action_drawer_about:
                    openAbout();
                    drawer.close();
                    return true;
            }
            return false;
        });
    }

    private void openAbout() {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        fm.popBackStack();
        fm.executePendingTransactions();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, AboutNoteFragment.newInstance(), NOTE_ABOUT);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void fillRepo() {
        repository.create(new Note("???????????? 1", "???????????????? 1 "));
        repository.create(new Note("???????????? 2", "???????????????? 2 "));
        repository.create(new Note("???????????? 3", "???????????????? 3 "));
        repository.create(new Note("???????????? 4", "???????????????? 4 "));
        repository.create(new Note("???????????? 5", "???????????????? 5 "));
        repository.create(new Note("???????????? 6", "???????????????? 6 "));
        repository.create(new Note("???????????? 7", "???????????????? 7 "));
        repository.create(new Note("???????????? 8", "???????????????? 8 "));
        repository.create(new Note("???????????? 9", "???????????????? 9 "));
        repository.create(new Note("???????????? 10", "???????????????? 10 "));
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

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment.isVisible()) {
                    FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                    if (childFragmentManager.getBackStackEntryCount() > 0) {
                        childFragmentManager.popBackStack();
                    }
                }
            }
            super.onBackPressed();
        }else {
            new ExitDialogFragment().show(getSupportFragmentManager(), ExitDialogFragment.TAG);
        }
    }

    private void onNoteClickPort(Note note) {
        if(this.getSupportFragmentManager().getBackStackEntryCount()>0){
            this.getSupportFragmentManager().popBackStack();
        }
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, detail, NOTE_VALUE);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void onNoteClickLand(Note note) {
        if(this.getSupportFragmentManager().getBackStackEntryCount()>0){
            this.getSupportFragmentManager().popBackStack();
        }
        EditNoteFragment detail = EditNoteFragment.newInstance(note);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.edit_fragment, detail, NOTE_VALUE);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }


}