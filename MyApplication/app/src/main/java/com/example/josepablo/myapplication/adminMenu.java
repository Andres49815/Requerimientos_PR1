package com.example.josepablo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.josepablo.myapplication.model.Cookie;

public class adminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        Button createBand = (Button) findViewById(R.id.createBand);

        Button managePostAdmin = (Button) findViewById(R.id.managePostAdmin);

        Button clientList = (Button) findViewById(R.id.clientList);

        Button createPostAdmin = (Button) findViewById(R.id.createPostAdmin);

        Button postsAdmin = (Button) findViewById(R.id.postsAdmin);

        Button StoreAdmin = (Button) findViewById(R.id.StoreAdmin);

        Button eventsAdmin = (Button) findViewById(R.id.eventsAdmin);

        Button discoverAdmin = (Button) findViewById(R.id.discoverAdmin);

        Button profileAdmin = (Button) findViewById(R.id.profileAdmin);

        Button LogoutAdmin = (Button) findViewById(R.id.LogoutAdmin);


        createBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        managePostAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        clientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(adminMenu.this,clientList.class);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });

        createPostAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });

        postsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posts();
            }
        });

        StoreAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        eventsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        discoverAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAdmin();
            }
        });

        profileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void createPost(){
        Intent intento = new Intent(adminMenu.this,createPost.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
    }


    private void posts(){
        Intent intento = new Intent(adminMenu.this,postsDisplay.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
    }

    private void discover(){ //Change to discover activity
        Intent intento = new Intent(adminMenu.this,MainActivity.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
    }


    private void profileAdmin(){//Profile admin
        Intent intento = new Intent(adminMenu.this,AccountProfile.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
    }

    private void logout(){
        Cookie.current_user_ID = null;
        Cookie.userType = null;
        Intent intento = new Intent(adminMenu.this,MainActivity.class);
        startActivity(intento);
        overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
    }


}