package com.example.uni_housing;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

import static DataBase_Controller.Login_controllerDB.Cheak_User_Name;
import static com.example.uni_housing.Side_navbar.welcome_name;

public class LoginController {




    @FXML
    private JFXButton Close_B;

    @FXML
    private Label Error_text;

    @FXML
    private AnchorPane Login_AP;

    @FXML
    private JFXButton Login_B;

    @FXML
    private PasswordField Password_T;

    @FXML
    private TextField UserName_T;

    @FXML
    void Close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void Login(ActionEvent event) throws IOException, SQLException {
    boolean flag =Cheak_User_Name(UserName_T.getText(),Password_T.getText());
        Stage stage=new Stage();
    if(flag){
        Stage stage_login=(Stage) Login_B.getScene().getWindow();
        stage_login.close();
        FXMLLoader loader=new FXMLLoader();
        Pane root=loader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.show();


    }else
        Error_text.setText("Incorrect User Name or password");
    }

}
