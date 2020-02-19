package com.example.looters;

public class AdminUsername {
    String name;
    String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminUsername(String name, String otp) {
        this.name = name;
        this.otp = otp;
    }
}
