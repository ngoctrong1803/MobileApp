package com.example.mobileshop.model;

public class User {
    public int UserID;
    public String UserName;
    public String Password;
    public String FullName;
    public String Address;
    public String Phone;
    public String Email;

    public User(int userID, String userName, String password, String fullName, String address, String phone, String email) {
        UserID = userID;
        UserName = userName;
        Password = password;
        FullName = fullName;
        Address = address;
        Phone = phone;
        Email = email;
    }

    public User() {
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
