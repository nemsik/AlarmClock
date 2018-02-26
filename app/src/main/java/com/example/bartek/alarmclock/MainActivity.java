package com.example.bartek.alarmclock;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int hour, minute, position;
    private ArrayList<Alarm> alarmsList;
    private Button buttSetAlarm;
    private Context context;
    private ListView listView;
    private CustomAdapter adapter;
    private boolean repeat, newDay = true;
    private ArrayList<Integer> days;

    final static String alarm_hour = "alarm_hour";
    final static String alarm_minute = "alarm_minute";
    final static String alarm_repeat = "alarm_repeat";
    final static String alarm_days = "alarm_days";
    final static String alarm_requestcode = "alarm_requestcode";
    final static String alarm_date = "alarm_date";
    final static String alarm_time = "alarm_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        buttSetAlarm = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listview);

        alarmsList = new ArrayList<Alarm>();
        days = new ArrayList<Integer>();

        adapter = new CustomAdapter(this, R.layout.customview, alarmsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> changeSelectedAlarm(position));
        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            deleteAlarm(position);
            return true;
        });
        buttSetAlarm.setOnClickListener(view -> {
            newDay = true;
            showTimeDialog(null);
        });
    }

    public void showTimeDialog(ArrayList<Integer> days){
        TimeDialog timeDialog = new TimeDialog(new TimeDialogHandler(), days);
        timeDialog.show(getSupportFragmentManager(), "timepicker");
    }

    public void changeSelectedAlarm(int i){
        position = i;
        showTimeDialog(alarmsList.get(position).getDays());
        newDay = false;
    }

    public void deleteAlarm(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle("Delete").setMessage("Can you delete alarm?");
        builder.setPositiveButton("YES!", (dialogInterface, i) -> {
            alarmsList.get(position).cancelAlarm();
            alarmsList.remove(position);
            adapter.notifyDataSetChanged();
            dialogInterface.cancel();
        }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel()).show();
    }

    public class TimeDialogHandler extends Handler{
        public void handleMessage(Message msg){
            hour = msg.getData().getInt(alarm_hour);
            minute = msg.getData().getInt(alarm_minute);
            repeat = msg.getData().getBoolean(alarm_repeat, false);
            days = msg.getData().getIntegerArrayList(alarm_days);
            if(newDay){
                alarmsList.add(new Alarm(context, alarmsList.size(), hour, minute,
                        alarmsList.size()-1, true, days, repeat));
                alarmsList.get(alarmsList.size()-1).setAlarm();
            }else{
                alarmsList.get(position).change(hour, minute, days, repeat);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
