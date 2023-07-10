package com.example.uni_housing;

import DataBase_Controller.connection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateDoctorController {
    public Label UD_errortext;
    public TextField UD_nationalIdSearch;
    String name,nationalid,gender,mobileNum,email,roomNum,start,end;
    @FXML
    private JFXButton UD_UpdateB;

    @FXML
    private TextField UD_email;

    @FXML
    private DatePicker UD_end;

    @FXML
    private TextField UD_gender;

    @FXML
    private TextField UD_mobile;

    @FXML
    private TextField UD_name;



    @FXML
    private TextField UD_nationalID;

    @FXML
    private JFXButton UD_remove;

    @FXML
    private TextField UD_roomNum;

    @FXML
    private JFXButton UD_searchB;

    @FXML
    private DatePicker UD_start;

    @FXML
    private AnchorPane Update_doc_ap;

    @FXML
    void Remove_D(ActionEvent event) {
        String sql = "DELETE FROM doctor WHERE nationalId = "+UD_nationalID.getText()+";";
        try {

            Connection conn =  connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            UD_errortext.setTextFill(Color.GREEN);
            UD_errortext.setText("The Doctor has been removed");
            N_ull();
            set_text();
            conn.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void Search_D(ActionEvent event) throws SQLException {
        LocalDate selectedDatestart ;
        LocalDate selectedDateend ;
        PreparedStatement PS = null;
        ResultSet RS = null;
        String command="select * from doctor where nationalId = ?";
        try {
            PS = connection.connect().prepareStatement(command);
            PS.setString(1,UD_nationalIdSearch.getText());

            RS=PS.executeQuery();

            if(RS.next()){
                UD_errortext.setTextFill(Color.GREEN);
                UD_errortext.setText("The National ID has been found");


                name = RS.getString("name");
                nationalid = RS.getString("nationalId");
                gender= RS.getString("gender");
                mobileNum = RS.getString("mobileNum");
                email = RS.getString("email");
                roomNum = RS.getString("roomNum");
                start = RS.getString("start");
                end = RS.getString("end");

            }else{
                UD_errortext.setTextFill(Color.RED);
                UD_errortext.setText("This National ID dose not exist !");
                N_ull();

            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());

        }finally {
            PS.close();
            RS.close();
        }
        set_text();

    }

    @FXML
    void Update_D(ActionEvent event) throws SQLException {
        LocalDate selectedDatestart = UD_start.getValue();
        LocalDate selectedDateend = UD_end.getValue();

            UD_errortext.setTextFill(Color.RED);

            if(UD_name.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's Name !");
            }else if(UD_nationalID.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's National ID !");

            }else if(UD_nationalID.getText().length()!=14)
            {
                UD_errortext.setText("Invalid Student's National ID ! 'Must be 14 digit'");
            }else if (UD_email.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's Email");
            }else if(UD_gender.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's Gender");

            }else if(UD_mobile.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's Mobile Number");
            }else if(UD_roomNum.getText().isEmpty()){
                UD_errortext.setText("Invalid Student's  Room Number");
            }else if(selectedDatestart==null){
                UD_errortext.setText("Select a Student's Start date");
            }else if(selectedDateend==null) {
                UD_errortext.setText("Select a Student's end date");
            }else {
                Doctor d1=new Doctor(UD_name.getText(),UD_nationalID.getText(),UD_email.getText(),UD_gender.getText(),UD_mobile.getText(),UD_roomNum.getText(),selectedDatestart,selectedDateend);
                d1.Update(UD_nationalIdSearch.getText());
                UD_errortext.setTextFill(Color.GREEN);
                UD_errortext.setText("Updated");

            }





    }

    public void N_ull(){

        name =  null;
        nationalid =  null;;
        gender=  null;
        mobileNum = null;
        email =  null;
        roomNum =  null;
        start =  null;
        end =  null;
    }

    public void set_text(){
        UD_name.setText(name);
        UD_nationalID.setText(nationalid);
        UD_email.setText(email);
        UD_gender.setText(gender);
        UD_mobile.setText(mobileNum);
        UD_start.setValue(start==null? null: LocalDate.parse(start));
        UD_end.setValue(end==null? null:LocalDate.parse(end));
        UD_roomNum.setText(roomNum);
    }

}
