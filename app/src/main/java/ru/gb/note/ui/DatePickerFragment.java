package ru.gb.note.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private String currentValueDate;

    public DatePickerFragment(DatePickerDialog.OnDateSetListener listener,  String currentValueDate){
        this.listener = listener;
        this.currentValueDate = currentValueDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();

        if(currentValueDate !=null){
            try {
                Date parse = format1.parse(currentValueDate);
                c.setTime(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }


}
