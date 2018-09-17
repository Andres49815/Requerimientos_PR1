package com.example.josepablo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;
import com.example.josepablo.myapplication.model.Titular;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class postsDisplay extends AppCompatActivity {

    public Titular[] data;

    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_display);

        fillDataPosts();

        listView = (ListView) findViewById(R.id.Lista);

        Button backToMenu = (Button) findViewById(R.id.backToMenuPosts);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });


        Adaptador adaptador = new Adaptador(this, data);

        listView.setAdapter(adaptador);

        View header = getLayoutInflater().inflate(R.layout.header_list,null);

        listView.addHeaderView(header);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"c: "+ Integer.toString(data[i-1].getPostID()),Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(postsDisplay.this,noticiaExpandida.class);
                intento.putExtra("postID",data[i-1].getPostID());
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
            }
        });
    }


    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(postsDisplay.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(postsDisplay.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(postsDisplay.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }

    private void fillDataPosts(){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("select count(*) cont from post");
            ResultSet rs = pst.executeQuery();
            rs.next();
            int postCount = rs.getInt("cont");
            addPostsToArray(postCount);

        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void addPostsToArray(int count){

        try{
            data = new Titular[count];
            int pos = 0;
            PreparedStatement pst= conectionDB().prepareStatement("select * from post");
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                data[pos] = new Titular(rs.getInt("postID"),
                        rs.getString("poster_ID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        R.drawable.newspaper);
                pos++;
            }
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private String subString(String postContent){

        if(postContent.length() > 100){
            postContent = postContent.substring(0,100) ;
        }
        return postContent + "...";
    }

    /*Adapter for the list of posts*/
    private class Adaptador extends ArrayAdapter<Titular>{

        public Adaptador(@NonNull Context context, Titular[] datos) {
            super(context, R.layout.activity_item_posts, datos);
        }

        public View getView(final int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_item_posts,null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.lblTitle);
            lblTitulo.setText( data[pos].getTitle());

            TextView lblContent = (TextView) item.findViewById(R.id.lblContent);
            lblContent.setText( subString(data[pos].getContent()));

            TextView lblPublisher = (TextView) item.findViewById(R.id.lblPublisher);
            lblPublisher.setText( data[pos].getPublisher());

            ImageView thumNail = (ImageView) item.findViewById(R.id.thumbNail);
            thumNail.setImageResource(data[pos].getThumbNail());
/*
            Button botonDel = (Button) item.findViewById(R.id.postButtonTEST);
            botonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"titulo = "+data[pos].getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
*/
            return item;
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