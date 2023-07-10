package com.example.uni_housing;

import java.sql.SQLException;
import java.time.LocalDate;

public abstract class Person {
    String Name;
    String NationalID;
    String Email;
    String Gender;
    String MobileNum;
    LocalDate selectedDatestart;
    LocalDate selectedDateend;


    public abstract void add() throws SQLException;
    public abstract void Update(String data)throws SQLException;

    public abstract void GenerateRoom()throws SQLException;
    public abstract void AddHistory()throws SQLException;
    public abstract void UpdateHistory(String idsearch)throws SQLException;

}
