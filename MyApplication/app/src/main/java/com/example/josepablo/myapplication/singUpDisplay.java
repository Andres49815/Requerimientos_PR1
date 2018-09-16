package com.example.josepablo.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class singUpDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_display);

        Button singUp = (Button) findViewById(R.id.BT_singUp);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Image implementation is on wait
                String userName = ((EditText) findViewById(R.id.TX_userName)).getText().toString();
                String lastName = ((EditText) findViewById(R.id.TX_lastName)).getText().toString();
                String email = ((EditText) findViewById(R.id.TX_userEmail)).getText().toString();
                String password = ((EditText) findViewById(R.id.TX_password)).getText().toString();
                singUpAccount(userName, lastName,email,password);
            }
        });
    }

    /*Not top 10, more like top 3,i am not 2, cause nobody can top me*/
    public void singUpAccount(String userName, String lastName, String email, String password){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("insert into account values" +
                    "(?,?,'C',null,?,?)");
            pst.setString(1,email);
            pst.setString(2,password);
            pst.setString(3,userName);
            pst.setString(4,lastName);
            pst.executeUpdate();
            Intent intento = new Intent(singUpDisplay.this,MainActivity.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),"Account not valid " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
