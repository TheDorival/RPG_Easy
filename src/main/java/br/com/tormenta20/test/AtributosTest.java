package br.com.tormenta20.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import java.util.*;

public class AtributosTest {
    
    @Test
    public void testModificadorAtributo() {
        Atributos atrib = new Atributos();
        
        atrib.setValor(TipoAtributo.FORCA, 18);
        assertEquals(4, atrib.getModificador(TipoAtributo.FORCA));
        
        atrib.setValor(TipoAtributo.DESTREZA, 10);
        assertEquals(0, atrib.getModificador(TipoAtributo.DESTREZA));
        
        atrib.setValor(TipoAtributo.INTELIGENCIA, 8);
        assertEquals(-1, atrib.getModificador(TipoAtributo.INTELIGENCIA));
    }
    
    @Test
    public void testAplicarModificadores() {
        Atributos atrib = new Atributos();
        atrib.setValor(TipoAtributo.FORCA, 14);
        atrib.setValor(TipoAtributo.DESTREZA, 12);
        
        Map<TipoAtributo, Integer> mods = new HashMap<>();
        mods.put(TipoAtributo.FORCA, 2);
        mods.put(TipoAtributo.DESTREZA, -1);
        
        atrib.aplicarModificadores(mods);
        
        assertEquals(16, atrib.getValor(TipoAtributo.FORCA));
        assertEquals(11, atrib.getValor(TipoAtributo.DESTREZA));
    }
}

