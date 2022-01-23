package ru.gb.note.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ExitDialogFragment extends DialogFragment {

    public static final String TAG = "ExitDialogFragment";

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Activity activity = requireActivity();
        return new AlertDialog.Builder(activity)
                .setTitle("Выход из приложения")
                .setMessage("Вы действительно хотите закрыть приложение?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity, "Приложение закрыто", Toast.LENGTH_LONG).show();
                        activity.finish();
                    }
                })
                .setNegativeButton("Нет",null)
                .create();
    }

}
