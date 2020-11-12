package sample.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB.DB_RegAuth;

public class ControllerAutoRegis {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_reg;

    @FXML
    private TextField email_reg;

    @FXML
    private Button btn_reg;

    @FXML
    private PasswordField password_reg;

    @FXML
    public TextField login_auto;

    @FXML
    private Button btn_auto;

    @FXML
    private PasswordField password_auto;


    private DB_RegAuth db = new DB_RegAuth();

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {
            login_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            password_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Изменить данные");

            if (login_reg.getCharacters().length() <= 3){
                login_reg.setStyle("-fx-border-color: red");
                btn_reg.setText("Короткий логин");
                return;
            } else if (email_reg.getCharacters().length() <= 5){
                email_reg.setStyle("-fx-border-color: red");
                btn_reg.setText("Email некоректен");
                return;
            } else if (password_reg.getCharacters().length() <= 6){
                password_reg.setStyle("-fx-border-color: red");
                btn_reg.setText("Пароль меньше 6");

                return;
            }
            String pass = md5str(password_reg.getCharacters().toString());

            try {
                boolean isReg = db.regUsers(login_reg.getCharacters().toString(),
                        email_reg.getCharacters().toString(), pass);
                if (isReg){
                    login_reg.setText("");
                    email_reg.setText("");
                    password_reg.setText("");
                    btn_reg.setText("Готово");
                } else {
                    btn_reg.setText("Пользователь существует");
                    login_reg.setStyle("-fx-border-color: red");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        btn_auto.setOnAction(event -> {
            login_auto.setStyle("-fx-border-color: #fafafa");
            password_auto.setStyle("-fx-border-color: #fafafa");
            btn_auto.setText("Готово");

            if (login_auto.getCharacters().length() <= 3){
                login_auto.setStyle("-fx-border-color: red");
                return;
            } else if (password_auto.getCharacters().length() <= 6){
                password_auto.setStyle("-fx-border-color: red");
                return;
            }
            String pass = md5str(password_auto.getCharacters().toString());
            try {
                boolean isAuth = db.authUsers(login_auto.getCharacters().toString(), pass);
                if (isAuth){
                    login_auto.setText("");
                    password_auto.setText("");
                    openChange(event);
                } else {
                    btn_auto.setText("Пользователь not found");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

    public void openChange(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/scenes/changeUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }


}

