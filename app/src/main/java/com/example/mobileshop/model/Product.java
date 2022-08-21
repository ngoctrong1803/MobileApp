package com.example.mobileshop.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int ProductID;
    public String ProductName;
    public double Price;
    public String Image;
    public String Descripton;
    public String Content;
    public int Quanliti;
    public int CategoryID;

    public Product(int productID, String productName, double price, String image, String descripton, String content, int quanliti, int categoryID) {
        ProductID = productID;
        ProductName = productName;
        Price = price;
        Image = image;
        Descripton = descripton;
        Content = content;
        Quanliti = quanliti;
        CategoryID = categoryID;
    }

    public Product() {
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

    public String getDescripton() {
        return Descripton;
    }

    public void setDescripton(String descripton) {
        Descripton = descripton;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getQuanliti() {
        return Quanliti;
    }

    public void setQuanliti(int quanliti) {
        Quanliti = quanliti;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }
}
