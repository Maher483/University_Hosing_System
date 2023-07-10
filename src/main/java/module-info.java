module com.example.uni_housing {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;


    opens com.example.uni_housing to javafx.fxml;
    exports com.example.uni_housing;
    exports DataBase_Controller;
    opens DataBase_Controller to javafx.fxml;
}