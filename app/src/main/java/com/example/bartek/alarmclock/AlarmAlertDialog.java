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

public class AlarmAlertDialog extends Activity {
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_dialog_alert);

        AlertDialog.Builder alarmAlertDialog = new AlertDialog.Builder(AlarmAlertDialog.this);
        alarmAlertDialog.setTitle("ALARM!").
                setNegativeButton("Cancel", (dialogInterface, i) -> finish()).
                setPositiveButton("OK", (dialogInterface, i) -> finish());
        alarmAlertDialog.show();
    }
}
