package com.example.myapplicatione9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Map<String, String> event_list = new HashMap<String, String>();
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        final TextView eventDisplay = (TextView) findViewById(R.id.event_display);
        eventDisplay.setText("No event");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                selectedDate = "" + year + month + day;
                String event = getEvent(selectedDate);
                eventDisplay.setText(event);
            }
        });
    }

    public String getEvent(String selectedDate) {
        String event;
        if (event_list.containsKey(selectedDate)) {
            event = "Event: " + event_list.get(selectedDate);
        } else {
            event = "No event";
        }
        return event;
    }

    public void deleteCalendarEvent(View view) {
        if (event_list.containsKey(selectedDate)) {
            event_list.remove(selectedDate);
            TextView eventDisplay = (TextView) findViewById(R.id.event_display);
            eventDisplay.setText(getEvent(selectedDate));
        }
    }

    public void addCalendarEvent(View view) {
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        final Long date = calendarView.getDate();
        String formerEvent = getEvent(selectedDate);
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.event_input, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // pre populate former event
        final EditText inputView = (EditText) findViewById(R.id.event_input);
        if (!formerEvent.equals("No event")) {
            inputView.setText(formerEvent);
        }

//      dismiss popup window by clicking cancel button
        Button buttonOk = popupView.findViewById(R.id.ok);
        final TextView eventDisplay = (TextView) findViewById(R.id.event_display);
        buttonOk.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputView = (EditText) popupView.findViewById(R.id.event_input);
                String input = inputView.getText().toString();
                event_list.put(selectedDate, input);
                popupWindow.dismiss();
                eventDisplay.setText(getEvent(selectedDate));
            }
        }));
    }

}