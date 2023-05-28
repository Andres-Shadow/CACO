package co.uq.proyectobases2.Principal;

import co.uq.proyectobases2.Controladores.Controlador_login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uq/proyectobases2/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Controlador_login control = loader.getController();
            control.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
        }
    }
}