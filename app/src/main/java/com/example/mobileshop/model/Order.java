package com.example.mobileshop.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    public int OrderID;
    public String ReceiverName;
    public String Phone;
    public String Address;
    public int UserID;
    public Date ReceivedDate;
    public Date BookingDate;
    public int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Order(int orderID, String receiverName, String phone, String address, int userID, Date receivedDate, Date bookingDate, int status) {
        OrderID = orderID;
        ReceiverName = receiverName;
        Phone = phone;
        Address = address;
        UserID = userID;
        ReceivedDate = receivedDate;
        BookingDate = bookingDate;
        Status = status;
    }

    public Date getReceivedDate() {
        return ReceivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        ReceivedDate = receivedDate;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }



    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public Order(int orderID, String receiverName, String phone, String address, int userID) {
        OrderID = orderID;
        ReceiverName = receiverName;
        Phone = phone;
        Address = address;
        UserID = userID;
    }
}
