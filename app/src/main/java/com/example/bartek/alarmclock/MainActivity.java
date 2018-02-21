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

    private int timeHour, timeMinute;
    private long time;
    private Boolean repeat;
    private ArrayList<Alarm> alarmsList;
    private Button buttSetAlarm;
    private Context context;
    private Calendar c;
    private ListView listView;
    private CustomAdapter adapter;
    private boolean bnew = true;
    private int position;
    private ArrayList<Integer> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        buttSetAlarm = (Button)findViewById(R.id.button);


        c = Calendar.getInstance();

        alarmsList = new ArrayList();
        days = new ArrayList<Integer>();

        listView = (ListView)findViewById(R.id.listview);

        adapter = new CustomAdapter(this, R.layout.customview, alarmsList);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                bnew = false;

                showTimeDialog(alarmsList.get(position).getDays());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog);
                builder.setTitle("Delete").setMessage("Can you delete alarm?");
                builder.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alarmsList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialogInterface.cancel();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                return true;
            }
        });

        buttSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnew = true;
                showTimeDialog(null);
            }
        });


    }

    public class MyHandler extends Handler{
        public void handleMessage(Message msg){
            timeHour = msg.getData().getInt("time_hour");
            timeMinute = msg.getData().getInt("time_minute");
            repeat = msg.getData().getBoolean("repeat", false);
            days = msg.getData().getIntegerArrayList("array_days");
            if(bnew){
                alarmsList.add(new Alarm(context, alarmsList.size(), timeHour, timeMinute, alarmsList.size()-1, true, days, repeat));
                alarmsList.get(alarmsList.size()-1).setAlarm();
            }else{
                alarmsList.get(position).change(timeHour, timeMinute, days, repeat);
            }
            adapter.notifyDataSetChanged();
            //Log.d("Alarm msg", msg.toString());
        }
    }

    public void showTimeDialog(ArrayList<Integer> days){
        TimeDialog timeDialog = new TimeDialog(new MyHandler(), days);
        timeDialog.show(getSupportFragmentManager(), "timepicker");
    }
}
