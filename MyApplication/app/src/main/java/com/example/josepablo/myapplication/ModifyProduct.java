package com.example.josepablo.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyProduct extends AppCompatActivity {

    private int productID;
    private String bandID;
    private String productName;
    private int value;
    private String description;
    private int productTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerProductTypes);
        spinner.setVisibility(View.GONE);

        Button backToMenu = (Button) findViewById(R.id.backToMenuAddProduct);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.productID = (int) bundle.get("productID");
        getDataProduct();
        setDataProduct();

        final Button buttonModifyProduct = (Button) findViewById(R.id.addProduct);
        buttonModifyProduct.setText("Modify Product");

        buttonModifyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Implement with image
                productName = ((TextView) findViewById(R.id.etProductName)).getText().toString();
                value = Integer.parseInt(((TextView) findViewById(R.id.etProductValue)).getText().toString());
                description = ((TextView) findViewById(R.id.etProductDescription)).getText().toString();
                modifyProduct(productName, value, description);
            }
        });
    }


    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(ModifyProduct.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(ModifyProduct.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(ModifyProduct.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }


    private void getDataProduct(){
        try{
            PreparedStatement pst=conectionDB().prepareStatement
                    ("select * from product where productID = ?");
            pst.setInt(1,this.productID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            this.bandID = rs.getString("bandID");
            this.productName = rs.getString("name");
            this.value = rs.getInt("value");
            this.description = rs.getString("description");
            this.productTypeID = rs.getInt("productTypeID");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDataProduct(){
        EditText editTextProductName = (EditText) findViewById(R.id.etProductName);
        editTextProductName.setText(this.productName);

        EditText editTextProductValue = (EditText) findViewById(R.id.etProductValue);
        editTextProductValue.setText(this.value);

        EditText editTextProductDescription = (EditText) findViewById(R.id.etProductDescription);
        editTextProductDescription.setText(this.description);
    }

    private void modifyProduct(String productName, int value, String description){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("update product set productID = ? bandID = ? name = ? value = ? description = ? productTypeID = ?");
            pst.setInt(1, this.productID);
            pst.setString(2, this.bandID);
            pst.setString(3, productName);
            pst.setInt(4, value);
            pst.setString(5, description);
            pst.setInt(6, this.productTypeID);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"Modified", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(ModifyProduct.this, BandShop.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Conection to DB
    public Connection conectionDB(){
        Connection connection = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://musicbeansjar.database.windows.net:1433/musicBeansJAR;user=administrador@musicbeansjar;password=Inicio123;");
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return connection;
    }
}
