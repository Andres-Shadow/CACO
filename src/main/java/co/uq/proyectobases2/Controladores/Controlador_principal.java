package co.uq.proyectobases2.Controladores;

import co.uq.proyectobases2.DAO.GeneralDao;
import co.uq.proyectobases2.Logica.Aprendiz;
import co.uq.proyectobases2.Logica.Curso;
import co.uq.proyectobases2.Logica.Equipo;
import co.uq.proyectobases2.Logica.Rango;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_principal implements Initializable {

    private Stage stage;
    private Controlador_login login;
    private String emailUsuario="";
    private Aprendiz usuario = null;
    private Rango rangoUsuario = null;
    private Equipo equipoUsuario = null;
    private GeneralDao dao;
    private Curso cursoSeleccionado = null;

    @FXML
    private Label lbl_nombre;

    @FXML
    private Label lbl_ganancias;

    @FXML
    private Label lbl_ganancias_totales;

    @FXML
    private Label lbl_cant_cupos;

    @FXML
    private Label lbl_equipo;

    @FXML
    private Label lbl_rango;


    @FXML
    private TableColumn<Curso, String> col_nombre_curso;

    @FXML
    private TableColumn<Aprendiz, String> col_afiliados;


    @FXML
    private TableColumn<Curso, String> col_ofer_nombre;

    @FXML
    private TableColumn<Curso, Float> col_ofer_precio;

    @FXML
    private TableView<Curso> table_cursos;

    @FXML
    private TableView<Curso> tbl_oferta_cursos;

    @FXML
    private TableView<Aprendiz> tbl_afiliados;

    @FXML
    private TextField txt_cant_cupos;


    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_telefono;

    @FXML
    private ObservableList<Curso> lista_cursos = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Aprendiz> lista_afiliados = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Curso> lista_oferta_cursos = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(String email, Stage stage, Controlador_login login){
        this.login = login;
        this.stage = stage;
        emailUsuario = email;
        dao = new GeneralDao();
        dao.conectar();
        setearInformacionUsuario(email);
        acomodarCursos(usuario.getId_aprendiz());
        acomodarOfertaCursos();
        acomodarAfiliados(usuario.getId_aprendiz());
        acomodarGanancias(usuario.getId_aprendiz());
    }

    public void setearInformacionUsuario(String email){
        usuario = dao.retornarAprendizPorEmail(email);
        lbl_nombre.setText("Bienvenido: "+usuario.getNombre());
        this.equipoUsuario = dao.retornarEquipoMaestro(this.usuario.getId_aprendiz());
        this.lbl_equipo.setText("Tu equipo actual es: "+this.equipoUsuario.getNombre());
        this.rangoUsuario = dao.retornarRangoMestro(this.usuario.getId_aprendiz());
        this.lbl_rango.setText("Tu rango actual es: "+this.rangoUsuario.getEtiqueta());
    }

    public void acomodarCursos(int id_usuario){
        this.col_nombre_curso.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.table_cursos.getItems().clear();
        ArrayList<Curso> cursos = dao.retornarCursosPorAprendiz(id_usuario);
        this.lista_cursos.clear();
        this.lista_cursos.setAll(cursos);
        this.table_cursos.setItems(lista_cursos);
    }

    public void acomodarAfiliados(int id_usuario){
        this.col_afiliados.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tbl_afiliados.getItems().clear();
        ArrayList<Aprendiz> aprendices = dao.listarAfiliadosMaestrio(id_usuario);
        this.lista_afiliados.clear();
        this.lista_afiliados.setAll(aprendices);
        this.tbl_afiliados.setItems(lista_afiliados);
    }

    public void acomodarOfertaCursos(){

        this.col_ofer_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.col_ofer_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ArrayList<Curso> cursos = dao.listarCursos();
        this.lista_oferta_cursos.clear();
        this.lista_oferta_cursos.setAll(cursos);
        this.tbl_oferta_cursos.setItems(lista_oferta_cursos);
        this.tbl_oferta_cursos.setRowFactory(tv ->{
            TableRow<Curso> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2) {
                    cursoSeleccionado = row.getItem();
                }
            });
            return row;
        });
    }

    public void acomodarGanancias(int id_usuario){
        float ganancias = dao.retornarTotalGananciasMes(id_usuario);
        this.lbl_ganancias.setText("$ "+ganancias);
        ganancias = dao.retornarTotalGananciasTotales(id_usuario);
        this.lbl_ganancias_totales.setText("$ "+ganancias);
    }

    public void tramitarCompraCurso(){
        int cantidad = Integer.parseInt(this.txt_cant_cupos.getText());
        int resultado = 0;
        if(cantidad<cursoSeleccionado.getCupos()){
            resultado = realizarCompra(cursoSeleccionado.getCurso_id(), this.usuario.getId_aprendiz(), cantidad);
        }
        if(resultado == 1){
            acomodarGanancias(this.usuario.getId_aprendiz());
            acomodarCursos(this.usuario.getId_aprendiz());
            mostrarMensaje("Compra realizada", "Compra realizada", "Se ha realizado la compra del curso", Alert.AlertType.CONFIRMATION);
        }
    }

    public int realizarCompra(int id_curso, int id_usuario, int cantidad){
        int resultado = dao.tramitarCompraCursos(id_usuario, cursoSeleccionado.getCurso_id(), cantidad);
        if(resultado == 1){
            return 1;
        }else{
            return 0;
        }
    }

    @FXML
    public void registrarAfiliado(){
        int id_rango = 1;
        int id_equipo = this.equipoUsuario.getId_equipo();
        int id_monitor = this.usuario.getId_aprendiz();
        String nombreHijo = txt_nombre.getText();
        String emailHijo = txt_email.getText();
        int telefonoHijo = Integer.parseInt(txt_telefono.getText());

        int resultado = dao.registrarAfiliados(id_rango, id_equipo, id_monitor, nombreHijo, emailHijo, telefonoHijo);

        if(resultado == 1){
            acomodarAfiliados(this.usuario.getId_aprendiz());
            mostrarMensaje("Aprendiz agregado", "Aprendiz agregado", "Se ha agregado un aprendiz", Alert.AlertType.INFORMATION);
        }

    }


    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    //---------------MANEJO DE VENTANAS ---------------

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void show() {
        stage.show();
    }


    public void volver_Login_A(ActionEvent event) {
        stage.close();
        login.show();
    }
}
