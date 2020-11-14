package sample.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Button btn_data, back;

    @FXML
    private PasswordField password_data;

    private DB_Change db = new DB_Change();
    private ControllerAutoRegis auth = new ControllerAutoRegis();
    private String userLogin;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
            Map<String, String> user = db.getUser("Admin");
            login_data.setText(user.get("login"));
            userLogin = user.get("login");
            email_data.setText(user.get("email")); // Устанавливаем текст в поле с email
        btn_data.setOnAction(event -> {
                String login = login_data.getCharacters().toString();
                String email = email_data.getCharacters().toString();

            String pass = auth.md5str(password_data.getCharacters().toString());

            try {

                boolean updateUser = db.updateUser(login, email, pass, userLogin);
                if(updateUser) {
                    login_data.setText("");
                    email_data.setText("");
                    password_data.setText("");
                    btn_data.setText("Готово");
                } else
                    btn_data.setText("Введите другой логин");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        back.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scenes/article.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Регистрация и Авторизация");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        });

    }

}
