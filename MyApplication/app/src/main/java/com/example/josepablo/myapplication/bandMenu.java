package com.example.josepablo.myapplication;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.util.EventLog;
>>>>>>> 905145cc2e0f73520c5834aaf48650c7a9340a62
import android.view.View;
import android.widget.Button;

public class bandMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_menu);

        // BUTTON DEFINITION
        Button Shop = (Button) findViewById(R.id.Shop);
        Button News = (Button) findViewById(R.id.News);
        Button Events = (Button) findViewById(R.id.Events);

        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
