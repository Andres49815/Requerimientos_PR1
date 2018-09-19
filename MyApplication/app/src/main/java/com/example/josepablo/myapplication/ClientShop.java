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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.myapplication.model.Cookie;
import com.example.josepablo.myapplication.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientShop extends AppCompatActivity {

    Product[] products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shop);

        Button backToMenu = (Button) findViewById(R.id.backToMenuClientShop);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specifyMenu();
            }
        });

        fillProducts();

        ListView listOfProducts = (ListView) findViewById(R.id.listProductsToBuy);


        AdaptaterForClientShop adaptador = new AdaptaterForClientShop(this, this.products);
        listOfProducts.setAdapter(adaptador);
        View header = getLayoutInflater().inflate(R.layout.header_list,null);
        listOfProducts.addHeaderView(header);

    }

    private void specifyMenu(){
        if(Cookie.userType.compareTo(Character.toString('A')) == 0) {
            Intent intento = new Intent(ClientShop.this,adminMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }

        else if(Cookie.userType.compareTo(Character.toString('B')) == 0) {
            Intent intento = new Intent(ClientShop.this,bandMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
        }
        else {
            Intent intento = new Intent(ClientShop.this, clientMenu.class);
            startActivity(intento);
            overridePendingTransition(R.anim.animacion, R.anim.animacioncontraria);
        }
    }



    private void fillProducts(){

        try{
            this.products = new Product[countProducts()];
            int pos = 0;

            PreparedStatement pst= conectionDB().prepareStatement("select * from product" );
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                this.products[pos] = new Product(rs.getInt("productID"),
                        rs.getString("bandID"),
                        rs.getString("name"),
                        rs.getInt("value"),
                        rs.getString("description"),
                        rs.getInt("productTypeID"));
                pos++;
            }
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private int countProducts(){
        int result = 0;
        try{
            PreparedStatement pst= conectionDB().prepareStatement(  "select count(*) cont from product" );
            ResultSet rs = pst.executeQuery();
            rs.next();
            result = rs.getInt("cont");
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return result;

    }

    /*Adapter for the list of posts*/
    private class AdaptaterForClientShop extends ArrayAdapter<Product> {

        public AdaptaterForClientShop(@NonNull Context context, Product[] datos) {
            super(context, R.layout.activity_client_shop_item, datos);
        }

        public View getView(final int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_client_shop_item,null);

            TextView textViewProductName = (TextView) item.findViewById(R.id.textViewProductName);
            textViewProductName.setText( products[pos].getProductName());

            Button buttonViewProduct = (Button) item.findViewById(R.id.buttonViewProduct);
            buttonViewProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent( ClientShop.this,ViewProduct.class);
                    intento.putExtra("productID",products[pos].getProductID());
                    intento.putExtra("productTypeID",products[pos].getProductTypeID());
                    startActivity(intento);
                    overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);
                }
            });

            Button buttonBuyProduct = (Button) item.findViewById(R.id.buttonBuyProduct);
            buttonBuyProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyProduct(products[pos].getProductID());
                }
            });
            return item;
        }
    }

    private void buyProduct(int productID){
        try{
            PreparedStatement pst=conectionDB().prepareStatement("delete from product where productID = ?");
            pst.setInt(1,productID);
            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"Product Bought", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(ClientShop.this,ClientShop.class);
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
