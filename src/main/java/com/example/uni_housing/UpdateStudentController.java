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
import java.util.ArrayList;

public class UpdateStudentController {
    public TextField US_faculty;
    String id,name,nationalid,gender,mobileNum,email,faculty,program,roomNum,start,end;

    public Label US_errortext;
    @FXML
    private TextField US_email;

    @FXML
    private DatePicker US_end;

    @FXML
    private TextField US_gender;

    @FXML
    private TextField US_id;

    @FXML
    private TextField US_idsearch;

    @FXML
    private TextField US_mobile;

    @FXML
    private TextField US_name;

    @FXML
    private TextField US_nationalid;

    @FXML
    private TextField US_program;

    @FXML
    private JFXButton US_removeB;

    @FXML
    private TextField US_roomNum;

    @FXML
    private JFXButton US_searchB;

    @FXML
    private DatePicker US_start;

    @FXML
    private JFXButton US_updateB;

    @FXML
    private AnchorPane Update_student_ap;

    @FXML
    void Remove(ActionEvent event) {
        String sql = "DELETE FROM student WHERE id = '"+US_id.getText()+"';";
        try {

            Connection conn =  connection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            US_errortext.setTextFill(Color.GREEN);
            US_errortext.setText("The student has been removed");
            N_ull();
            set_text();


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Search(ActionEvent event) throws SQLException,NullPointerException {

        PreparedStatement PS = null;
        ResultSet RS = null;
        String sql="select * from student where id = ?";
        try {
            PS = connection.connect().prepareStatement(sql);
            PS.setString(1,US_idsearch.getText());

            RS=PS.executeQuery();

            if(RS.next()){
                US_errortext.setTextFill(Color.GREEN);
                US_errortext.setText("The student has been found");


                    id = RS.getString("id");
                    name = RS.getString("name");
                    nationalid = RS.getString("nationalId");
                    gender= RS.getString("gender");
                    mobileNum = RS.getString("mobileNum");
                    email = RS.getString("email");
                    faculty = RS.getString("faculty");
                    program = RS.getString("program");
                    roomNum = RS.getString("roomNum");
                    start = RS.getString("start");
                    end = RS.getString("end");

            }else{
                US_errortext.setTextFill(Color.RED);
                US_errortext.setText("This ID dose not exist !");
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
    void Update(ActionEvent event) throws SQLException {
        LocalDate selectedDatestart = US_start.getValue();
        LocalDate selectedDateend = US_end.getValue();

            US_errortext.setTextFill(Color.RED);

            if(US_name.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Name !");
            }else if(US_nationalid.getText().isEmpty()){
                US_errortext.setText("Invalid Student's National ID !");

            }else if(US_nationalid.getText().length()!=14)
            {
                US_errortext.setText("Invalid Student's National ID ! 'Must be 14 digit'");

            }else if (US_id.getText().isEmpty()){
                US_errortext.setText("Invalid Student's ID");
            }else if (US_program.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Program");

            }else if (US_email.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Email");

            }else if(US_faculty.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Faculty");

            }else if(US_gender.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Gender");

            }else if(US_mobile.getText().isEmpty()){
                US_errortext.setText("Invalid Student's Mobile Number");
            }else if(US_roomNum.getText().isEmpty()){
                US_errortext.setText("Invalid Student's  Room Number");
            }else if(selectedDatestart==null){
                US_errortext.setText("Select a Student's Start date");
            }else if(selectedDateend==null) {
                US_errortext.setText("Select a Student's end date");
            }else {
                Student s1=new Student(US_name.getText(),US_nationalid.getText(),US_email.getText(),US_gender.getText(),US_mobile.getText(),US_id.getText(),US_faculty.getText(),US_program.getText(),US_roomNum.getText(),selectedDatestart,selectedDateend);
                s1.Update(US_idsearch.getText());
                US_errortext.setTextFill(Color.GREEN);
                US_errortext.setText("Updated");

            }





    }
    public void N_ull(){

        id = null;
        name =  null;
        nationalid =  null;;
        gender=  null;
        mobileNum = null;
        email =  null;
        faculty =  null;
        program =  null;
        roomNum =  null;
        start =  null;
        end =  null;
    }

    public void set_text(){
        US_name.setText(name);
        US_id.setText(id);
        US_nationalid.setText(nationalid);
        US_program.setText(program);
        US_faculty.setText(faculty);
        US_email.setText(email);
        US_gender.setText(gender);
        US_mobile.setText(mobileNum);
        US_start.setValue(start==null? null:LocalDate.parse(start));
        US_end.setValue(end==null? null:LocalDate.parse(end));
        US_roomNum.setText(roomNum);
    }

}
