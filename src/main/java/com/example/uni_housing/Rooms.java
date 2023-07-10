package com.example.uni_housing;

import DataBase_Controller.connection;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rooms {
    private String roomName;
    private  String  buildingName;
    private String availability;
    private String bed1;
    private String bed2;

    public Rooms(String roomName, String buildingName, String AVE, String bed1, String bed2) {
        this.roomName = roomName;
        this.buildingName = buildingName;
        this.availability = AVE;
        this.bed1 = bed1;
        this.bed2 = bed2;
    }

    public  String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public  String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String isBed1() {
        return bed1;
    }

    public void setBed1(String bed1) {
        this.bed1 = bed1;
    }

    public String isBed2() {
        return bed2;
    }

    public void setBed2(String bed2) {
        this.bed2 = bed2;
    }

    public void bed1_UNAVA(String roomNameS){
        try {
            String sql = "UPDATE room SET bed1 = 'Unavailable' WHERE roomName = '"+roomNameS+"' ;";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void bed2_UNAVA(String roomNameS){
        try {
            String sql = "UPDATE room SET bed2 = 'Unavailable' WHERE roomName = '"+roomNameS+"' ;";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void cheak_AVA(String roomNameS){
        String bed1 = "Not found";
        String bed2 = "Not found";
        PreparedStatement PS = null;
        ResultSet RS = null;
        String sql = "SELECT * from room where roomName = '"+roomNameS+"'";
        try {
            PS = connection.connect().prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {
               bed1= RS.getString("bed1");
               bed2= RS.getString("bed2");
            }

            PS.close();
            RS.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        if(bed1.equals("Available") && bed2.equals("Available")){
            bed1_UNAVA(roomNameS);
        }else if(bed1.equals("Unavailable") && bed2.equals("Available")){
            bed2_UNAVA(roomNameS);
            Availability(roomNameS);
        }else {
            System.out.println("Error: Invalid number of rows returned for room " + roomNameS);
        }

    }

    public void Availability(String roomNameS){
        try {
            String sql = "UPDATE room SET availability = 'Unavailable' WHERE roomName = '"+roomNameS+"' ;";
            Connection conn = connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

}
//try {
//        Connection conn =  connection.connect();
//        stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery( "SELECT * FROM books WHERE code = "+num+";" );
//        while ( rs.next() ) {
//        title = rs.getString("title");
//        author = rs.getString("author");
//        code  = rs.getString("code");
//        imageSrc = rs.getString("imagesrc");
//        type = rs.getString("type");
//        availability = rs.getString("availability");
//        imageRootSrc = rs.getString("rootImageSrc");
//        }
//        rs.close();
//        stmt.close();
//        c
//        ( SQLException e ) {
//        System.err.println( e.getMessage() );
//        }