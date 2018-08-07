package com.example.josepablo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    public Titular[] datos = new Titular[]{
            new Titular("Josepa lo hace de nuevo","El es un crack",R.drawable.ic_launcher_background),
            new Titular("Que pasa","CHAVAL",R.drawable.newspaper),
            new Titular("No se que pasa","#Zad",R.drawable.ic_launcher_foreground)};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.Lista);

        Adaptador adaptador = new Adaptador(this, datos);

        listView.setAdapter(adaptador);

        View header = getLayoutInflater().inflate(R.layout.header_list,null);

        listView.addHeaderView(header);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Cargando...",Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(MainActivity.this,noticiaExpandida.class);
                startActivity(intento);
                overridePendingTransition(R.anim.animacion,R.anim.animacioncontraria);

            }
        });


    }

    class Adaptador extends ArrayAdapter<Titular>{

        public Adaptador(@NonNull Context context, Titular[] datos) {
            super(context, R.layout.item_noticias, datos);
        }

        public View getView(int pos, View converView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.item_noticias,null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.lblTitulo);
            lblTitulo.setText( datos[pos].getTitulo());

            TextView lblSubTitulo = (TextView) item.findViewById(R.id.subTitulo);
            lblSubTitulo.setText( datos[pos].getSubTitulo());

            ImageView imagen = (ImageView) item.findViewById(R.id.imagen);
            imagen.setImageResource(datos[pos].getImagen());

            return item;



        }
    }
}
