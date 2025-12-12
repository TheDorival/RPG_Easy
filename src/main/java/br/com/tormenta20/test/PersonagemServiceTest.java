package br.com.tormenta20.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import java.util.*;

public class PersonagemServiceTest {
    
    private PersonagemService service;
    private Raca racaElfo;
    private Classe classeGuerreiro;
    private Origem origemNobre;
    
    @BeforeEach
    public void setUp() {
        service = new PersonagemService();
        
        // Setup Raça
        racaElfo = new Raca("Elfo", "Seres da floresta");
        racaElfo.adicionarModificador(TipoAtributo.DESTREZA, 2);
        racaElfo.adicionarCaracteristica("Visão na Penumbra");
        
        // Setup Classe
        classeGuerreiro = new Classe("Guerreiro", "Lutador", 
                                     TipoAtributo.FORCA);
        classeGuerreiro.adicionarPericiaDisponivel(TipoPericia.ATLETISMO);
        classeGuerreiro.adicionarPericiaDisponivel(TipoPericia.INTIMIDACAO);
        
        // Setup Origem
        origemNobre = new Origem("Nobre", "Nascido na nobreza");
        Poder poder = new Poder("Recursos", "Acesso a dinheiro", "Nobre");
        origemNobre.adicionarPoder(poder);
    }
    
    @Test
    public void testCriarPersonagemValido() {
        Personagem p = service.criarPersonagem("Aragorn", 5, 
                                               racaElfo, classeGuerreiro, origemNobre);
        
        assertNotNull(p);
        assertEquals("Aragorn", p.getNome());
        assertEquals(5, p.getNivel());
        assertEquals(racaElfo, p.getRaca());
        assertEquals(classeGuerreiro, p.getClasse());
        assertEquals(origemNobre, p.getOrigem());
    }
    
    @Test
    public void testCriarPersonagemNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.criarPersonagem("", 5, racaElfo, 
                                   classeGuerreiro, origemNobre);
        });
    }
    
    @Test
    public void testCriarPersonagemNivelInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.criarPersonagem("Test", 0, racaElfo, 
                                   classeGuerreiro, origemNobre);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.criarPersonagem("Test", 21, racaElfo, 
                                   classeGuerreiro, origemNobre);
        });
    }
    
    @Test
    public void testValidarPersonagemCompleto() {
        Personagem p = service.criarPersonagem("Test", 3, 
                                               racaElfo, classeGuerreiro, origemNobre);
        assertTrue(service.validarPersonagem(p));
    }
    
    @Test
    public void testValidarPersonagemIncompleto() {
        Personagem p = new Personagem("Test", 3);
        assertFalse(service.validarPersonagem(p));
    }
    
    @Test
    public void testCalcularBonusTotal() {
        Personagem p = service.criarPersonagem("Test", 4, 
                                               racaElfo, classeGuerreiro, origemNobre);
        
        // Define atributos
        p.getAtributos().setValor(TipoAtributo.FORCA, 16);
        p.getAtributos().setValor(TipoAtributo.INTELIGENCIA, 10);
        
        Map<String, Integer> bonus = service.calcularBonusTotal(p);
        
        assertNotNull(bonus);
        assertTrue(bonus.containsKey("FORCA"));
        assertEquals(3, bonus.get("FORCA")); // (16-10)/2 = 3
        assertEquals(0, bonus.get("INTELIGENCIA")); // (10-10)/2 = 0
    }
    
    @Test
    public void testTreinarPericia() {
        Personagem p = service.criarPersonagem("Test", 3, 
                                               racaElfo, classeGuerreiro, origemNobre);
        
        service.treinarPericia(p, TipoPericia.ATLETISMO);
        
        Pericia atletismo = p.getPericias().stream()
            .filter(per -> per.getTipo() == TipoPericia.ATLETISMO)
            .findFirst()
            .orElse(null);
        
        assertNotNull(atletismo);
        assertTrue(atletismo.isTreinada());
    }
    
    @Test
    public void testAplicarModificadoresRaca() {
        Personagem p = service.criarPersonagem("Legolas", 1, 
                                               racaElfo, classeGuerreiro, origemNobre);
        
        p.getAtributos().setValor(TipoAtributo.DESTREZA, 14);
        p.aplicarModificadoresRaca();
        
        // Elfo ganha +2 Destreza
        assertEquals(16, p.getAtributos().getValor(TipoAtributo.DESTREZA));
    }
}
