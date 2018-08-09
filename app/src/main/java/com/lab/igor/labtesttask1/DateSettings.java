//package com.lab.igor.labtesttask1;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.text.format.DateFormat;
//import android.widget.DatePicker;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
///**
// * Created by Igor on 29-Jul-18.
// */
//
//public class DateSettings implements DatePickerDialog.OnDateSetListener {
//    Context context;
//
//    public DateSettings(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        Toast.makeText(context, "selected date: " + i2 + "/" + i1 + "/" + i, Toast.LENGTH_SHORT).show();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, i);
//        calendar.set(Calendar.MONTH, i1);
//        calendar.set(Calendar.DAY_OF_MONTH, i2);
//        String currentDateString = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
//
//    }
//}
