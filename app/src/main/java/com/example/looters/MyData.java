package com.example.looters;

class MyData {

    private String name,price,quantity;

    private String section;


    private String enabled;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }



    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public MyData(String name, String price, String enabled, String section, String quantity) {
        this.name = name;
        this.price = price;
        this.enabled=enabled;
        this.section=section;
        this.quantity=quantity;
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


}
