package br.com.tormenta20.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.factory.*;

public class PersonagemServiceTest {
    
    private PersonagemService service;
    
    @BeforeEach
    public void setUp() {
        service = new PersonagemService();
    }
    
    @Test
    public void testCriarPersonagemValido() {
        Personagem p = service.criarPersonagem(
            "Aragorn", 5, 
            RacaFactory.criarHumano(), 
            ClasseFactory.criarGuerreiro(), 
            new Origem("Nobre", "Nobre")
        );
        
        assertNotNull(p);
        assertEquals("Aragorn", p.getNome());
        assertEquals(5, p.getNivel());
    }
    
    @Test
    public void testCriarPersonagemNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.criarPersonagem(
                "", 5,
                RacaFactory.criarHumano(),
                ClasseFactory.criarGuerreiro(),
                new Origem("Teste", "Teste")
            );
        });
    }
    
    @Test
    public void testCriarPersonagemNivelInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.criarPersonagem(
                "Test", 0,
                RacaFactory.criarHumano(),
                ClasseFactory.criarGuerreiro(),
                new Origem("Teste", "Teste")
            );
        });
    }
    
    @Test
    public void testValidarPersonagem() {
        Personagem p = service.criarPersonagem(
            "Test", 3,
            RacaFactory.criarHumano(),
            ClasseFactory.criarGuerreiro(),
            new Origem("Teste", "Teste")
        );
        
        assertTrue(service.validarPersonagem(p));
    }
    
    @Test
    public void testTreinarPericia() {
        Personagem p = service.criarPersonagem(
            "Test", 3,
            RacaFactory.criarHumano(),
            ClasseFactory.criarGuerreiro(),
            new Origem("Teste", "Teste")
        );
        
        service.treinarPericia(p, TipoPericia.ATLETISMO);
        
        Pericia atletismo = p.getPericias().stream()
            .filter(per -> per.getTipo() == TipoPericia.ATLETISMO)
            .findFirst()
            .orElse(null);
        
        assertNotNull(atletismo);
        assertTrue(atletismo.isTreinada());
    }
}