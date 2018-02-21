package com.example.bartek.alarmclock;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by bartek on 18.02.2018.
 */

public class Alarm {
    private Context context;
    private int requestcode, hour, minute, id;
    private long time;
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private Calendar c;
    private boolean isenabled;
    private ArrayList<Integer> days;
    private ArrayList<PendingIntent> pendingIntents;
    private Date date;
    private Boolean repeat;
    private long difftime;

    public Alarm(Context context, int id, int hour, int minute, int requestcode, boolean isenabled, ArrayList<Integer> days, boolean repeat) {
        this.context = context;
        this.hour = hour;
        this.minute = minute;
        this.requestcode = requestcode;
        this.isenabled = isenabled;
        c = Calendar.getInstance(Locale.getDefault());
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        time = c.getTimeInMillis();
        this.days = days;
        this.repeat = repeat;
        this.id = id;
    }

    public void setAlarm(){

        alarmManager=(AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);

        if(c.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            c.add(Calendar.DAY_OF_WEEK, 1);
            //Log.d("DATE day of weeks ", c.get(Calendar.DAY_OF_WEEK)+"");
        }

        time = c.getTimeInMillis();

        pendingIntents = new ArrayList<>();

        Intent intent = new Intent(this.context, AlarmBroadcast.class);


        Random generator = new Random();
        int rg = generator.nextInt(100) + requestcode;
        requestcode = requestcode * rg;

        date = new Date();
        date.setTime(time);

        Log.d("ALARM DATE ", date.toString());
        Log.d("ALARM REQUESTCODE", requestcode+"");

        intent.putExtra("REQUESTCODE", requestcode);
        intent.putExtra("TIME", time);

        pi = PendingIntent.getBroadcast(this.context, requestcode, intent, 0);


        if(days.isEmpty()) {

            /*
                Jeżeli użytkownik nie wybierze dnia ustawiamy najbliższy możliwy dzień
            */
            days.add(c.get(Calendar.DAY_OF_WEEK));

            time = c.getTimeInMillis();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pi);

            /*
            date = new Date();
            date.setTime(time);
            Log.d("DATE SET", date.toString());
            */
        }
        else {
            for(int i=0; i<days.size(); i++){
                c = Calendar.getInstance();
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.DAY_OF_WEEK, days.get(i));
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);

                if(c.getTimeInMillis() < System.currentTimeMillis()) {
                    c.add(Calendar.DAY_OF_YEAR, 7);
                }
                time = c.getTimeInMillis();


                date = new Date();
                date.setTime(time);
                Log.d("ALARM DATE ", date.toString());

                generator = new Random();
                rg = generator.nextInt(100) + i;

                requestcode = requestcode * rg;
                Log.d("ALARM REQUESTCODE", requestcode+"");

                intent = new Intent(this.context, AlarmBroadcast.class);
                intent.putExtra("REQUESTCODE", requestcode);
                intent.putExtra("TIME", time);
                pi = PendingIntent.getBroadcast(this.context, requestcode, intent, 0);
                pendingIntents.add(pi);
                if(!repeat) alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pi);
                else alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY*7, pi);
            }
        }

        isenabled = true;
        Log.d("DATE DAY", getDays().toString());

        difftime = c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        long seconds = difftime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        long days = hours / 24;
        hours = hours % 24;

        StringBuilder builder = new StringBuilder();
        builder.append("Start in: ");
        if(days!=0) builder.append(days).append(" day(s) ");
        if (hours!=0) builder.append(hours).append(" hour(s) ");
        if (minutes!=0) builder.append(minutes).append(" minute(s)");
        if(days==0 && hours==0 && minutes==0) builder.append("less than a minute");

        Toast.makeText(context, builder.toString(), Toast.LENGTH_LONG).show();

    }

    public void cancelAlarm(){
        if(pendingIntents.isEmpty()){
            if(alarmManager!=null){
                alarmManager.cancel(pi);
                Log.d("Alarm", "Cancel");
            }
        }else {
            for(int i=0; i<pendingIntents.size(); i++){
                if(alarmManager!=null){
                    alarmManager.cancel(pendingIntents.get(i));
                }
            }
        }

        isenabled = false;
    }

    public void change(int hour, int minute, ArrayList<Integer> days, boolean repeat){
        if(alarmManager!=null){
            cancelAlarm();
            this.hour = hour;
            this.minute = minute;
            this.days = days;
            this.repeat = repeat;

            c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);

            setAlarm();
        }
    }

    public long getTime() {
        return time;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getRequestcode() {
        return requestcode;
    }

    public boolean getisIsenabled() {
        return isenabled;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public int getId() {
        return id;
    }

    public Boolean getRepeat() {
        return repeat;
    }
}
