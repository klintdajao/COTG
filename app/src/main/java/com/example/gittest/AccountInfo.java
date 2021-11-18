package com.example.gittest;

public class AccountInfo {
    private String id,  email, fn, mn, ln, p;
    public AccountInfo(){}
    public AccountInfo(String id, String email, String fn, String mn, String ln, String p){
        this.id=id;
        this.email=email;
        this.fn=fn;
        this.mn=mn;
        this.ln=ln;
        this.p=p;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFn() { return fn; }
    public void setFn(String fn) { this.fn = fn; }
    public String getMn() { return mn; }
    public void setMn(String mn) { this.mn = mn; }
    public String getLn() { return ln; }
    public void setLn(String ln) { this.ln = ln; }
    public String getP() { return p; }
    public void setP(String p) { this.p = p; }




}
