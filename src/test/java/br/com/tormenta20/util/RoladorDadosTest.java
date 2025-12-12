package br.com.tormenta20.util;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoladorDadosTest {
    
    @Test
    public void testRolarD20() {
        int resultado = RoladorDados.rolarD20();
        assertTrue(resultado >= 1 && resultado <= 20, 
            "D20 deve estar entre 1 e 20");
    }
    
    @Test
    public void testRolarMultiplosDados() {
        int resultado = RoladorDados.rolar(2, 6);
        assertTrue(resultado >= 2 && resultado <= 12, 
            "2d6 deve estar entre 2 e 12");
    }
    
    @Test
    public void testRolarComModificador() {
        ResultadoRolagem resultado = RoladorDados.rolarComModificador(5);
        
        assertTrue(resultado.getValorDado() >= 1 && resultado.getValorDado() <= 20);
        assertEquals(5, resultado.getModificador());
        assertEquals(resultado.getValorDado() + 5, resultado.getTotal());
    }
    
    @Test
    public void testCritico() {
        for (int i = 0; i < 100; i++) {
            ResultadoRolagem resultado = RoladorDados.rolarComModificador(0);
            if (resultado.isCritico()) {
                assertEquals(20, resultado.getValorDado());
                break;
            }
        }
        // Estatisticamente, em 100 rolagens devemos encontrar pelo menos um 20
    }
    
    @Test
    public void testRoladorInterface() {
        RoladorDados rolador = new RoladorDados();
        int resultado = rolador.rolarDado(8);
        assertTrue(resultado >= 1 && resultado <= 8);
    }
}
