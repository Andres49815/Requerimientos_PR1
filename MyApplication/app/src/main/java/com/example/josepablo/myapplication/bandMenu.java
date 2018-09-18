package com.example.josepablo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                Intent intent = new Intent(bandMenu.this, ClientBandProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bandMenu.this, createPost.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
            }
        });

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bandMenu.this, BandCreateEvent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
            }
        });
    }
}
