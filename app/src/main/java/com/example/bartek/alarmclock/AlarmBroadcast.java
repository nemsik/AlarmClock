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
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show();

        req = intent.getIntExtra(MainActivity.alarm_requestcode, -1);
        time = intent.getLongExtra(MainActivity.alarm_time, -1);
        Log.d("BROADCAST ", time+"");
        date = new Date();
        date.setTime(time);
        Log.d("ALARM BROADCAST REQ ", ""+req);
        Log.d("ALARM BROADCAST DATE ", date.toString());

        Intent mintent = new Intent(context, AlarmAlertDialog.class);
        mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
        wl.release();
    }
}
