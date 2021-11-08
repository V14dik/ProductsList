package com.example.secondlab;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String numberOfUnits;
    private String price;
    private String date;


    public Product(String name, String numberOfUnits, String price, String date){
        this.name = name;
        this.numberOfUnits = numberOfUnits;;
        this.price = price + "руб.";
        this.date = date;
    }
    public Product(String str){
        String[] data = str.split(", ");

        name = data[0];
        numberOfUnits = data[1];
        price = data[2] + "руб.";
        date = data[3];
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getNumberOfUnits(){
        return this.numberOfUnits;
    }
    public void setNumberOfUnits(String number){
        this.numberOfUnits = number;
    }

    public String getPrice(){
        return this.price;
    }
    public void setPrice(String price){
        this.price = price;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
