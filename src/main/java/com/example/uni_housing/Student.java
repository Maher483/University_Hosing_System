package com.example.uni_housing;

import DataBase_Controller.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Student extends Person{
Rooms r2=new Rooms("test","test","test","test","test");
    String id;
    String faculty;
    String program;
    String roomNum="Waiting";

    public Student(String Name, String NationalID, String Email, String Gender, String Mobile, String id, String faculty, String program, LocalDate selectedDatestart,LocalDate selectedDateend) {
        this.id = id;
        this.faculty = faculty;
        this.program = program;
        this.MobileNum=Mobile;
        this.selectedDatestart =selectedDatestart;
        this.selectedDateend=selectedDateend;
        this.Gender=Gender;
        this.NationalID=NationalID;
        this.Email=Email;
        this.Name=Name;
    }

    public Student(String Name, String NationalID, String Email, String Gender, String Mobile, String id, String faculty, String program,String roomNum, LocalDate selectedDatestart,LocalDate selectedDateend) {
        this.id = id;
        this.faculty = faculty;
        this.program = program;
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
                String sql = "INSERT INTO student(id,name,nationalId,gender,mobileNum,email,faculty,program,roomNum,start,end) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
                Connection conn = connection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, Name);
                pstmt.setString(3, NationalID);
                pstmt.setString(4, Gender);
                pstmt.setString(5, MobileNum);
                pstmt.setString(6, Email);
                pstmt.setString(7, faculty);
                pstmt.setString(8, program);
                pstmt.setString(9, roomNum);
                pstmt.setString(10, selectedDatestart.toString());
                pstmt.setString(11, selectedDateend.toString());
                pstmt.executeUpdate();
                AddHistory();
                conn.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Update(String idSearch) throws SQLException {
        try {
            String sql = "UPDATE student SET id = '" + id + "',name = '" + Name + "', nationalID = '" + NationalID + "',gender = '" + Gender + "',mobileNum = '" + MobileNum + "',email = '" + Email + "',faculty = '" + faculty + "',program = '" + program + "',roomNum = '" + roomNum + "',start = '" + selectedDatestart + "',end = '" + selectedDateend + "'  WHERE id = '"+idSearch+"';";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        UpdateHistory(idSearch);

    }

    @Override
    public void GenerateRoom() throws SQLException,NullPointerException {
        PreparedStatement PS = null;
        ResultSet RS = null;
        String buildingName=null;
        if(Gender=="Male"){
             buildingName="Student male";
        }else if(Gender=="Female"){
             buildingName="Student female";
        }
        try {
            String sql="SELECT * FROM room WHERE buildingName = '"+buildingName+"' AND availability = 'Available' ORDER BY random() LIMIT 1;";
            PS = connection.connect().prepareStatement(sql);
            RS=PS.executeQuery();

            while (RS.next()){
                roomNum = RS.getString("roomName");
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
//            GenerateRoom();
            String sql = "INSERT INTO history(id,name,nationalId,gender,mobileNum,email,faculty,program,roomNum,start,end) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, Name);
            pstmt.setString(3, NationalID);
            pstmt.setString(4, Gender);
            pstmt.setString(5, MobileNum);
            pstmt.setString(6, Email);
            pstmt.setString(7, faculty);
            pstmt.setString(8, program);
            pstmt.setString(9, roomNum);
            pstmt.setString(10, selectedDatestart.toString());
            pstmt.setString(11, selectedDateend.toString());

            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void UpdateHistory(String idSearch) throws SQLException {
        try {
            String sql = "UPDATE history SET id = '" + id + "',name = '" + Name + "', nationalID = '" + NationalID + "',gender = '" + Gender + "',mobileNum = '" + MobileNum + "',email = '" + Email + "',faculty = '" + faculty + "',program = '" + program + "',roomNum = '" + roomNum + "',start = '" + selectedDatestart + "',end = '" + selectedDateend + "'  WHERE id = '"+idSearch+"';";
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
        String sql = "SELECT * FROM student  WHERE  id = ?";
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
