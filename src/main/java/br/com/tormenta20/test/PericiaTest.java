package br.com.tormenta20.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import java.util.*;

public class PericiaTest {
    
    @Test
    public void testCalcularValorPericia() {
        Pericia atletismo = new Pericia(TipoPericia.ATLETISMO);
        
        // Nível 4, Modificador Força +3
        int valor = atletismo.calcularValor(4, 3);
        
        // 4/2 + 3 = 2 + 3 = 5
        assertEquals(5, valor);
    }
    
    @Test
    public void testPericiaComTreinamento() {
        Pericia furtividade = new Pericia(TipoPericia.FURTIVIDADE);
        furtividade.setTreinada(true);
        
        // Nível 6, Modificador +2, Treinada (+2)
        int valor = furtividade.calcularValor(6, 2);
        
        // 6/2 + 2 + 2 = 3 + 2 + 2 = 7
        assertEquals(7, valor);
    }
    
    @Test
    public void testRolarPericia() {
        Pericia percepcao = new Pericia(TipoPericia.PERCEPCAO);
        int resultado = percepcao.rolarPericia(4, 2);
        
        // Deve estar entre 1+5 e 20+5 (d20 + modificador)
        assertTrue(resultado >= 6 && resultado <= 25);
    }
}

