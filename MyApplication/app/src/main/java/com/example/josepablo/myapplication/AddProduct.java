package com.example.josepablo.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.example.josepablo.myapplication.model.Cookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProduct extends AppCompatActivity {

    private int productsAmount;
    private String productName;
    private int productValue;
    private String productDescription;
    private String productType;
    private int productTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerProductTypes);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.productTypes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button backToMenuAddProduct = (Button) findViewById(R.id.backToMenuAddProduct);
        backToMenuAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });


        final Button buttonAddProduct = (Button) findViewById(R.id.addProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Implement with image
                productName = ((TextView) findViewById(R.id.etProductName)).getText().toString();
                productValue = Integer.parseInt(((TextView) findViewById(R.id.etProductValue)).getText().toString());
                productDescription = ((TextView) findViewById(R.id.etProductDescription)).getText().toString();
                getProductTypeID(productType);
                calculateProductAmount();
                addProduct(productName, productValue, productDescription, productTypeID);
            }
        });
    }

    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(AddProduct.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(AddProduct.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(AddProduct.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }

    private void getProductTypeID(String name){
        try{
            PreparedStatement pst =conectionDB().prepareStatement
                    ("select productTypeID id from productType where type = ?");
            pst.setString(1, name);
            ResultSet resultCount = pst.executeQuery();
            resultCount.next();
            this.productTypeID = resultCount.getInt("id");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateProductAmount(){
        try{
            PreparedStatement pst =conectionDB().prepareStatement
                    ("select count(*) cont from product");
            ResultSet resultCount = pst.executeQuery();
            resultCount.next();
            productsAmount = resultCount.getInt("cont");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void addProduct(String productName, int value, String description, int prodyctTypeID){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("insert into product values(?,?,?,?,?,?)");
            pst.setInt(1, productsAmount);
            pst.setString(2, Cookie.current_user_ID);
            pst.setString(3, productName);
            pst.setInt(4, value);
            pst.setString(5, description);
            pst.setInt(6, prodyctTypeID);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"Added", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(AddProduct.this, BandShop.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Data base connection*/
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
