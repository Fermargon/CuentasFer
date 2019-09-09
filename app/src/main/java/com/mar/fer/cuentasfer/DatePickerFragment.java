package com.mar.fer.cuentasfer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jahid on 12/10/15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    TextView etiqFecha;
    TextView etiqUsuario;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

        Date    daFecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        etiqFecha = (TextView) getActivity().findViewById(R.id.etFecha);
        String  stFecha = etiqFecha.getText().toString();

        try {
            daFecha = sdf.parse(stFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        c.setTime(daFecha);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        etiqFecha = (TextView) getActivity().findViewById(R.id.etFecha);
        etiqUsuario= (TextView) getActivity().findViewById(R.id.etUsuario);

        month ++;

        String stDia = String.valueOf(day);
        String stMes = String.valueOf(month);

        if (day < 10){
            stDia = "0" + String.valueOf(day);
        }
        if (month < 10){
            stMes = "0" + String.valueOf(month);
        }

        etiqFecha.setText(stDia+"/"+stMes+"/"+year);
    }
}