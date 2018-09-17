package com.example.josepablo.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.AccountAdministrator;
import com.example.josepablo.myapplication.model.Cookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__singup);

        Button singUp = (Button) findViewById(R.id.BT_singUp);
        Button logIn = (Button) findViewById(R.id.BT_login);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this,singUpDisplay.class);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = ((EditText) findViewById(R.id.TX_userID)).getText().toString();
                String password = ((EditText) findViewById(R.id.TX_password)).getText().toString();

                AccountAdministrator.LogIn(user, password);
                RedirectToView();
            }
        });
    }


    private void verifyUser(String user, String password){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("select typeAccount from account " +
                    "where ID = ? and passW = ?");
            pst.setString(1,user);
            pst.setString(2,password);

            ResultSet rs = pst.executeQuery();
            rs.next();

            if(rs.getString("typeAccount") != null) {
                String mensaje = rs.getString("typeAccount");
                Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_SHORT).show();
                Cookie.current_user_ID = user;
                Cookie.userType = mensaje;
                Intent intento = new Intent(MainActivity.this,postsDisplay.class);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
            else {
                Toast.makeText(getApplicationContext(),"Not on DB", Toast.LENGTH_SHORT).show();
            }

        }

        catch (SQLException e){
            Toast.makeText(getApplicationContext(),"Not on DB", Toast.LENGTH_SHORT).show();
        }
    }

    // REDIRECT TO VIEW
    private void RedirectToView() {
        Intent intent;

        switch (AccountAdministrator.userType) {
            case "A":
                intent = new Intent(MainActivity.this, postsDisplay.class);
                intent.putExtra("typePosts","A");
                break;
            case "B":
                intent = new Intent(MainActivity.this, bandMenu.class);
                break;
            case "C":
                intent = null;
                break;
            default:
                intent = null;
        }
        try {
            Cookie.current_user_ID = AccountAdministrator.actualUser().getUserID();
            Cookie.userType = AccountAdministrator.userType;
            startActivity(intent);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Not on DB", Toast.LENGTH_SHORT).show();
        }
    }

    //Conection to DB
    public Connection conectionDB(){
        Connection conexion = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://musicbeansjar.database.windows.net:1433/musicBeansJAR;user=administrador@musicbeansjar;password=Inicio123;");
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }
}