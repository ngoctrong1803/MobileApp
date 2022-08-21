package com.example.mobileshop.model;

public class Category {
    public int CategoryID;
    public String CategoryName;
    public String Image;

    public Category() {
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Category(int categoryID, String categoryName, String image) {
        CategoryID = categoryID;
        CategoryName = categoryName;
        Image = image;
    }
}
