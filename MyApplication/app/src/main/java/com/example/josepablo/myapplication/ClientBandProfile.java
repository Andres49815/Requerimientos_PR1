package com.example.josepablo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.AccountAdministrator;
import com.example.josepablo.myapplication.model.Cookie;
import com.example.josepablo.myapplication.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientBandProfile extends AppCompatActivity {

    private static String bandID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_band_profile);

        NumberPicker picker = (NumberPicker) findViewById(R.id.NP_Score);
        Button AddFavorite = (Button) findViewById(R.id.BT_Favorite);
        Button Posts = (Button) findViewById(R.id.BT_Posts);
        Button Events = (Button) findViewById(R.id.BT_Events);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bandID = (String) bundle.get("bandID");

        SelectBand();

        picker.setMaxValue(5);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(false);
        AddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Toast.makeText(getApplicationContext(), ":::" + bandID, Toast.LENGTH_LONG).show();
            }
        });

        Posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientBandProfile.this, postsDisplay.class);
                intent.putExtra("typePosts", bandID);
                startActivity(intent);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);

            }
        });

        Events.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intento = new Intent(ClientBandProfile.this, EventsDisplay.class);
                intento.putExtra("eventType", Cookie.current_user_ID);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
            }
        });
    }

    private void SelectBand() {
        try {
            Connection connection = AccountAdministrator.connectionDB();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT B.userName, B.userLastName, AVG(R.score) AS Score " +
                            "FROM account B LEFT OUTER JOIN dbo.rate R  ON (B.ID = R.bandID) " +
                            "WHERE ID = ? " +
                            "GROUP BY B.userName, B.userLastName"
            );
            AccountAdministrator.SetStatement(statement, bandID);
            ResultSet set = statement.executeQuery();
            set.next();

            ((TextView)findViewById(R.id.LB_BandName)).setText(set.getString("userName"));
            ((TextView)findViewById(R.id.LB_Details)).setText(set.getString("userLastName"));
            try {
                ((TextView)findViewById(R.id.LB_Score)).setText(set.getString("Score"));
            }
            catch (Exception e) {
                ((TextView)findViewById(R.id.LB_Score)).setText("0.0");
            }
            Toast.makeText(getApplicationContext(), ":::" + bandID, Toast.LENGTH_LONG).show();
        }
        catch (SQLException e) {}
    }
}
