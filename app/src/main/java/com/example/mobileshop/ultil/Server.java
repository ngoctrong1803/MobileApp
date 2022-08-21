package com.example.mobileshop.ultil;

public class Server {
    public static String localhost = "192.168.1.174";
    public static String CategoryUrl = "http://"+localhost+"/servermobile/getcategory.php";
    public static String NewProductsUrl= "http://"+localhost+"/servermobile/getNewBooks.php";
    public static String GetProductUrl = "http://"+localhost+"/servermobile/getproduct.php?page=";
    public static String UserInformationUrl = "http://"+localhost+"/servermobile/userinformation.php";
    public static String OrderDetailUrl = "http://"+localhost+"/servermobile/orderdetail.php";
    public static String LoginUrl = "http://"+localhost+"/servermobile/login.php";
    public static String RegisterUrl = "http://"+localhost+"/servermobile/register.php";
    public static String ListOrderUrl = "http://"+localhost+"/servermobile/getlistorder.php";
    public static String ListOrderDetailUrl = "http://"+localhost+"/servermobile/getorderdetail.php";

}
