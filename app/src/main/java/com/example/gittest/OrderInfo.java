package com.example.gittest;

public class OrderInfo {

    private Integer id;
    private String userid;
    private String name;
    private Integer quantity;
    private Double amount;
    private String date;
    public OrderInfo(){}
    public OrderInfo(Integer id, String userid, String name, Double amount, Integer quantity, String date){
        this.id=id;
        this.userid=userid;
        this.name=name;
        this.amount=amount;
        this.quantity=quantity;
        this.date=date;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUserid() {
      return userid;
    }
    public void setUserid(String userid) {
      this.userid = userid;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getName() {
      return name;
    }
    public Double getAmount() {
      return amount;
    }
    public void setAmount(Double amount) {
      this.amount = amount;
    }
    public Integer getQuantity() {
      return quantity;
    }
    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
    public String getOrderDate() {
      return date;
    }
    public void setOrderDate(String date) {
      this.date = date;
    }
    
    

}
