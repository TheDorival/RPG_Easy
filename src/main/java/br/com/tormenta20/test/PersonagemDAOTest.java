package br.com.tormenta20.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import java.util.*;

public class PersonagemDAOTest {
    
    private PersonagemDAO dao;
    private Personagem personagemTeste;
    
    @BeforeEach
    public void setUp() {
        dao = new PersonagemDAO();
        personagemTeste = new Personagem("Teste", 5);
    }
    
    @AfterEach
    public void tearDown() {
        dao.limparBase();
    }
    
    @Test
    public void testSalvarPersonagem() {
        int id = dao.salvar(personagemTeste);
        assertTrue(id > 0);
        
        Personagem recuperado = dao.buscarPorId(id);
        assertNotNull(recuperado);
        assertEquals("Teste", recuperado.getNome());
    }
    
    @Test
    public void testSalvarPersonagemNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.salvar(null);
        });
    }
    
    @Test
    public void testBuscarPorIdInexistente() {
        Personagem p = dao.buscarPorId(999);
        assertNull(p);
    }
    
    @Test
    public void testListarTodos() {
        dao.salvar(new Personagem("P1", 1));
        dao.salvar(new Personagem("P2", 2));
        dao.salvar(new Personagem("P3", 3));
        
        List<Personagem> todos = dao.listarTodos();
        assertEquals(3, todos.size());
    }
    
    @Test
    public void testAtualizar() {
        int id = dao.salvar(personagemTeste);
        
        Personagem atualizado = new Personagem("Atualizado", 10);
        boolean resultado = dao.atualizar(id, atualizado);
        
        assertTrue(resultado);
        assertEquals("Atualizado", dao.buscarPorId(id).getNome());
    }
    
    @Test
    public void testAtualizarInexistente() {
        boolean resultado = dao.atualizar(999, personagemTeste);
        assertFalse(resultado);
    }
    
    @Test
    public void testDeletar() {
        int id = dao.salvar(personagemTeste);
        boolean resultado = dao.deletar(id);
        
        assertTrue(resultado);
        assertNull(dao.buscarPorId(id));
    }
    
    @Test
    public void testBuscarPorNivel() {
        dao.salvar(new Personagem("P1", 5));
        dao.salvar(new Personagem("P2", 5));
        dao.salvar(new Personagem("P3", 10));
        
        List<Personagem> nivel5 = dao.buscarPorNivel(5);
        assertEquals(2, nivel5.size());
    }
    
    @Test
    public void testBuscarPorNome() {
        dao.salvar(new Personagem("Aragorn", 10));
        dao.salvar(new Personagem("Legolas", 8));
        dao.salvar(new Personagem("Gimli", 9));
        
        List<Personagem> resultado = dao.buscarPorNome("ara");
        assertEquals(1, resultado.size());
        assertEquals("Aragorn", resultado.get(0).getNome());
    }
}

