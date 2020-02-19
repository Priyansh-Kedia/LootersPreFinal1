package com.example.looters;

public class CartChangeData {

    private String name;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        this.onoff = onoff;
    }

    public CartChangeData(String name, String price, String onoff) {
        this.name = name;
        this.price = price;
        this.onoff = onoff;
    }

    private String onoff;

}
