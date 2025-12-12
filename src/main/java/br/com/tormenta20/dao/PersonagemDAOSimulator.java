package br.com.tormenta20.dao;

import br.com.tormenta20.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simulador de DAO em memória para testes
 */
public class PersonagemDAOSimulator {
    private Map<Integer, Personagem> database;
    private int nextId;
    
    public PersonagemDAOSimulator() {
        this.database = new HashMap<>();
        this.nextId = 1;
    }
    
    public int salvar(Personagem personagem) {
        if (personagem == null) {
            throw new IllegalArgumentException("Personagem não pode ser nulo");
        }
        
        int id = nextId++;
        personagem.setId(id);
        database.put(id, personagem);
        return id;
    }
    
    public Personagem buscarPorId(int id) {
        return database.get(id);
    }
    
    public List<Personagem> listarTodos() {
        return new ArrayList<>(database.values());
    }
    
    public boolean atualizar(int id, Personagem personagem) {
        if (!database.containsKey(id)) {
            return false;
        }
        personagem.setId(id);
        database.put(id, personagem);
        return true;
    }
    
    public boolean deletar(int id) {
        return database.remove(id) != null;
    }
    
    public List<Personagem> buscarPorNivel(int nivel) {
        return database.values().stream()
            .filter(p -> p.getNivel() == nivel)
            .collect(Collectors.toList());
    }
    
    public List<Personagem> buscarPorNome(String nome) {
        String nomeLower = nome.toLowerCase();
        return database.values().stream()
            .filter(p -> p.getNome().toLowerCase().contains(nomeLower))
            .collect(Collectors.toList());
    }
    
    public void limparBase() {
        database.clear();
        nextId = 1;
    }
}