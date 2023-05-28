package co.uq.proyectobases2.Controladores;

import co.uq.proyectobases2.DAO.GeneralDao;
import co.uq.proyectobases2.Logica.Aprendiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controlador_login {
    //LOGICA
    private GeneralDao dao = new GeneralDao();
    private HashMap<String, String> aprendices;
    private Stage stage;
    final String ruta_ventana = "/co/uq/proyectobases2/vtn.fxml";

    //INTERFAZ
    @FXML private Button btn_ingreso;

    @FXML private TextField txt_passws;

    @FXML private TextField txt_usuario;

    @FXML
    public void verificar(){
        dao.conectar();
        aprendices = dao.retornarAprendices();
        String nombre = "", email = "";
        nombre = txt_usuario.getText();
        email = txt_passws.getText();

        if(aprendices.get(nombre)!=null && aprendices.values().contains(email)){
            try {
                mostrarVetanaPpal(ruta_ventana, email);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            mostrarMensaje("ERROR", "ERROR", "USUARIO NO ENCONTRADO", Alert.AlertType.ERROR);
        }
    }

    public void mostrarVetanaPpal(String ruta, String email) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Controlador_principal aprendiz = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        aprendiz.init(email,stage, this);
        stage.setScene(scene);
        stage.show();
        this.stage.close();
    }

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void show() {
        stage.show();
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
