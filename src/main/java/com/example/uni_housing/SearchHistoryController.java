package com.example.uni_housing;

import DataBase_Controller.connection;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchHistoryController implements Initializable {

    public TableView SR_tableview;
    public Label SR_errorText;
    @FXML
    private Label SR_IDlable6;

    @FXML
    private TableColumn<History, String> SR_emailC;

    @FXML
    private Label SR_emaillable2;

    @FXML
    private TableColumn<History, String> SR_endC;

    @FXML
    private Label SR_endlable1;

    @FXML
    private TableColumn<History, String> SR_facultyC;

    @FXML
    private Label SR_facultylable9;

    @FXML
    private TableColumn<History, String> SR_genderC;

    @FXML
    private Label SR_genderlable7;

    @FXML
    private TableColumn<History, String> SR_idC;

    @FXML
    private TableColumn<History, String> SR_mobileNumC;

    @FXML
    private Label SR_mobilelable8;

    @FXML
    private TableColumn<History, String> SR_nameC;

    @FXML
    private Label SR_namelable;

    @FXML
    private TableColumn<History, String> SR_nationalIdC;

    @FXML
    private Label SR_nationalIdlable5;

    @FXML
    private TableColumn<History, String> SR_programC;

    @FXML
    private Label SR_programlable10;

    @FXML
    private TableColumn<History, String> SR_roomNumC;

    @FXML
    private Label SR_roomNumlable4;

    @FXML
    private TextField SR_searchTextfild;

    @FXML
    private TableColumn<History, String> SR_startC;

    @FXML
    private Label SR_startable3;

    @FXML
    private JFXButton Search_HistoryB;

    @FXML
    private AnchorPane Search_ap;

    @FXML
    void Search_History(ActionEvent event) {

        PreparedStatement PS = null;
        ResultSet RS = null;
        String sql = "select * from history where nationalId = ?";
        try {
            PS = connection.connect().prepareStatement(sql);
            PS.setString(1, SR_searchTextfild.getText());

            RS = PS.executeQuery();

            if (RS.next()) {
                SR_errorText.setTextFill(Color.GREEN);
                SR_errorText.setText("The National ID has been found");

                SR_namelable.setText("Name: "+RS.getString("name"));
                SR_nationalIdlable5.setText("National Id: "+RS.getString("nationalId"));
                SR_IDlable6.setText("Id: "+RS.getString("id"));
                SR_genderlable7.setText("Gender: "+RS.getString("gender"));
                SR_mobilelable8.setText("Mobile Num: "+RS.getString("mobileNum"));
                SR_emaillable2.setText("Email: "+RS.getString("email"));
                SR_facultylable9.setText("Faculty: "+RS.getString("faculty"));
                SR_programlable10.setText("Program: "+RS.getString("program"));
                SR_roomNumlable4.setText("Room Name: "+RS.getString("roomNum"));
                SR_startable3.setText("Start Date: "+RS.getString("start"));
                SR_endlable1.setText("End Date: "+RS.getString("end"));


            } else {
                SR_errorText.setTextFill(Color.RED);
                SR_errorText.setText("This National ID dose not exist !");

                SR_namelable.setText(null);
                SR_nationalIdlable5.setText(null);
                SR_IDlable6.setText(null);
                SR_genderlable7.setText(null);
                SR_mobilelable8.setText(null);
                SR_facultylable9.setText(null);
                SR_programlable10.setText(null);
                SR_roomNumlable4.setText(null);
                SR_startable3.setText(null);
                SR_endlable1.setText(null);
                SR_emaillable2.setText(null);
            }
        } catch (SQLException e) {
            System.out.println(e);

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            get_data();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void get_data() throws SQLException {
        PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM history");
        ResultSet rs = stmt.executeQuery();

        ObservableList<History> data = FXCollections.observableArrayList();
        while (rs.next()) {
            String name = rs.getString("name");
            String nationalId = rs.getString("nationalId");
            String id = rs.getString("id");
            String gender = rs.getString("gender");
            String mobileNum = rs.getString("mobileNum");
            String email = rs.getString("email");
            String faculty = rs.getString("faculty");
            String program = rs.getString("program");
            String roomNum = rs.getString("roomNum");
            String start = rs.getString("start");
            String end = rs.getString("end");


            data.add(new History(name,nationalId,email,gender,mobileNum,id,faculty,program,roomNum,start,end));
            SR_nameC.setCellValueFactory(new PropertyValueFactory<History, String>("Name"));
            SR_nationalIdC.setCellValueFactory(new PropertyValueFactory<History, String>("NationalID"));
            SR_idC.setCellValueFactory(new PropertyValueFactory<History, String>("id"));
            SR_emailC.setCellValueFactory(new PropertyValueFactory<History, String>("Email"));
            SR_mobileNumC.setCellValueFactory(new PropertyValueFactory<History, String>("MobileNum"));
            SR_facultyC.setCellValueFactory(new PropertyValueFactory<History, String>("faculty"));
            SR_programC.setCellValueFactory(new PropertyValueFactory<History, String>("program"));
            SR_genderC.setCellValueFactory(new PropertyValueFactory<History, String>("Gender"));
            SR_roomNumC.setCellValueFactory(new PropertyValueFactory<History, String>("roomNum"));
            SR_startC.setCellValueFactory(new PropertyValueFactory<History, String>("selectedDatestart"));
            SR_endC.setCellValueFactory(new PropertyValueFactory<History, String>("selectedDateend"));


            SR_tableview.setItems(data);
        }


        // Add a listener to the items property of the TableView
        SR_tableview.getItems().addListener(new ListChangeListener<Rooms>() {
            @Override
            public void onChanged(Change<? extends Rooms> change) {
                // Handle the event here
                System.out.println("Data loaded into TableView");
            }
        });

        rs.close();
        stmt.close();
        connection.connect().close();
    }
}
