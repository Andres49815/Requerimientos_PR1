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

public class ManagePosts extends AppCompatActivity {

    Titular[] postsForManage;
    String ownerPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_posts);

        Button backToMenu = (Button) findViewById(R.id.backToMenuManagePosts);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ownerPosts = (String) bundle.get("userID");

        fillDataForManage(ownerPosts);

        ListView listOfPosts = (ListView) findViewById(R.id.listManagePosts);


        AdaptaterForManagePosts adaptador = new AdaptaterForManagePosts(this, postsForManage);
        listOfPosts.setAdapter(adaptador);
        View header = getLayoutInflater().inflate(R.layout.header_list,null);
        listOfPosts.addHeaderView(header);

    }

    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(ManagePosts.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(ManagePosts.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(ManagePosts.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }



    private void fillDataForManage(String userID){

        try{
            postsForManage = new Titular[countPostsOfUser(userID)];
            int pos = 0;

            PreparedStatement pst= conectionDB().prepareStatement("select * from post where poster_ID = ?" );
            pst.setString(1,userID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                postsForManage[pos] = new Titular(rs.getInt("postID"),
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


    private int countPostsOfUser(String userID){
        int result = 0;
        try{
            PreparedStatement pst= conectionDB().prepareStatement(  "select count(*) cont from post where poster_ID = ?" );
            pst.setString(1,userID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            result = rs.getInt("cont");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return result;

    }

    private void deletePostByID(int postID){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("delete from post where postID = ?");
            pst.setInt(1,postID);
            pst.executeUpdate();
            Intent intento = new Intent(ManagePosts.this,ManagePosts.class);
            intento.putExtra("userID",ownerPosts);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Adapter for the list of posts*/
    private class AdaptaterForManagePosts extends ArrayAdapter<Titular> {

        public AdaptaterForManagePosts(@NonNull Context context, Titular[] datos) {
            super(context, R.layout.activity_manage_posts_item, datos);
        }

        public View getView(final int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_manage_posts_item,null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.titleManagePost);
            lblTitulo.setText( postsForManage[pos].getTitle());

            ImageView lblContent = (ImageView) item.findViewById(R.id.thumbNailManagePost);
            lblContent.setImageResource( postsForManage[pos].getThumbNail());

            Button buttonViewPost = (Button) item.findViewById(R.id.buttonViewPost);
            buttonViewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent( ManagePosts.this,noticiaExpandida.class);
                    intento.putExtra("postID",postsForManage[pos].getPostID());
                    startActivity(intento);
                    overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
                }
            });

            Button buttonDeletePost = (Button) item.findViewById(R.id.buttonDeletePost);
            buttonDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePostByID(postsForManage[pos].getPostID());
                }
            });
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
