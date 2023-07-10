package com.example.uni_housing;

import DataBase_Controller.connection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    private final String[] GenderOptions= {"Male","Female"};


    public TextField AS_faculty;
//    public TextField AS_roomNum;
    public Label AS_errortext;
    public Label AS_roomNumLable;
    public ComboBox AS_GenderCompo;
    @FXML
    private TextField AS_NAME;

    @FXML
    private JFXButton AS_addB;

    @FXML
    private TextField AS_email;

    @FXML
    private DatePicker AS_from;

    @FXML
    private TextField AS_gender;

    @FXML
    private TextField AS_id;

    @FXML
    private TextField AS_mobile;

    @FXML
    private TextField AS_nationalID;

    @FXML
    private TextField AS_program;

    @FXML
    private DatePicker AS_to;

    @FXML
    private AnchorPane Add_student_ap;



    @FXML
    void Add(ActionEvent event) throws SQLException {
            AS_errortext.setTextFill(Color.RED);

            LocalDate selectedDatestart = AS_from.getValue();
            LocalDate selectedDateend = AS_to.getValue();
            if(AS_NAME.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's Name !");
            }else if(AS_nationalID.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's National ID !");

            }else if(AS_nationalID.getText().length()!=14)
            {
                AS_errortext.setText("Invalid Student's National ID ! 'Must be 14 digit'");

            }else if (AS_id.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's ID");
            }else if (AS_program.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's Program");

            }else if (AS_email.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's Email");

            }else if(AS_faculty.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's Faculty");

            }else if(AS_GenderCompo.getValue()==null){
                AS_errortext.setText("Invalid Student's Gender");

            }else if(AS_mobile.getText().isEmpty()){
                AS_errortext.setText("Invalid Student's Mobile Number");
//            }else if(AS_roomNum.getText().isEmpty()){
//                AS_errortext.setText("Invalid Student's  Room Number");
            }else if(selectedDatestart==null){
                AS_errortext.setText("Select a Student's Start date");
            }else if(selectedDateend==null) {
                AS_errortext.setText("Select a Student's end date");
            }else {
                Student s1=new Student(AS_NAME.getText(),AS_nationalID.getText(),AS_email.getText(),AS_GenderCompo.getValue().toString(),AS_mobile.getText(),AS_id.getText(),AS_faculty.getText(),AS_program.getText(),selectedDatestart,selectedDateend);
                if (s1.checkValueExists(AS_id.getText())){
                    AS_errortext.setTextFill(Color.RED);
                    AS_errortext.setText("This ID exist before!!");
                }else {
                    s1.add();
                    AS_roomNumLable.setText(s1.roomNum);
                    AS_errortext.setTextFill(Color.GREEN);
                    AS_errortext.setText("Added successfully");
                }


            }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AS_GenderCompo.getItems().addAll(GenderOptions);
    }
}
