package com.example.mobileshop.model;

public class ProductInOrder {
    public int ProductID;
    public String ProductName;
    public int Quanliti;
    public double Price;
    public String Image;

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

    public int getQuanliti() {
        return Quanliti;
    }

    public void setQuanliti(int quanliti) {
        Quanliti = quanliti;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public ProductInOrder(int productID, String productName, int quanliti, double price, String image) {
        ProductID = productID;
        ProductName = productName;
        Quanliti = quanliti;
        Price = price;
        Image = image;
    }
}
