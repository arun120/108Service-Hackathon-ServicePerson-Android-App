package com.b2b.home.a108serviceperson;

import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 13-11-2016.
 */
public class Database {

    public static String verifyDriver(String user,String pass){
        String id=null;


        Dbdetails db=new Dbdetails();

        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
         ResultSet rs=stmt.executeQuery("select driver_id from driver_login where driver_id like '"+user+"' and login like '"+pass+"'");
            if(rs.next())
               id=rs.getString("driver_id");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }


    public static int registerPublic(Customer_Details c){
        Dbdetails db=new Dbdetails();
        int cust_id=0;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into customer_details values("+null+",'"+c.getName()+"','"+c.getMobile()+
                    "','"+c.getAddress()+"')");
            ResultSet rs=stmt.executeQuery("select cust_id from customer_details where mobile like '"+c.getMobile()+"'");
            if(rs.next())
                cust_id=rs.getInt("cust_id");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            if(stmt!=null)
                stmt.close();
            if(conn!=null)

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return cust_id;
    }


    public static  int updateDescription(Customer_Case c){

        Dbdetails db=new Dbdetails();
        int update=0;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            update=stmt.executeUpdate("update customer_case set description ='"+c.getDescription()+"' where case_id like '"+c.getCase_id()+"'");


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return update;
    }

    public static String getSMSurl(Customer_Case cc){
        String surl=Dbdetails.getServerpath()+"addCase?custid="+cc.getCust_id()+
                "&type="+cc.getType()+"&lat="+cc.getLatitude()+"&lon="+cc.getLongitude()+
                "&people="+cc.getNo_ppl_affected();

        surl=surl.replace("+","%2B");
    return surl;
    }

    public static  String addCase(Customer_Case cc){

        HttpURLConnection connection = null;
        String s = "";

        String surl=getSMSurl(cc);
        URL url = null;
        try {
            url = new URL(surl);

        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        char c;
        while ((c = (char) input.read()) != (char) -1)
            s += c;

           // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
    public static  int updateImage(String caseid){

        Dbdetails db=new Dbdetails();
        int update=0;
        Connection conn=null;
        PreparedStatement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.prepareStatement("update customer_case set image =? where case_id like '"+caseid+"'");
            File file=new File(String.valueOf(Environment.getExternalStorageDirectory()+"/image.png"));
            FileInputStream is=new FileInputStream(file);
            stmt.setBinaryStream(1,is,(int)file.length());
            stmt.executeUpdate();
            is.close();



        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return update;
    }

    public static String  findDriver(Customer_Case c){
        Dbdetails db=new Dbdetails();
        String driver_id=null;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();

            ResultSet rs=stmt.executeQuery("select driver_id  from case_link where case_id like '"+c.getCase_id()+"'");
            if(rs.next())
                driver_id=rs.getString("driver_id");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return driver_id;
    }



    public static Driver_Location  driverLocation(String id){
        Dbdetails db=new Dbdetails();
        String driver_id=null;
        Connection conn=null;
        Statement stmt=null;
        Driver_Location loc=new Driver_Location();
        loc.setDriver_id(id);
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();

            ResultSet rs=stmt.executeQuery("select * from driver_location where driver_id like '"+id+"'");
            if(rs.next()) {
                 loc.setLongitude(rs.getString("longitude"));
                loc.setLatitude(rs.getString("latitude"));

            }
            else{
                loc=null;
            }
            } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return loc;
    }


    public static String  updatedriverLocation(Driver_Location d){
        HttpURLConnection connection = null;
        String sUrl=Dbdetails.getServerpath()+"updateLoc?driverid="+d.getDriver_id()+
                "&lat="+d.getLatitude()+"&lon="+d.getLongitude();
        Log.i("Url",sUrl);
        String s="";
        URL url = null;
        try {
            url = new URL(sUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            char c;
            while ((c = (char) input.read()) != (char) -1)
                s += c;

            // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    public static List<String> getNotification(String id){
        Dbdetails db=new Dbdetails();
        List<String> case_id=new ArrayList<>();
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            Log.i("query","select * from notification where driverid like '"+id+"'");
            ResultSet rs=stmt.executeQuery("select * from notification where driverid like '"+id+"'");
            while(rs.next())
                case_id.add(rs.getString("caseid"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return case_id;
    }


    public static Customer_Case getcaseDetails(String id){
        Dbdetails db=new Dbdetails();
        Customer_Case ccase=new Customer_Case();
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            Log.i("query","select * from notification where driverid like '"+id+"'");
            ResultSet rs=stmt.executeQuery("select * from customer_case where case_id like '"+id+"'");
            if(rs.next()){
                ccase.setLatitude(rs.getString("latitude"));
                ccase.setLongitude(rs.getString("longitude"));
                ccase.setNo_ppl_affected(rs.getString("no_ppl_affected"));
                ccase.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ccase;
    }



    public static  String addRequest(Case_Link cc){

        HttpURLConnection connection = null;
        String sUrl=Dbdetails.getServerpath()+"acceptDriver?caseid="+cc.getCase_id()+
                "&driverid="+cc.getDriver_id();
        Log.i("Url",sUrl);
        String s="";
        URL url = null;
        try {
            url = new URL(sUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            char c;
            while ((c = (char) input.read()) != (char) -1)
                s += c;

            // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    public static  String finalizeRequest(Case_Link cc){

        HttpURLConnection connection = null;
        String sUrl=Dbdetails.getServerpath()+"finalizeCase?caseid="+cc.getCase_id()+
                "&driverid="+cc.getDriver_id();

        String s="";
        Log.i("Url",sUrl);
        URL url = null;
        try {
            url = new URL(sUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            char c;
            while ((c = (char) input.read()) != (char) -1)
                s += c;

            // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}
