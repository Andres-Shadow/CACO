package co.uq.proyectobases2.DAO;

import co.uq.proyectobases2.Logica.Aprendiz;
import co.uq.proyectobases2.Logica.Curso;
import co.uq.proyectobases2.Logica.Equipo;
import co.uq.proyectobases2.Logica.Rango;
import oracle.jdbc.internal.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GeneralDao {
    private final String stringConexion = "jdbc:oracle:thin:@localhost:1521:xe";
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private Connection cx;
    private String usr = "MULTINIVEL";
    private String passwd = "1234";
    private Statement statement;
    private ResultSet resultSet;

    public static void main(String[] args) {

        GeneralDao conexion  = new GeneralDao();
        conexion.conectar();

    }

    public Connection conectar(){
        try{
            Class.forName(driver);
            cx = DriverManager.getConnection(stringConexion, usr, passwd);
            System.out.println("Se conectó a la base de datos mi rey");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se conectó a la base de datos mi rey");
        }
        return cx;
    }

    public HashMap<String, String> retornarAprendices(){
        HashMap<String, String> aprendices = new HashMap<>();
        String consulta = "select nombre, email from aprendiz";
        String nombre="", email="";
        try{
            statement = cx.createStatement();
            resultSet = statement.executeQuery(consulta);
            while (resultSet.next()){
                nombre = resultSet.getString(1);
                email = resultSet.getString(2);
                aprendices.put(nombre, email);
            }
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
            System.out.println("ENTRO AQUI");
        }

        return  aprendices;
    }

    public float retornarTotalGananciasMes(int id_mentor){
        float resultado = 0;
        try {
            String sql = "{?=call TOTAL_VENTAS_MES(?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.NUMERIC);
            st.setInt(2, id_mentor);
            st.execute();
            resultado = st.getFloat(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultado;
    }

    public float retornarTotalGananciasTotales(int id_mentor){
        float resultado = 0;
        try {
            String sql = "{?=call TOTAL_COMISIONES_MAESTRO(?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.NUMERIC);
            st.setInt(2, id_mentor);
            st.execute();
            resultado = st.getFloat(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public Equipo retornarEquipoMaestro(int id_mentor){
        Equipo resultado = null;
        try {
            String sql = "{?=call RETORNAR_EQUIPO_MAESTRO(?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.setInt(2, id_mentor);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while(rs.next()){
                resultado = new Equipo(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public int registrarAfiliados(
            int id_rango,
            int id_equipo,
            int id_monitor,
            String nombreHijo,
            String emailHijo,
            int telefonoHijo
    ){
        int resultado = 0;
        try {
            String sql = "{?=call REGISTRAR_AFILIADO(?,?,?,?,?,?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.NUMBER);
            st.setInt(2, id_rango);
            st.setInt(3, id_equipo);
            st.setInt(4, id_monitor);
            st.setString(5, nombreHijo);
            st.setString(6, emailHijo);
            st.setInt(7, telefonoHijo);
            st.execute();
            resultado = st.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public Rango retornarRangoMestro(int id_mentor){
        Rango resultado = null;
        try {
            String sql = "{?=call RETORNAR_RANGO_MAESTRO(?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.setInt(2, id_mentor);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while(rs.next()){
                resultado = new Rango(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getFloat(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    public int tramitarCompraCursos(int id_mentor, int id_curso, int cupos){
        int resultado = 0;
        try {
            String sql = "{?=call TRAMITAR_VENTA(?,?,?)}";
            CallableStatement st = cx.prepareCall( sql);
            st.registerOutParameter(1, OracleTypes.NUMERIC);
            st.setInt(2, id_mentor);
            st.setInt(3, id_curso);
            st.setInt(4, cupos);
            st.execute();
            resultado = st.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultado;
    }

    public ArrayList<Curso> retornarCursosPorAprendiz(int aprendiz_id){
        ArrayList<Curso> cursos = new ArrayList<>();
        try{
            String sql = "{?=call LISTAR_CURSOS_VENDIDOS_ID(?)}";
            CallableStatement st = cx.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.REF_CURSOR);
            st.setInt(2, aprendiz_id);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()){
                cursos.add(
                        new Curso(
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getString(1)
                        )
                );
            }
            rs.close();
            st.close();
        }catch (Exception e){

        }

        return cursos;
    }

    public ArrayList<Aprendiz> listarAfiliadosMaestrio(int maestro_id){
        ArrayList<Aprendiz> aprendices = new ArrayList<>();
        try{
            String sql = "{?=call LISTAR_AFILIADOS(?)}";
            CallableStatement st = cx.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.REF_CURSOR);
            st.setInt(2, maestro_id);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()){
                aprendices.add(
                        new Aprendiz(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getDate(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9)
                        )
                );
            }
            rs.close();
            st.close();
        }catch (Exception e){

        }

        return aprendices;
    }

    public ArrayList<Curso> listarCursos(){
        ArrayList<Curso> cursos = new ArrayList<>();
        try{
            String sql = "{?=call LISTAR_CURSOS_FALTANTES}";
            CallableStatement st = cx.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.REF_CURSOR);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()){
                cursos.add(
                        new Curso(
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getString(1)
                        )
                );
            }
            rs.close();
            st.close();
        }catch (Exception e){

        }

        return cursos;
    }


    //LOGIN
    public Aprendiz retornarAprendizPorEmail(String email){
        Aprendiz a = null;
        String consulta = "select  aprendiz_id, rango_id,equipo_id, monitor_id, fecha_afiliacion, estado, nombre, email, telefono from aprendiz \n" +
                "where email = '"+email+"'";
        try{
            statement = cx.createStatement();
            resultSet = statement.executeQuery(consulta);
            while (resultSet.next()){
                a = new Aprendiz(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
            System.out.println("ENTRO AQUI");
        }
        return a;
    }





}
