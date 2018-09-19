package com.example.josepablo.myapplication;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Titular;
import com.example.josepablo.myapplication.model.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clientList extends AppCompatActivity {


    UserAccount[] clientsArray;
    int imageorDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String typeForAccount = (String) bundle.get("typeAccount");

        fillClientList(typeForAccount);
        ListView clientList = (ListView) findViewById(R.id.listClientListView);

        clientListAdapter  clientListAdapter = new clientListAdapter(this, clientsArray);

        clientList.setAdapter(clientListAdapter);
        imageorDisplay= R.drawable.man;
        if (typeForAccount.compareTo("B") == 0) {
            imageorDisplay = R.drawable.band;
        }

        View header = getLayoutInflater().inflate(R.layout.header_list,null);

        clientList.addHeaderView(header);

        clientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"c: "+ clientsArray[i-1].getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private int getClientsAmount(String typeAccount){
        int postCount = 0;
        try{
            PreparedStatement pst=conectionDB().prepareStatement("select count(*) cont from " +
                    "account where typeAccount = ? ");
            pst.setString(1,typeAccount);
            ResultSet rs = pst.executeQuery();

            rs.next();
            postCount = rs.getInt("cont");

        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return postCount;
    }


    private void fillClientList(String typeAccount){
        try{

            clientsArray = new UserAccount[getClientsAmount(typeAccount)];

            PreparedStatement pst=conectionDB().prepareStatement("select * from account where typeAccount = ?");
            pst.setString(1,typeAccount);
            ResultSet rs = pst.executeQuery();

            int pos = 0;
            while(rs.next()){
                clientsArray[pos] = new UserAccount(rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("userLastName"),
                        imageorDisplay); //Imagenes
                pos++;
            }
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

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


    public void deleteClientByID(String userID){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("delete from account where ID = ?");
            pst.setString(1,userID);
            pst.executeUpdate();
            Intent intento = new Intent(clientList.this,postsDisplay.class);
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

    /*Class for adapter and shit*/
    class clientListAdapter extends ArrayAdapter<UserAccount> {

        public clientListAdapter(@NonNull Context context, UserAccount[] clientsArray) {
            super(context, R.layout.activity_client_list_item, clientsArray);
        }

        public View getView(final int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_client_list_item,null);

            TextView idClientOnList = (TextView) item.findViewById(R.id.idClientOnList);
            idClientOnList.setText( clientsArray[pos].getUserID());

            TextView nameClientOnList = (TextView) item.findViewById(R.id.nameClientOnList);
            nameClientOnList.setText( clientsArray[pos].getName());

            TextView LastNameOnClientList = (TextView) item.findViewById(R.id.LastNameOnClientList);
            LastNameOnClientList.setText( clientsArray[pos].getLastName());

            ImageView profilePicClientOnList = (ImageView) item.findViewById(R.id.profilePicClientOnList);
            profilePicClientOnList.setImageResource( imageorDisplay);

            Button botonDel = (Button) item.findViewById(R.id.deleteButtonOnClientList);
            botonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteClientByID(clientsArray[pos].getUserID());
                }
            });

            return item;
        }

    }
}


