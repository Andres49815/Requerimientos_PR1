package com.example.josepablo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.josepablo.myapplication.model.Cookie;

public class clientMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);


        Button viewFavoriteBands = (Button) findViewById(R.id.viewFavoriteBands);

        Button postsClient = (Button) findViewById(R.id.postsClient);

        Button StoreClient = (Button) findViewById(R.id.StoreClient);

        Button eventsClient = (Button) findViewById(R.id.eventsClient);

        Button discoverClient = (Button) findViewById(R.id.discoverClient);

        Button LogoutClient = (Button) findViewById(R.id.LogoutClient);

        viewFavoriteBands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(clientMenu.this, bandsDiscover.class);
                intent.putExtra("typeLayout", Cookie.current_user_ID);
                startActivity(intent);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });

        StoreClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        discoverClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(clientMenu.this,bandsDiscover.class);
                intento.putExtra("typeLayout","B");
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });

        postsClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posts();
            }
        });


        eventsClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(clientMenu.this,EventsDisplay.class);
                intento.putExtra("eventType","A");
                intento.putExtra("userID",Cookie.current_user_ID);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });



        LogoutClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }


    private void posts() {
        Intent intento = new Intent(clientMenu.this, postsDisplay.class);
        intento.putExtra("typePosts","A");
        startActivity(intento);
        overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
    }


    private void logout() {
        Cookie.current_user_ID = null;
        Cookie.userType = null;
        Intent intento = new Intent(clientMenu.this, MainActivity.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
    }
}