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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Event;
import com.example.josepablo.myapplication.model.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class bandsDiscover extends AppCompatActivity {
    UserAccount[] bandsForDiscover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bands_discover);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String typeLayout = (String) bundle.get("typeLayout");

        fillBandsDiscover(typeLayout);
        ListView listDiscover = (ListView) findViewById(R.id.listDiscover);

        AdapterForDiscover adapterForDiscover = new AdapterForDiscover(this,bandsForDiscover);
        listDiscover.setAdapter(adapterForDiscover);
        View header = getLayoutInflater().inflate(R.layout.header_list,null);
        listDiscover.addHeaderView(header);
        listDiscover.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),bandsForDiscover[position].getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(bandsDiscover.this, ClientBandProfile.class);
                intent.putExtra("bandID", bandsForDiscover[position - 1].getUserID());
                startActivity(intent);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
            }
        });

    }


    private String createsQuery(String typeLayout){//REvisar la consulta de obando
        if(typeLayout.compareTo("B") == 0) {
            return "select * from account where typeAccount = ?";//PARA ESTA
        }
        else {
            return  "select * from account a inner join favorites f on(a.ID = f.clientID) where a.ID = ?";
        }
    }


    private void fillBandsDiscover(String typeLayout){
        try{
            int pos = 0;
            bandsForDiscover = new UserAccount[ammountOfBands(typeLayout)];
            PreparedStatement pst = conectionDB().prepareStatement(createsQuery(typeLayout));
            pst.setString(1,typeLayout);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                bandsForDiscover[pos] = new UserAccount(rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("userLastName"),
                        R.drawable.band); //Imagenes
                pos++;
            }
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int ammountOfBands(String typeLayout) {
        int postCount = 0;
        try {
            PreparedStatement pst;
            if(typeLayout.compareTo("B") == 0){
                pst = conectionDB().prepareStatement( "select count(*) cont from account where typeAccount = 'B'" );
            }
            else{
                pst =  conectionDB().prepareStatement("select (*) cont from account a inner join favorites f on(a.ID = f.clientID) where a.ID = ?");
            }
            pst.setString(1,typeLayout);
            ResultSet rs = pst.executeQuery();
            rs.next();
            postCount = rs.getInt("cont");
        }
        catch(SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return postCount;
    }


    private class AdapterForDiscover extends ArrayAdapter<UserAccount> {

        public AdapterForDiscover(@NonNull Context context, UserAccount[] bandsForDiscover) {
            super(context, R.layout.activity_bands_discover_item, bandsForDiscover);
        }

        public View getView(final int pos, View converView, ViewGroup parent){

            try{
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View item = inflater.inflate(R.layout.activity_bands_discover_item,null);

                TextView bandName = (TextView) item.findViewById(R.id.bandNameDiscover);
                bandName.setText( bandsForDiscover[pos].getName());

                TextView placeEvent = (TextView) item.findViewById(R.id.descriptionBand);
                placeEvent.setText( bandsForDiscover[pos].getLastName());

                ImageView detailsEvent = (ImageView) item.findViewById(R.id.profilePicBand);
                detailsEvent.setImageResource( R.drawable.band );
                return item;
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                return null;
            }
        }
    }

    /*Data base connection*/
    public Connection conectionDB(){
        Connection conexion = null;
        try {
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
