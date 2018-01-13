package com.sphinax.hrms.employee.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.sphinax.hrms.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        List<EventDay> events = new ArrayList<>();

       /* Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_1));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 6);
        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));*/

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 5);
        events.add(new EventDay(calendar2, R.drawable.ic_menu_camera, Color.WHITE));

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -4);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 60);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getApplicationContext(),
                        eventDay.getCalendar().getTime().toString(),
                        Toast.LENGTH_SHORT).show());

        Button setDateButton = (Button) findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener((View v) -> {
            try {
                calendarView.setDate(getRandomCalendar());
            } catch (OutOfDateRangeException exception) {
                exception.printStackTrace();

              /*  Toast.makeText(getApplicationContext(),
                        "Date is out of range",
                        Toast.LENGTH_LONG).show();*/
            }
        });
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}
