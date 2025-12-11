package br.com.tormenta20.dao;

import br.com.tormenta20.model.*;
import java.util.*;

public class PersonagemDAO {
    // Simulando um banco de dados em memória
    private Map<Integer, Personagem> database;
    private int nextId;
    
    public PersonagemDAO() {
        this.database = new HashMap<>();
        this.nextId = 1;
    }
    
    /**
     * Salva um novo personagem
     */
    public int salvar(Personagem personagem) {
        if (personagem == null) {
            throw new IllegalArgumentException("Personagem não pode ser nulo");
        }
        
        int id = nextId++;
        database.put(id, personagem);
        return id;
    }
    
    /**
     * Busca personagem por ID
     */
    public Personagem buscarPorId(int id) {
        return database.get(id);
    }
    
    /**
     * Lista todos os personagens
     */
    public List<Personagem> listarTodos() {
        return new ArrayList<>(database.values());
    }
    
    /**
     * Atualiza um personagem existente
     */
    public boolean atualizar(int id, Personagem personagem) {
        if (!database.containsKey(id)) {
            return false;
        }
        database.put(id, personagem);
        return true;
    }
    
    /**
     * Remove um personagem
     */
    public boolean deletar(int id) {
        return database.remove(id) != null;
    }
    
    /**
     * Busca personagens por nível
     */
    public List<Personagem> buscarPorNivel(int nivel) {
        List<Personagem> resultado = new ArrayList<>();
        for (Personagem p : database.values()) {
            if (p.getNivel() == nivel) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    /**
     * Busca personagens por nome (contém)
     */
    public List<Personagem> buscarPorNome(String nome) {
        List<Personagem> resultado = new ArrayList<>();
        String nomeLower = nome.toLowerCase();
        
        for (Personagem p : database.values()) {
            if (p.getNome().toLowerCase().contains(nomeLower)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    /**
     * Limpa todos os dados (útil para testes)
     */
    public void limparBase() {
        database.clear();
        nextId = 1;
    }
}