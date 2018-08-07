package com.example.josepablo.myapplication;

import android.media.Image;

public class Titular {

    private String titulo;
    private String subTitulo;
    private int imagen;



    public Titular(String p_titulo, String p_subTitulo, int p_imagen){

       titulo = p_titulo;
       subTitulo = p_subTitulo;
       imagen = p_imagen;



    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }


}
