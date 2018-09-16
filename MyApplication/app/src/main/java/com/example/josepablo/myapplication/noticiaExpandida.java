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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;
import com.example.josepablo.myapplication.model.Titular;
import com.example.josepablo.myapplication.model.comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class noticiaExpandida extends AppCompatActivity {

    private String title;
    private String content;
    private int postID;
    private String publisher;
    private int thumbnail;
    private ListView listComments;
    private comment[] commentsArray;
    private int amountOfComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_expandida);
        Button commentOnPost = (Button) findViewById(R.id.BT_makeComment) ;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.postID = (int) bundle.get("postID");
        getDataPost();
        setDataPost();

        listComments = (ListView) findViewById(R.id.commentsDisplay);

        AdapterForComments adapterForComments = new AdapterForComments(this, commentsArray);

        listComments.setAdapter(adapterForComments);

        View header = getLayoutInflater().inflate(R.layout.header_list,null);

        listComments.addHeaderView(header);

        commentOnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentTyped = ((EditText) findViewById(R.id.TX_commentTyped )).getText().toString();
                if(commentTyped.compareTo("") != 0){
                    addComentToPost(commentTyped);
                }
                else{
                    Toast.makeText(getApplicationContext(),"The comment cant be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void addComentToPost(String commentTyped){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("insert into comment values" +
                    "(?,?,?,?)");
            pst.setInt(1,amountOfComments + 1);
            pst.setInt(2,postID);
            pst.setString(3,Cookie.current_user_ID);
            pst.setString(4, commentTyped);
            pst.executeUpdate();
            Intent intento = new Intent(noticiaExpandida.this,noticiaExpandida.class);
            intento.putExtra("postID",postID);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    private void getDataPost(){
        try{
            PreparedStatement pst=conectionDB().prepareStatement
                    ("select * from post where postID = ?");
            pst.setInt(1,this.postID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            title = rs.getString("title");
            content = rs.getString("content");
            publisher = rs.getString("poster_ID");
            thumbnail = R.drawable.newpost;

            PreparedStatement commentCount =conectionDB().prepareStatement
                    ("select count(*) cont from comment where postID = ?");
            commentCount.setInt(1,this.postID);
            ResultSet resultCount = commentCount.executeQuery();
            resultCount.next();
            amountOfComments = resultCount.getInt("cont");
            getCommentsPost();

        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDataPost(){
        TextView TX_Title = (TextView) findViewById(R.id.TX_titleExpandedPost);
        TX_Title.setText(this.title);

        TextView TX_Content = (TextView) findViewById(R.id.TX_contentExpandedPost);
        TX_Content.setText(this.content);

        TextView TX_publisher = (TextView) findViewById(R.id.TX_publisherExpandedPost);
        TX_publisher.setText(this.publisher);

        ImageView IM_thumbNail = (ImageView) findViewById(R.id.thumbNailExpandedPost);
        IM_thumbNail.setImageResource(thumbnail );
    }

    private void getCommentsPost(){
        try{
            commentsArray = new comment[amountOfComments];
            int pos = 0;
            PreparedStatement pst =conectionDB().prepareStatement
                    ("select * from comment where postID = ?");
            pst.setInt(1,this.postID);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                commentsArray[pos] = new comment(rs.getString("commentator_ID"),
                        rs.getString("content"),
                        R.drawable.newspaper);
                pos++;
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
}

    /*Adapter for the list of posts*/
    class AdapterForComments extends ArrayAdapter<comment> {

        public AdapterForComments(@NonNull Context context, comment[] commentsArray) {
            super(context, R.layout.activity_item_comment, commentsArray);
        }

        public View getView(int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_item_comment,null);

            try{
                TextView lblUser = (TextView) item.findViewById(R.id.userID_ItemComment);
                lblUser.setText( commentsArray[pos].getUserID() );

                TextView lblContent = (TextView) item.findViewById(R.id.content_ItemComment);
                lblContent.setText( commentsArray[pos].getContent()) ;

                ImageView imageThumbnail = (ImageView) item.findViewById(R.id.thumbNail_ItemComment);
                imageThumbnail.setImageResource(R.drawable.newpost);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return item;
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
