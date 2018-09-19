package com.example.josepablo.myapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("productID")
    private int productID;

    @JsonProperty("bandID")
    private String bandID;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("value")
    private int value;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private int productTypeID;

    public Product(int productID, String bandID, String productName, int value, String description, int productTypeID){
        this.productID = productID;
        this.bandID = bandID;
        this.productName = productName;
        this.value = value;
        this.description = description;
        this.productTypeID = productTypeID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getBandID() {
        return bandID;
    }

    public void setBandID(String bandID) {
        this.bandID = bandID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int type) {
        this.productTypeID = type;
    }
}
