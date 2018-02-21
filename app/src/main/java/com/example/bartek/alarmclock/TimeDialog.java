package com.example.bartek.alarmclock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bartek on 19.02.2018.
 */

public class TimeDialog extends AppCompatDialogFragment {

    private TimePicker timePicker;
    private int timeHour, timeMinute;
    private Handler handlerAlarm;
    private Bundle b;
    private Message msg;
    private Button buttondays;
    private final CharSequence[] items = {"Sunday", "Monday","Tuesday","Wednesday","Thursday", "Friday", "Saturday"};
    private ArrayList<Integer> seletedItems;

    public TimeDialog(Handler handlerAlarm, ArrayList<Integer> seletedItems) {
        this.handlerAlarm = handlerAlarm;
        this.seletedItems = seletedItems;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.time_layout, null);
        b = new Bundle();
        msg = new Message();

        if(seletedItems==null) seletedItems = new ArrayList<>();

        final CheckBox checkRepeat = (CheckBox)view.findViewById(R.id.checkBoxRepeat);

        try {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            timeHour = calendar.get(Calendar.HOUR_OF_DAY);
            timeMinute = calendar.get(Calendar.MINUTE);

           // Log.d(timeHour+" ", timeMinute+"");
        }catch (Exception e){
            Log.d("Alarm TimePiceker", e.toString());
        }


        builder.setView(view).setTitle("Set time").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                b.putInt("time_hour", timeHour);
                b.putInt("time_minute", timeMinute);
                Collections.sort(seletedItems);
                b.putIntegerArrayList("array_days", seletedItems);
                Log.d("ALARM DAYS", seletedItems.toString());
                msg.setData(b);
                handlerAlarm.sendMessage(msg);
            }
        });

        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                //Log.d("Alarm TIMEPICKER", "changed");
                timeHour = i;
                timeMinute = i1;
            }
        });

        final boolean[] checkedItems = new boolean[7];
        for(int i=0; i<7; i++){
            if(seletedItems.indexOf(i+1) !=-1) checkedItems[i] = true;
            else checkedItems[i] = false;
        }

        buttondays = (Button)view.findViewById(R.id.buttondays);
        buttondays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Select The Days")
                        .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    seletedItems.add(indexSelected+1);
                                } else if (seletedItems.contains(indexSelected)) {
                                    seletedItems.remove(Integer.valueOf(indexSelected+1));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create();
                dialog.show();

            }
        });

        checkRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRepeat.isChecked()){
                    b.putBoolean("repeat", true);
                }else b.putBoolean("repeat", false);
            }
        });
        return builder.create();
    }
}
