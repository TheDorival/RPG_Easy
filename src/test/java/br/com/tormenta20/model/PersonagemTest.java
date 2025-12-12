package br.com.tormenta20.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.factory.*;

public class PersonagemTest {
    
    private Personagem personagem;
    
    @BeforeEach
    public void setUp() {
        personagem = new Personagem("Teste", 5);
        personagem.setRaca(RacaFactory.criarHumano());
        personagem.setClasse(ClasseFactory.criarGuerreiro());
    }
    
    @Test
    public void testCriacaoPersonagem() {
        assertNotNull(personagem);
        assertEquals("Teste", personagem.getNome());
        assertEquals(5, personagem.getNivel());
    }
    
    @Test
    public void testCalcularPVePM() {
        personagem.getAtributos().setValor(TipoAtributo.CONSTITUICAO, 14);
        personagem.calcularPVePM();
        
        // Guerreiro: (10 + modCon) x nivel = (10 + 2) x 5 = 60
        assertTrue(personagem.getPontosVida() > 0);
        assertTrue(personagem.getPontosMana() > 0);
    }
    
    @Test
    public void testCalcularDefesa() {
        personagem.getAtributos().setValor(TipoAtributo.DESTREZA, 14);
        personagem.setArmaduraEquipada(ArmaduraFactory.criarCouro());
        personagem.calcularDefesa();
        
        // 10 + modDex(2) + armadura(2) = 14
        assertEquals(14, personagem.getDefesa());
    }
    
    @Test
    public void testCalcularModificadorMagia() {
        Classe mago = ClasseFactory.criarArcanista();
        personagem.setClasse(mago);
        personagem.getAtributos().setValor(TipoAtributo.INTELIGENCIA, 18);
        
        // 10 + nivel/2 + modInt = 10 + 2 + 4 = 16
        assertEquals(16, personagem.calcularModificadorMagia());
    }
    
    @Test
    public void testAdicionarPericia() {
        Pericia atletismo = new Pericia(TipoPericia.ATLETISMO);
        personagem.adicionarPericia(atletismo);
        
        assertTrue(personagem.getPericias().contains(atletismo));
    }
    
    @Test
    public void testRolarAtaque() {
        personagem.setArmaEquipada(ArmaFactory.criarEspada());
        personagem.getAtributos().setValor(TipoAtributo.FORCA, 16);
        
        int ataque = personagem.rolarAtaque();
        assertTrue(ataque > 0);
    }
    
    @Test
    public void testRolarDano() {
        personagem.setArmaEquipada(ArmaFactory.criarEspada());
        personagem.getAtributos().setValor(TipoAtributo.FORCA, 16);
        
        int dano = personagem.rolarDano();
        assertTrue(dano > 0);
    }
}