package test;

import dao.impl.DaoEnMemoria;
import dao.impl.DaoH2Odontologo;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;
import test.OdontologoEnMemoriaTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class OdontologoEnMemoriaTest {
    private static final Logger logger = Logger.getLogger(OdontologoEnMemoriaTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new DaoEnMemoria());

    @Test
    @DisplayName("Testear que se agregue un odontologo de manera correcta")
    void caso1(){
        Odontologo odontologo = new Odontologo("123", "Ricardo", "Cuellar");
        Odontologo odontologoEnMemoria = odontologoService.guardarOdontologo(odontologo);
        logger.info(odontologoEnMemoria);
        assertNotNull(odontologoEnMemoria);
    }

    @Test
    @DisplayName("Testear que se listen todos los odontologos")
    void caso2(){
        //DADO
        List<Odontologo> odontologoList;
        Odontologo odontologo1 = new Odontologo("154", "Luisa", "Vargas");
        Odontologo odontologo2 = new Odontologo("457", "Arya", "Lopez");
        Odontologo odontologoEnMemoria1 = odontologoService.guardarOdontologo(odontologo1);
        Odontologo odontologoEnMemoria2 = odontologoService.guardarOdontologo(odontologo2);

        //CUANDO
        odontologoList = odontologoService.buscarTodos();
        logger.info(odontologoList);
        // entonces
        assertNotNull(odontologoList);
    }
}