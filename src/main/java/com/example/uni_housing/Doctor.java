package com.example.uni_housing;

import DataBase_Controller.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Doctor extends Person {
    Rooms r2=new Rooms("test","test","test","test","test");
    String roomNum;
    public Doctor(String Name, String NationalID, String Email, String Gender, String Mobile, LocalDate selectedDatestart, LocalDate selectedDateend) {

        this.MobileNum=Mobile;
        this.selectedDatestart =selectedDatestart;
        this.selectedDateend=selectedDateend;
        this.Gender=Gender;
        this.NationalID=NationalID;
        this.Email=Email;
        this.Name=Name;
    }

    public Doctor(String Name, String NationalID, String Email, String Gender, String Mobile,String roomNum, LocalDate selectedDatestart, LocalDate selectedDateend) {

        this.MobileNum=Mobile;
        this.selectedDatestart =selectedDatestart;
        this.selectedDateend=selectedDateend;
        this.Gender=Gender;
        this.NationalID=NationalID;
        this.Email=Email;
        this.Name=Name;
        this.roomNum=roomNum;
    }

    @Override
    public void add() throws SQLException {
        try {
            GenerateRoom();
            String sql = "INSERT INTO doctor(nationalId,name,email,gender,mobileNum,roomNum,start,end) VALUES (?,?,?,?,?,?,?,?);";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, NationalID);
            pstmt.setString(2, Name);
            pstmt.setString(3, Email);
            pstmt.setString(4, Gender);
            pstmt.setString(5, MobileNum);
            pstmt.setString(6, roomNum);
            pstmt.setString(7, selectedDatestart.toString());
            pstmt.setString(8, selectedDateend.toString());

            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        AddHistory();

    }

    @Override
    public void Update(String NationalIDsearch) throws SQLException {
        try {
            String sql = "UPDATE doctor SET nationalId = '"+NationalID+"',name = '"+Name+"',email = '"+Email+"',gender = '"+Gender+"',mobileNum = '"+MobileNum+"',roomNum = '"+roomNum+"',start = '"+selectedDatestart+"',end = '"+selectedDateend+"' WHERE nationalId = '"+NationalIDsearch+"';";

            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        UpdateHistory(NationalIDsearch);

    }

    @Override
    public void GenerateRoom() throws SQLException {
        PreparedStatement PS = null;
        ResultSet RS = null;
        String buildingName=null;
        if(Gender=="Male"){
            buildingName="Doctors male";
        }else if(Gender=="Female"){
            buildingName="Doctors female";
        }
        try {
            String sql="SELECT * FROM room WHERE buildingName = '"+buildingName+"' AND availability = 'Available' ORDER BY random() LIMIT 1;";
            PS = connection.connect().prepareStatement(sql);
            RS=PS.executeQuery();

            while (RS.next()){
                roomNum = RS.getString("roomName");
                System.out.println(roomNum);

            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());

        }finally {
            PS.close();
            RS.close();
            r2.cheak_AVA(roomNum);

        }
    }

    @Override
    public void AddHistory() throws SQLException {
        try {
            GenerateRoom();
            String sql = "INSERT INTO history(nationalId,name,email,gender,mobileNum,roomNum,start,end) VALUES (?,?,?,?,?,?,?,?);";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, NationalID);
            pstmt.setString(2, Name);
            pstmt.setString(3, Email);
            pstmt.setString(4, Gender);
            pstmt.setString(5, MobileNum);
            pstmt.setString(6, roomNum);
            pstmt.setString(7, selectedDatestart.toString());
            pstmt.setString(8, selectedDateend.toString());

            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void UpdateHistory(String nationalID) throws SQLException {
        try {
            String sql = "UPDATE history SET name = '" + Name + "', nationalID = '" + NationalID + "',gender = '" + Gender + "',mobileNum = '" + MobileNum + "',email = '" + Email + "',roomNum = '" + roomNum + "',start = '" + selectedDatestart + "',end = '" + selectedDateend + "'  WHERE nationalId = '"+nationalID+"';";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean checkValueExists(String value) {
        boolean exists = false;
        PreparedStatement PS = null;
        ResultSet RS = null;
        String sql = "SELECT * FROM doctor  WHERE  nationalID = ?";
        try {
            PS = connection.connect().prepareStatement(sql);
            PS.setString(1, value);
            RS = PS.executeQuery();

            if (RS.next() && RS.getInt(1) > 0) {
                exists = true;
            }

            PS.close();
            RS.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return exists;
    }
}

