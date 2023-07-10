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

public class AddDoctorController implements Initializable {
    private final String[] GenderOptions= {"Male","Female"};


    public Label AD_errortext;
//    public TextField AD_roomNum;
    public ComboBox AD_GenderCompo;
    public Label AD_roomNumLable;
    @FXML
    private JFXButton AD_addb;

    @FXML
    private TextField AD_email;

    @FXML
    private DatePicker AD_end;

//    @FXML
//    private TextField AD_gender;

    @FXML
    private TextField AD_mobile;

    @FXML
    private TextField AD_name;

    @FXML
    private TextField AD_nationalid;

    @FXML
    private DatePicker AD_start;



    @FXML
    void AD_ADD(ActionEvent event) throws SQLException {

                AD_errortext.setTextFill(Color.RED);

                LocalDate selectedDatestart = AD_start.getValue();
                LocalDate selectedDateend = AD_end.getValue();
                if(AD_name.getText().isEmpty()){
                    AD_errortext.setText("Invalid Doctor's Name !");
                }else if(AD_nationalid.getText().isEmpty()){
                    AD_errortext.setText("Invalid Doctor's National ID !");

                }else if(AD_nationalid.getText().length()!=14)
                {
                    AD_errortext.setText("Invalid Doctor's National ID ! 'Must be 14 digit'");

                }else if (AD_email.getText().isEmpty()){
                    AD_errortext.setText("Invalid Doctor's Email");
                }else if(AD_GenderCompo.getValue()==null){
                    AD_errortext.setText("Invalid Doctor's Gender");

                }else if(AD_mobile.getText().isEmpty()){
                    AD_errortext.setText("Invalid Doctor's Mobile Number");
//                }else if(AD_roomNum.getText().isEmpty()){
//                    AD_errortext.setText("Invalid Doctor's  Room Number");
                }else if(selectedDatestart==null){
                    AD_errortext.setText("Select a Doctor's Start date");
                }else if(selectedDateend==null) {
                    AD_errortext.setText("Select a Doctor's end date");
                }else {
                    Doctor d1=new Doctor(AD_name.getText(),AD_nationalid.getText(),AD_email.getText(),AD_GenderCompo.getValue().toString(),AD_mobile.getText(),selectedDatestart,selectedDateend);
                    if (d1.checkValueExists(AD_nationalid.getText())){
                        AD_roomNumLable.setText(d1.roomNum);
                        AD_errortext.setTextFill(Color.GREEN);
                        AD_errortext.setText("This National ID exist before");
                    }else {
                        d1.add();
                        AD_roomNumLable.setText(d1.roomNum);
                        AD_errortext.setTextFill(Color.GREEN);
                        AD_errortext.setText("Added successfully");
                    }

                }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AD_GenderCompo.getItems().addAll(GenderOptions);
    }
}


