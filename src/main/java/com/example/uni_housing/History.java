package com.example.uni_housing;

import java.time.LocalDate;

public class History  {
      String MobileNum;
      String selectedDatestart;
      String selectedDateend;
      String Gender;
      String NationalID;
      String Email;
      String Name;
    String id;
    String faculty;

    public String getMobileNum() {
        return MobileNum;
    }

    public void setMobileNum(String mobileNum) {
        MobileNum = mobileNum;
    }

    public String getSelectedDatestart() {
        return selectedDatestart;
    }

    public void setSelectedDatestart(String selectedDatestart) {
        this.selectedDatestart = selectedDatestart;
    }

    public String getSelectedDateend() {
        return selectedDateend;
    }

    public void setSelectedDateend(String selectedDateend) {
        this.selectedDateend = selectedDateend;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    String program;
    String roomNum;

    public History(String Name, String NationalID, String Email, String Gender, String Mobile, String id, String faculty, String program, String roomNum, String selectedDatestart, String selectedDateend) {
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

}
