package com.example.uni_housing;

import DataBase_Controller.connection;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    private final String[] buildingsName = {"Student male", "Student female", "Doctors male", "Doctors female"};
    public Label R_errortext;
    public Label R_roomnamelable;
    public Label R_buildingnamelable;
    public Label R_availalable;
    public Label R_bed1lable;
    public Label R_bed2lable;

    @FXML
    private JFXButton R_addB;

    @FXML
    private TableColumn<Rooms, String> R_availabillityC;

    @FXML
    private TableColumn<Rooms, String> R_bed1C;

    @FXML
    private TableColumn<Rooms, String> R_bed2C;

    @FXML
    private TableColumn<Rooms, String> R_buildingNameC;

    @FXML
    private ComboBox<String> R_comobox;

    @FXML
    private TableColumn<Rooms, String> R_roomNameC;

    @FXML
    private TextField R_roomtextF;

    @FXML
    private TextField R_roomtextFadd;

    @FXML
    private JFXButton R_searchB;

    @FXML
    private TableView<Rooms> R_tableview;

    @FXML
    private AnchorPane Room_ap;

    @FXML
    void AddRoom(ActionEvent event) {

        String sql = "INSERT INTO room(buildingName,roomName,availability,bed1,bed2) VALUES (?,?,?,?,?);";
        try {
            R_errortext.setTextFill(Color.RED);

            if (R_roomtextFadd.getText().isEmpty()) {
                R_errortext.setText("Enter the room name !!");
            } else {
                Connection conn = connection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, R_comobox.getValue());
                pstmt.setString(2, R_roomtextFadd.getText());
                pstmt.setString(3, "Available");
                pstmt.setString(4, "Available");
                pstmt.setString(5, "Available");


                pstmt.executeUpdate();
                R_errortext.setTextFill(Color.GREEN);
                R_errortext.setText("Added successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void Search_R(ActionEvent event) throws SQLException {
        PreparedStatement PS = null;
        ResultSet RS = null;
        String sql = "select * from room where roomName = ?";
        try {
            PS = connection.connect().prepareStatement(sql);
            PS.setString(1, R_roomtextF.getText());

            RS = PS.executeQuery();

            if (RS.next()) {
                R_errortext.setTextFill(Color.GREEN);
                R_errortext.setText("The Room has been found");

                R_buildingnamelable.setText("Building Name: "+RS.getString("buildingName"));
                R_roomnamelable.setText("Room Name: "+RS.getString("roomName"));
                R_availalable.setText("Availability: "+RS.getString("availability"));
                R_bed1lable.setText("Bed 1 : "+RS.getString("bed1"));
                R_bed2lable.setText("Bed 2 : "+RS.getString("bed2"));

            } else {
                R_errortext.setTextFill(Color.RED);
                R_errortext.setText("This Room dose not exist !");

                R_buildingnamelable.setText(null);
                R_roomnamelable.setText(null);
                R_availalable.setText(null);
                R_bed1lable.setText(null);
                R_bed2lable.setText(null);
            }
        } catch (SQLException e) {
            System.out.println(e);

        }
    }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            try {
                get_data();
                R_comobox.getItems().addAll(buildingsName);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void get_data() throws SQLException {
            PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM room");
            ResultSet rs = stmt.executeQuery();

            ObservableList<Rooms> data = FXCollections.observableArrayList();
            while (rs.next()) {
                String buildingName = rs.getString("buildingName");
                String roomName = rs.getString("roomName");
                String availability = rs.getString("availability");
                String bed1 = rs.getString("bed1");
                String bed2 = rs.getString("bed2");

                data.add(new Rooms(roomName, buildingName, availability, bed1, bed2));
                R_buildingNameC.setCellValueFactory(new PropertyValueFactory<Rooms, String>("buildingName"));
                R_roomNameC.setCellValueFactory(new PropertyValueFactory<Rooms, String>("roomName"));
                R_availabillityC.setCellValueFactory(new PropertyValueFactory<Rooms, String>("availability"));
                R_bed1C.setCellValueFactory(new PropertyValueFactory<Rooms, String>("bed1"));
                R_bed2C.setCellValueFactory(new PropertyValueFactory<Rooms, String>("bed2"));

                R_tableview.setItems(data);
            }


            // Add a listener to the items property of the TableView
            R_tableview.getItems().addListener(new ListChangeListener<Rooms>() {
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




//    // Create a task to periodically check the database for new data
//    Task<Void> task = new Task<Void>() {
//        @Override
//        protected Void call() throws Exception {
//            while (true) {
//                // Retrieve new data from the database
//                ObservableList<Person> newData = FXCollections.observableArrayList();
//                ResultSet rs = stmt.executeQuery("SELECT * FROM person WHERE id > " + maxId); // maxId is the highest id value currently displayed in the table view
//                while (rs.next()) {
//                    Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
//                    newData.add(person);
//                }
//                rs.close();
//
//                // Update the table view with the new data
//                if (!newData.isEmpty()) {
//                    Platform.runLater(() -> data.addAll(newData));
//                    maxId = newData.get(newData.size() - 1).getId();
//                }
//
//                // Wait for some time before checking the database again
//                Thread.sleep(5000); // Wait for 5 seconds
//            }
//        }
//    };

//    // Start the task in a background thread
//    Thread threadTo add a listener to detect changes in the SQLite database and update the table view in real time, you can use the JavaFX `Task` class to run a background thread that periodically checks the database for new data.
//
//        Here's an example code snippet that demonstrates how to add a listener to the SQLite database and update the table view when new data is added:
//
//        ```java
//// Create a task to periodically check the database for new data
//        Task<Void> task = new Task<Void>() {
//@Override
//protected Void call() throws Exception {
//        while (true) {
//        // Retrieve new data from the database
//        ObservableList<Person> newData = FXCollections.observableArrayList();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM person WHERE id > " + maxId); // maxId is the highest id value currently displayed in the table view
//        while (rs.next()) {
//        Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
//        newData.add(person);
//        }
//        rs.close();
//
//        // Update the table view with the new data
//        if (!newData.isEmpty()) {
//        Platform.runLater(() -> data.addAll(newData));
//        maxId = newData.get(newData.size() - 1).getId();
//        }
//
//        // Wait for some time before checking the database again
//        Thread.sleep(5000); // Wait for 5 seconds
//        }
//        }
//        };
//
//// Start the task in a background thread
//        Thread thread= new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
//




























