package com.example.bartek.alarmclock;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bartek on 18.02.2018.
 */

public class AlarmBroadcast extends BroadcastReceiver {
    private int req;
    private Long time;
    private Date date;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wake_up");
        wl.acquire();


        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        req = intent.getIntExtra("REQUESTCODE", -1);
        time = intent.getLongExtra("TIME", -1);
        date = new Date();
        date.setTime(time);
        Log.d("ALARM BROADCAST REQ ", ""+req);
        Log.d("Alarm BROADCAST DATE ", date.toString());


        /*
        W jaki sposób wyświetlić ten dialog?


        AlarmAlertDialog alarmAlertDialog = new AlarmAlertDialog();
        alarmAlertDialog.show();

        */

        Intent mintent = new Intent(context, AlarmAlertDialog.class);
        mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
        wl.release();
    }
}
