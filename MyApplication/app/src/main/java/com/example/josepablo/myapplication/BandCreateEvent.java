package com.example.josepablo.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.AccountAdministrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class BandCreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_create_event);

        final CalendarView calendar = (CalendarView) findViewById(R.id.CAL_EventDate);
        Button CreateEvent = (Button) findViewById(R.id.BT_CreateEvent);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, date);
                calendarView.setDate(c.getTimeInMillis());
            }
        });

        CreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventDate = "";
                Date calendar = new Date(((CalendarView)findViewById(R.id.CAL_EventDate)).getDate());
                try {
                    eventDate = calendar.getYear() + 2000 - 100 + "-";
                    eventDate += calendar.getMonth() + 1 + "-";
                }
                catch (Exception e) {
                    eventDate += "1";
                }
                finally {
                    eventDate += calendar.getDate();
                }
                try {
                    Connection connection = AccountAdministrator.connectionDB();
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO dbo.BandEvent VALUES(?, 0, ?, ?, ?)"
                    );
                    String band = AccountAdministrator.actualUser().getUserID();
                    String place = ((EditText)findViewById(R.id.TX_Place)).getText().toString();
                    String details = ((EditText)findViewById(R.id.TX_Details)).getText().toString();
                    AccountAdministrator.SetStatement(statement, band, place, eventDate, details);

                    statement.executeQuery();
                }
                catch (SQLException e) { }
            }
        });

    }
}
