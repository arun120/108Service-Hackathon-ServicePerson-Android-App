package com.b2b.home.a108serviceperson;

/**
 * Created by Home on 13-11-2016.
 */
public class Dbdetails {

    final private String Driver="com.mysql.jdbc.Driver";
    final static private String UserName="root";
    final static private String Pass="1234";
    final static private String serverpath="http://192.168.0.101:8084/108Service/";
    private String Url="jdbc:mysql://192.168.0.101:3306/108_service";

    public String getDriver() {
        return Driver;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPass() {
        return Pass;
    }

    public String getUrl() {
        return Url;
    }

    public static String getServerpath(){return serverpath;}
}
