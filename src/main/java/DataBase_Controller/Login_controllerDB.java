package DataBase_Controller;

import java.sql.*;

public class Login_controllerDB {
public static boolean Cheak_User_Name (String UserName,String Password) throws SQLException {
    PreparedStatement PS = null;
    ResultSet RS = null;
    String command="select * from admin where UserName = ? and Password = ?";
    try {
        PS = connection.connect().prepareStatement(command);
        PS.setString(1,UserName);
        PS.setString(2,Password);
        RS=PS.executeQuery();
        if(RS.next()){
            return true;
        }else
            return false;

    }catch (SQLException e) {
        return false;

    }finally {
        PS.close();
        RS.close();
    }
}
}
