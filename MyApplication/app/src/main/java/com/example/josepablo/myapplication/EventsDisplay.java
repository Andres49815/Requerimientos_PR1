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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Event;
import com.example.josepablo.myapplication.model.Titular;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventsDisplay extends AppCompatActivity {

    Event[] eventsArray;
    String eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_display);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        eventType = (String) bundle.get("eventType");

        fillEventsArray();
        ListView listEvents = (ListView) findViewById(R.id.listEvents);

        AdapterForEvents adapterForEvents = new AdapterForEvents(this, eventsArray);
        listEvents.setAdapter(adapterForEvents);
        View header = getLayoutInflater().inflate(R.layout.header_list,null);
        listEvents.addHeaderView(header);

    }



    private void fillEventsArray(){
        try{
            eventsArray = new Event[calculateEventsAmount()];
            int pos = 0;
            PreparedStatement pst;

            if(eventType.compareTo("A") == 0){
                pst = conectionDB().prepareStatement( "select * from BandEvent" );
            }
            else{
                pst = conectionDB().prepareStatement( "select * from BandEvent where band = ?" );
                pst.setString(1,eventType);
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                eventsArray[pos] = new Event(rs.getString("band"),
                        rs.getInt("e_id"),
                        rs.getString("place"),
                        rs.getDate("e_date"),
                        rs.getString("details"));
                pos++;
            }
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private int calculateEventsAmount(){
        int postCount = 0;
        try{
            PreparedStatement pst;
            if(eventType.compareTo("A") == 0){
                pst = conectionDB().prepareStatement( "select count(*) cont from BandEvent" );
            }
            else{
                pst = conectionDB().prepareStatement( "select count(*) cont from BandEvent where band = ?" );
                pst.setString(1,eventType);
            }
            ResultSet rs = pst.executeQuery();
            rs.next();
            postCount = rs.getInt("cont");
        }
        catch(SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return postCount;
    }


    private class AdapterForEvents extends ArrayAdapter<Event> {

        public AdapterForEvents(@NonNull Context context, Event[] datos) {
            super(context, R.layout.activity_events_item, datos);
        }

        public View getView(final int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_events_item,null);

            TextView bandName = (TextView) item.findViewById(R.id.bandName);
            bandName.setText( eventsArray[pos].band);

            TextView placeEvent = (TextView) item.findViewById(R.id.placeEvent);
            placeEvent.setText( eventsArray[pos].place);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            TextView dateEvent = (TextView) item.findViewById(R.id.dateEvent);
            dateEvent.setText(df.format(eventsArray[pos].date));

            TextView detailsEvent = (TextView) item.findViewById(R.id.detailsEvent);
            detailsEvent.setText(eventsArray[pos].details);

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
