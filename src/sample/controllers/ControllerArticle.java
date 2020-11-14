package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ControllerArticle {

    @FXML
    private Button btn_add_article;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_exit;

    @FXML
    void initialize(){
        btn_exit.setOnAction(event -> {
            try {
            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(new User(""));

                oos.close();

                Parent root = null;
                root = FXMLLoader.load(getClass().getResource("/sample/scenes/authoRegis.fxml"));

                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Регистрация и Авторизация");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_change.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/scenes/changeUsers.fxml"));
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
