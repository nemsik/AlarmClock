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
        buttondays = (Button)view.findViewById(R.id.buttondays);
        final CheckBox checkRepeat = (CheckBox)view.findViewById(R.id.checkBoxRepeat);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        b = new Bundle();
        msg = new Message();
        if(seletedItems==null) seletedItems = new ArrayList<>();

        try {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            timeHour = calendar.get(Calendar.HOUR_OF_DAY);
            timeMinute = calendar.get(Calendar.MINUTE);
        }catch (Exception e){
            Log.d("Alarm TimePiceker", e.toString());
        }

        builder.setView(view).setTitle("Set time").setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).setPositiveButton("OK", (dialogInterface, i) -> clickOk());

        timePicker.setOnTimeChangedListener((timePicker, i, i1) -> {
            timeHour = i;
            timeMinute = i1;
        });

        buttondays.setOnClickListener(view1 -> chooseDays());

        checkRepeat.setOnClickListener(view12 -> {
            if(checkRepeat.isChecked()){
                b.putBoolean("repeat", true);
            }else b.putBoolean("repeat", false);
        });
        return builder.create();
    }

    private void clickOk(){
        Collections.sort(seletedItems);
        b.putInt(MainActivity.alarm_hour, timeHour);
        b.putInt(MainActivity.alarm_minute, timeMinute);
        b.putIntegerArrayList(MainActivity.alarm_days, seletedItems);
        Log.d("ALARM DAYS", seletedItems.toString());
        msg.setData(b);
        handlerAlarm.sendMessage(msg);
    }

    private void chooseDays(){
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Select The Days")
                .setMultiChoiceItems(items, checkSelectedItems(), (dialog13, indexSelected, isChecked) -> {
                    if (isChecked) {
                        seletedItems.add(indexSelected+1);
                    } else if (seletedItems.contains(indexSelected+1)) {
                        seletedItems.remove(Integer.valueOf(indexSelected+1));
                    }
                }).setPositiveButton("OK", (dialog1, id) -> {
                }).setNegativeButton("Cancel", (dialog12, id) -> {
                }).create();
        dialog.show();
    }

    private boolean[] checkSelectedItems(){
        final boolean[] checkedItems = new boolean[7];
        for(int i=0; i<7; i++){
            if(seletedItems.indexOf(i+1) !=-1) checkedItems[i] = true;
            else checkedItems[i] = false;
        }
        return checkedItems;
    }
}
