package com.example.gittest;

public class ProductInfo {
    String prodName, prodDesc,prodImg,vendorID;
    int prodID,prodStock,categID;
    double prodPrice;

    public ProductInfo(){}

    public ProductInfo(String prodName, String prodDesc, String prodImg, String vendorID, int prodID, int prodStock, int categID, double prodPrice) {
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodImg = prodImg;
        this.vendorID = vendorID;
        this.prodID = prodID;
        this.prodStock = prodStock;
        this.categID = categID;
        this.prodPrice = prodPrice;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public String getVendorID() { return vendorID;}

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public int getCategID() {
        return categID;
    }

    public void setCategID(int categID) {
        this.categID = categID;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
}
