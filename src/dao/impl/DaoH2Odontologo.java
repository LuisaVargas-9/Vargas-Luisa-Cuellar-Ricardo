package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoH2Odontologo implements IDao<Odontologo> {
    public static final Logger logger = Logger.getLogger(DaoH2Odontologo.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT, ?, ?, ?)";
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoAGuardar = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNumeroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            connection.commit();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                Integer idDesdeDb = resultSet.getInt(1);
                odontologoAGuardar = new Odontologo(idDesdeDb, odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());

            }
            logger.info("Odontologo guardado en la base de datos" + odontologoAGuardar);

        }catch (Exception e){
            if(connection != null){
                try{
                    connection.rollback();
                }catch (SQLException ex){
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return odontologoAGuardar;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologoABuscar = null;
        try{
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()){
                Integer id = resultSet.getInt(1);
                String numeroMatricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologoABuscar = new Odontologo(id, numeroMatricula, nombre, apellido);
                odontologos.add(odontologoABuscar);
                logger.info("odontologo buscado: " + odontologoABuscar);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return odontologos;
    }
}
