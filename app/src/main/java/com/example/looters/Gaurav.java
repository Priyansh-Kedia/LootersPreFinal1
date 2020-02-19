package com.example.looters;

public class Gaurav {

    public String name;
    public String price;
    public String q;

    public Gaurav(String name, String price, String q, String otp) {
        this.name = name;
        this.price = price;
        this.q = q;
        this.otp = otp;
    }

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

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String otp;

}
