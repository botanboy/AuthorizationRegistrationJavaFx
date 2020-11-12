package sample.DB;

import java.sql.*;

public class DB_RegAuth {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "artzarg";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connSrt = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connSrt, LOGIN, PASSWORD);
        return dbConn;
    }
    public boolean regUsers (String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1");
        if (res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2,email);
        prSt.setString(3, password);
        prSt.executeUpdate();
        return true;
    }
    public boolean authUsers (String login, String password) throws SQLException, ClassNotFoundException{
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "' LIMIT 1";
        ResultSet res = statement.executeQuery(sql);
        return (res.next());
    }

}

