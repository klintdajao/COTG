package com.example.gittest;

public class VendorInfo {
    private String id, name, email, p;
    public VendorInfo(){}
    public VendorInfo(String id, String name, String email,String p){
        this.id=id;
        this.name = name;
        this.email=email;
        this.p=p;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getP() {
        return p;
    }
    public void setP(String p) {
        this.p = p;
    }
}
