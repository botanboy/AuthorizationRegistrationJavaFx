package sample.DB;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DB_Change {
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
    public Map<String, String> getUser(String login) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1";
        ResultSet res = statement.executeQuery(sql);

        // Заполняем список данными про выбранного пользователя
        Map<String, String> user = new HashMap<>();
        while(res.next()) {
            user.put("id", res.getString("id"));
            user.put("login", res.getString("login"));
            user.put("email", res.getString("email"));
            user.put("password", res.getString("password"));
        }

        // Возвращаем список
        return user;
    }
    public boolean updateUser(String login, String email, String password, String oldLogin) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE `users` SET `login` = ?, `email` = ?, `password` = ? WHERE `login` = ?";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1");
        if(res.next()) // Если есть, то возвращаем false
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);

        prSt.setString(4, oldLogin);
        prSt.executeUpdate();
        return true;
    }



}
