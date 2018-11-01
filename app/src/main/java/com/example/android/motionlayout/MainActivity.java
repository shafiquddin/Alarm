package com.example.android.motionlayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    private String format = "";
    TextView list;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker=findViewById(R.id.timePicker);
        list=findViewById(R.id.alarmList);
       findViewById(R.id.btnsetlarm).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar calendar=Calendar.getInstance();
               if(Build.VERSION.SDK_INT>=23) {
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_MONTH),
                           timePicker.getHour(),
                           timePicker.getMinute(),
                           0
                   );
               }
               else{
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_MONTH),
                           timePicker.getCurrentHour(),
                           timePicker.getCurrentMinute(),
                           0
                   );
               }
               setAlarm(calendar.getTimeInMillis());

           }
       });


    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        ArrayList<PendingIntent> intentsArr=new ArrayList<>();
//        for(int i=0;i<10;++i) {
            Intent intent = new Intent(this, MyAlarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
            int hour = timePicker.getCurrentHour();
            int min = timePicker.getCurrentMinute();
            showTime(hour, min);
//            intentsArr.add(pendingIntent);
//        }
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
        list.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format).append("\n"));
    }
}
