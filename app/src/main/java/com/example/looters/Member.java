package com.example.looters;

public class Member {

    public Member()
    {

    }
    private String name;
    private String idno;
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public Member(String name, String idno) {
        this.name = name;
        this.idno = idno;
    }

    public String getName() {
        return name;
    }

    public String getIdno() {
        return idno;
    }
}
