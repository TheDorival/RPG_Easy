package br.com.tormenta20.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import java.util.*;

public class PersonagemTest {
    
    @Test
    public void testCalcularModificadorMagia() {
        Personagem mago = new Personagem("Gandalf", 10);
        
        Classe classeMago = new Classe("Mago", "Conjurador", 
                                       TipoAtributo.INTELIGENCIA);
        mago.setClasse(classeMago);
        
        mago.getAtributos().setValor(TipoAtributo.INTELIGENCIA, 18);
        
        // 10 + 10/2 + 4 = 10 + 5 + 4 = 19
        assertEquals(19, mago.calcularModificadorMagia());
    }
    
    @Test
    public void testRolarPericia() {
        Personagem ladino = new Personagem("Robin", 6);
        
        Pericia furtividade = new Pericia(TipoPericia.FURTIVIDADE);
        ladino.adicionarPericia(furtividade);
        ladino.getAtributos().setValor(TipoAtributo.DESTREZA, 16);
        
        int resultado = ladino.rolarPericia(TipoPericia.FURTIVIDADE);
        
        // d20 + (6/2 + 3) = d20 + 6
        assertTrue(resultado >= 7 && resultado <= 26);
    }
}
