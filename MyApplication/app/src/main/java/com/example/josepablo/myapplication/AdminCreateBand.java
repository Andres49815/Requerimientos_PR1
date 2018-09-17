package com.example.josepablo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.josepablo.myapplication.model.AccountAdministrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminCreateBand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_band);

        Button createBand = (Button) findViewById(R.id.BT_CreateBand);
        createBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBand();
            }
        });
    }

    public void CreateBand() {
        try {
            Connection connection = AccountAdministrator.connectionDB();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO dbo.account VALUES(?, ?, 'B', NULL, ?, ?)"
            );
            String ID = ((EditText)findViewById(R.id.TX_BandID)).getText().toString();
            String passW = ((EditText)findViewById(R.id.TX_Password)).getText().toString();
            String name = ((EditText)findViewById(R.id.TX_BandName)).getText().toString();
            String description = ((EditText)findViewById(R.id.TX_Description)).getText().toString();
            AccountAdministrator.SetStatement(statement, ID, passW, name, description);
            statement.executeQuery();
        }
        catch (SQLException sqlException) {}
    }
}
