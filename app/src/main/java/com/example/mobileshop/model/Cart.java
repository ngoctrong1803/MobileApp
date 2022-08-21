package com.example.mobileshop.model;

public class Cart {
    public int ProductID;
    public String ProductName;
    public double ProductPrice;
    public String Image;
    public int Quanliti;
    public int Stock;

    public Cart(int productID, String productName, double productPrice, String image, int quanliti, int stock) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        Image = image;
        Quanliti = quanliti;
        Stock = stock;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getQuanliti() {
        return Quanliti;
    }

    public void setQuanliti(int quanliti) {
        Quanliti = quanliti;
    }

    public Cart(int productID, String productName, double productPrice, String image, int quanliti) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        Image = image;
        Quanliti = quanliti;
    }
}
