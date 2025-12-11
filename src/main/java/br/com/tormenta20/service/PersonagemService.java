package br.com.tormenta20.service;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.model.Personagem;
import java.util.*;

public class PersonagemService {
    
    /**
     * Cria um personagem completo
     */
    public Personagem criarPersonagem(String nome, int nivel, Raca raca, 
                                     Classe classe, Origem origem) {
        validarDadosBasicos(nome, nivel);
        
        Personagem personagem = new Personagem(nome, nivel);
        personagem.setRaca(raca);
        personagem.setClasse(classe);
        personagem.setOrigem(origem);
        
        inicializarPericias(personagem);
        aplicarPoderesOrigem(personagem);
        aplicarHabilidadesClasse(personagem);
        
        return personagem;
    }
    
    /**
     * Valida dados básicos do personagem
     */
    private void validarDadosBasicos(String nome, int nivel) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nivel < 1 || nivel > 20) {
            throw new IllegalArgumentException("Nível deve estar entre 1 e 20");
        }
    }
    
    /**
     * Inicializa perícias disponíveis para a classe
     */
    private void inicializarPericias(Personagem personagem) {
        List<TipoPericia> periciasDisponiveis = 
            personagem.getClasse().getPericiasDisponiveis();
        
        for (TipoPericia tipo : periciasDisponiveis) {
            Pericia pericia = new Pericia(tipo);
            personagem.adicionarPericia(pericia);
        }
    }
    
    /**
     * Aplica poderes da origem ao personagem
     */
    private void aplicarPoderesOrigem(Personagem personagem) {
        Origem origem = personagem.getOrigem();
        if (origem != null) {
            for (Poder poder : origem.getPoderes()) {
                personagem.adicionarPoder(poder);
            }
        }
    }
    
    /**
     * Aplica habilidades de classe baseado no nível
     */
    private void aplicarHabilidadesClasse(Personagem personagem) {
        List<HabilidadeClasse> habilidades = 
            personagem.getClasse().getHabilidades();
        
        for (HabilidadeClasse hab : habilidades) {
            if (hab.getNivelRequerido() <= personagem.getNivel()) {
                personagem.adicionarHabilidade(hab);
            }
        }
    }
    
    /**
     * Calcula todos os bônus do personagem
     */
    public Map<String, Integer> calcularBonusTotal(Personagem personagem) {
        Map<String, Integer> bonus = new HashMap<>();
        
        // Modificadores de atributos
        for (TipoAtributo tipo : TipoAtributo.values()) {
            int mod = personagem.getAtributos().getModificador(tipo);
            bonus.put(tipo.name(), mod);
        }
        
        // Modificador de magia
        bonus.put("MAGIA", personagem.calcularModificadorMagia());
        
        // Modificadores de perícias
        for (Pericia pericia : personagem.getPericias()) {
            int mod = personagem.calcularModificadorPericia(pericia);
            bonus.put("PERICIA_" + pericia.getTipo().name(), mod);
        }
        
        return bonus;
    }
    
    /**
     * Valida se o personagem está completo e válido
     */
    public boolean validarPersonagem(Personagem personagem) {
        if (personagem == null) return false;
        if (personagem.getNome() == null || personagem.getNome().isEmpty()) 
            return false;
        if (personagem.getRaca() == null) return false;
        if (personagem.getClasse() == null) return false;
        if (personagem.getOrigem() == null) return false;
        if (personagem.getNivel() < 1 || personagem.getNivel() > 20) 
            return false;
        
        return true;
    }
    
    /**
     * Treina uma perícia para o personagem
     */
    public void treinarPericia(Personagem personagem, TipoPericia tipo) {
        Pericia pericia = personagem.getPericias().stream()
            .filter(p -> p.getTipo() == tipo)
            .findFirst()
            .orElse(null);
        
        if (pericia != null) {
            pericia.setTreinada(true);
        } else {
            throw new IllegalArgumentException(
                "Personagem não possui essa perícia disponível");
        }
    }
}
