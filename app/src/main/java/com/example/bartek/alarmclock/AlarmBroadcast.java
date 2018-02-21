package com.example.bartek.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

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
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();


        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        req = intent.getIntExtra("REQUESTCODE", -1);
        time = intent.getLongExtra("TIME", -1);
        date = new Date();
        date.setTime(time);
        Log.d("ALARM BROADCAST REQ ", ""+req);
        Log.d("Alarm BROADCAST DATE ", date.toString());

        wl.release();
    }
}
