package sample.controllers;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.DB.DB_Change;

public class ControllerChangeUsers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_data;

    @FXML
    private TextField email_data;

    @FXML
    private Button btn_data;

    @FXML
    private PasswordField password_data;

    private DB_Change db = new DB_Change();


    @FXML
    void initialize() {
        btn_data.setOnAction(event -> {
            login_data.setStyle("-fx-border-color: #fafafa");
            email_data.setStyle("-fx-border-color: #fafafa");
            password_data.setStyle("-fx-border-color: #fafafa");
            btn_data.setText("Изменить данные");

            if (login_data.getCharacters().length() <= 3) {
                login_data.setStyle("-fx-border-color: red");
                btn_data.setText("Короткий логин");
                return;
            } else if (email_data.getCharacters().length() <= 5) {
                email_data.setStyle("-fx-border-color: red");
                btn_data.setText("Email некоректен");
                return;
            } else if (password_data.getCharacters().length() <= 6) {
                password_data.setStyle("-fx-border-color: red");
                btn_data.setText("Пароль меньше 6");

                return;
            }
            String pass = md5str(password_data.getCharacters().toString());

            try {
                  db.changeUsers(login_data.getCharacters().toString(),
                          email_data.getCharacters().toString(), pass);
                  login_data.setText("");
                  email_data.setText("");
                  password_data.setText("");
                  btn_data.setText("Готово");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }
    public static String md5str (String password){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

}
