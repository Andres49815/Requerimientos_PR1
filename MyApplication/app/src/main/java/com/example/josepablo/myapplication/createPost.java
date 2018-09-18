package com.example.josepablo.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class createPost extends AppCompatActivity {


    private int postsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Button backToMenuCreatePost = (Button) findViewById(R.id.backToMenuCreatePost);
        backToMenuCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });

        final Button createPost = (Button) findViewById(R.id.createPost);

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Implement with image
                String titlePost = ((TextView) findViewById(R.id.titlePost)).getText().toString();
                String contentPost = ((TextView) findViewById(R.id.contentPost)).getText().toString();
                calculatePostAmount();
                createThePost(titlePost,contentPost);
            }
        });
    }

    private void calculatePostAmount(){
        try{
            PreparedStatement commentCount =conectionDB().prepareStatement
                    ("select count(*) cont from post");
            ResultSet resultCount = commentCount.executeQuery();
            resultCount.next();
            postsAmount = resultCount.getInt("cont");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void createThePost(String theTitle, String theContent){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("insert into post values(?,?,?,?,null)");
            pst.setInt(1, postsAmount);
            pst.setString(2, Cookie.current_user_ID);
            pst.setString(3, theTitle);
            pst.setString(4, theContent);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"Created", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(createPost.this, postsDisplay.class);
            intento.putExtra("typePosts","A");
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(createPost.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(createPost.this, bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(createPost.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }

    /*Data base connection*/
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
