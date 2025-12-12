package br.com.tormenta20.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.enums.TipoPericia;
import br.com.tormenta20.enums.TipoAtributo;

public class PericiaTest {
    
    @Test
    public void testCalcularValorSemTreinamento() {
        Pericia atletismo = new Pericia(TipoPericia.ATLETISMO);
        
        // Nível 4, Modificador +3, Não Treinada
        int valor = atletismo.calcularValor(4, 3);
        assertEquals(5, valor); // 4/2 + 3 = 2 + 3 = 5
    }
    
    @Test
    public void testCalcularValorComTreinamento() {
        Pericia furtividade = new Pericia(TipoPericia.FURTIVIDADE);
        furtividade.setTreinada(true);
        
        // Nível 6, Modificador +2, Treinada (+2)
        int valor = furtividade.calcularValor(6, 2);
        assertEquals(7, valor); // 6/2 + 2 + 2 = 3 + 2 + 2 = 7
    }
    
    @Test
    public void testRolarPericia() {
        Pericia percepcao = new Pericia(TipoPericia.PERCEPCAO);
        int resultado = percepcao.rolarPericia(4, 2);
        
        // d20 + modificador (6)
        assertTrue(resultado >= 7 && resultado <= 26);
    }
    
    @Test
    public void testAtributoBase() {
        assertEquals(TipoAtributo.FORCA, TipoPericia.ATLETISMO.getAtributoBase());
        assertEquals(TipoAtributo.DESTREZA, TipoPericia.ACROBACIA.getAtributoBase());
        assertEquals(TipoAtributo.SABEDORIA, TipoPericia.PERCEPCAO.getAtributoBase());
    }
}