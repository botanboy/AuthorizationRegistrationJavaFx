package sample.DB;

import java.sql.*;

public class DB_Change {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "artzarg";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    private Connection dbConn = null;
//    private ControllerAutoRegis loginAR = new ControllerAutoRegis();
//    private String loginAuth = loginAR.login_auto.getCharacters().toString();

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connSrt = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connSrt, LOGIN, PASSWORD);
        return dbConn;
    }
    public int changeUsers (String login, String email, String password)throws SQLException, ClassNotFoundException{
        Statement statement = getDbConnection().createStatement();
        String sql = "UPDATE `users` SET `login` = '"+ login +"', `email` = '" + email + "', `password` = '" + password + "'";
        int res = statement.executeUpdate(sql);
        return res;
    }

}
