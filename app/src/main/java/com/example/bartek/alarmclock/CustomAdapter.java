package com.example.bartek.alarmclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by bartek on 18.02.2018.
 */

public class CustomAdapter extends ArrayAdapter<Alarm> {


    public CustomAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CustomAdapter(Context context, int customview, ArrayList<Alarm> alarmsList) {
        super(context, customview, alarmsList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = View.inflate(getContext(), R.layout.customview, null );
        TextView texttime = (TextView)v.findViewById(R.id.texttime);
        TextView textdays = (TextView)v.findViewById(R.id.textdays);
        TextView textrepeat = (TextView)v.findViewById(R.id.textRepeat);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.checkBox);

        final Alarm p = getItem(position);

        texttime.setText(p.getHour()+":"+p.getMinute());


        if(p.getRepeat()) textrepeat.setVisibility(TextView.VISIBLE);


        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<p.getDays().size(); i++){
            int day = p.getDays().get(i);
            switch (day){
                case 1: stringBuilder.append("Sun ");
                    break;
                case 2: stringBuilder.append("Mon ");
                    break;
                case 3: stringBuilder.append("Tue ");
                    break;
                case 4: stringBuilder.append("Wed ");
                    break;
                case 5: stringBuilder.append("Thu ");
                    break;
                case 6: stringBuilder.append("Fri ");
                    break;
                case 7: stringBuilder.append("Sat ");
                    break;
            }
        }
        stringBuilder.toString();
        textdays.setText(stringBuilder);


        if(p.getisIsenabled()==true){
            checkBox.setChecked(true);
        }else checkBox.setChecked(false);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getisIsenabled()==true){
                    p.cancelAlarm();
                }else p.setAlarm();
                notifyDataSetChanged();
            }
        });
        return v;
    }
}
