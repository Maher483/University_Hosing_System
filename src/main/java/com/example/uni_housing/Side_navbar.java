package com.example.uni_housing;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Side_navbar {

    public BorderPane Home_bp;
    public AnchorPane Home_ap;
    public AnchorPane Search_ap;
   static public Text welcome_name;
    private Stage stage;
    private Scene scene;

    public void Logout(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void Search(MouseEvent mouseEvent) {
        loadPage("Search");

    }

    public void Rooms(MouseEvent mouseEvent) {
        loadPage("Rooms");

    }

    public void Update_Doctor(MouseEvent mouseEvent) {
        loadPage("Update_Doctor");

    }

    public void Update_Student(MouseEvent mouseEvent) {
        loadPage("Updata_Student");

    }

    public void Add_Doctor(MouseEvent mouseEvent) {
        loadPage("Add_Doctor");

    }

    public void Add_Student(MouseEvent mouseEvent) {
        loadPage("Add_Student");
    }

    public void Home(MouseEvent mouseEvent) {
        loadPage("Add_Student");
    }

    public  void loadPage(String page){
        Parent root =null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Home_bp.setCenter(root);
    }
}