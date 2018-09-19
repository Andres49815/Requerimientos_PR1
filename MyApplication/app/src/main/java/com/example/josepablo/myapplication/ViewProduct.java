package com.example.josepablo.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewProduct extends AppCompatActivity{

    private int productID;
    private String bandID;
    private String productName;
    private int value;
    private String description;
    private int productTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shop_product);

        Button backToMenu = (Button) findViewById(R.id.backToMenuViewProduct);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.productID = (int) bundle.get("productID");
        this.productTypeID = (int) bundle.get("productTypeID");
        getDataProduct();
        setDataProduct();

        final Button buttonBuyProduct = (Button) findViewById(R.id.buyProduct);

        buttonBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Implement with image
                buyProduct(productID);
            }
        });
    }

    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(ViewProduct.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(ViewProduct.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(ViewProduct.this, clientMenu.class);
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
        TextView textViewProductName = (TextView) findViewById(R.id.textViewProductName);
        textViewProductName.setText(this.productName);

        TextView textViewProductValue = (TextView) findViewById(R.id.textViewValue);
        textViewProductValue.setText(this.value);

        TextView textViewProductDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewProductDescription.setText(this.description);
    }

    private void buyProduct(int productID){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("delete from product where productID = ?");
            pst.setInt(1,productID);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"Product Bought", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(ViewProduct.this,ClientShop.class);
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
