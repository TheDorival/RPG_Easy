package br.com.tormenta20.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.enums.TipoAtributo;
import java.util.*;

public class AtributosTest {
    
    private Atributos atributos;
    
    @BeforeEach
    public void setUp() {
        atributos = new Atributos();
    }
    
    @Test
    public void testValoresPadrao() {
        for (TipoAtributo tipo : TipoAtributo.values()) {
            assertEquals(10, atributos.getValor(tipo));
            assertEquals(0, atributos.getModificador(tipo));
        }
    }
    
    @Test
    public void testModificadorPositivo() {
        atributos.setValor(TipoAtributo.FORCA, 18);
        assertEquals(4, atributos.getModificador(TipoAtributo.FORCA));
        
        atributos.setValor(TipoAtributo.DESTREZA, 20);
        assertEquals(5, atributos.getModificador(TipoAtributo.DESTREZA));
    }
    
    @Test
    public void testModificadorNegativo() {
        atributos.setValor(TipoAtributo.INTELIGENCIA, 8);
        assertEquals(-1, atributos.getModificador(TipoAtributo.INTELIGENCIA));
        
        atributos.setValor(TipoAtributo.CARISMA, 6);
        assertEquals(-2, atributos.getModificador(TipoAtributo.CARISMA));
    }
    
    @Test
    public void testAplicarModificadores() {
        atributos.setValor(TipoAtributo.FORCA, 14);
        
        Map<TipoAtributo, Integer> mods = new HashMap<>();
        mods.put(TipoAtributo.FORCA, 2);
        mods.put(TipoAtributo.DESTREZA, -1);
        
        atributos.aplicarModificadores(mods);
        
        assertEquals(16, atributos.getValor(TipoAtributo.FORCA));
        assertEquals(9, atributos.getValor(TipoAtributo.DESTREZA));
    }
    
    @Test
    public void testMaiorModFisico() {
        atributos.setValor(TipoAtributo.FORCA, 16);
        atributos.setValor(TipoAtributo.DESTREZA, 12);
        
        assertEquals(3, atributos.getMaiorModFisico());
        
        atributos.setValor(TipoAtributo.DESTREZA, 18);
        assertEquals(4, atributos.getMaiorModFisico());
    }
}