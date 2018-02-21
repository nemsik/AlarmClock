package com.example.bartek.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by bartek on 21.02.2018.
 */

public class AlarmAlertDialog extends AppCompatDialogFragment {


    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        AlertDialog.Builder alarmAlertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alarm_dialog_alert, null);

        alarmAlertDialog.setTitle("ALARM!").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return alarmAlertDialog.create();
    }
}
