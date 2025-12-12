package br.com.tormenta20.service;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.util.RoladorDados;

/**
 * Serviço para gerenciar magias e conjuração
 */
public class MagiaService {
    
    /**
     * Verifica se um personagem pode conjurar uma magia
     */
    public boolean podeConjurar(Personagem personagem, Magia magia) {
        // Verifica se tem PM suficiente
        int custoMagia = calcularCustoMagia(magia.getNivel());
        if (personagem.getPontosMana() < custoMagia) {
            return false;
        }
        
        // Verifica se conhece a magia
        return personagem.getMagias().contains(magia);
    }
    
    /**
     * Calcula o custo em PM de uma magia baseado no nível
     */
    public int calcularCustoMagia(int nivelMagia) {
        return nivelMagia;
    }
    
    /**
     * Calcula o modificador de magia do personagem
     */
    public int calcularModificadorMagia(Personagem personagem) {
        return personagem.calcularModificadorMagia();
    }
    
    /**
     * Realiza teste de resistência contra magia
     */
    public boolean testeResistencia(Personagem alvo, int dificuldade, 
                                   TipoAtributo atributoTeste) {
        int modificador = alvo.getAtributos().getModificador(atributoTeste);
        int nivelBonus = alvo.getNivel() / 2;
        int rolagem = RoladorDados.rolarD20() + modificador + nivelBonus;
        
        return rolagem >= dificuldade;
    }
    
    /**
     * Calcula CD (Classe de Dificuldade) de uma magia
     */
    public int calcularCDMagia(Personagem conjurador) {
        return conjurador.calcularModificadorMagia();
    }
    
    /**
     * Adiciona uma nova magia ao grimório do personagem
     */
    public void aprenderMagia(Personagem personagem, Magia magia) {
        if (!personagem.getMagias().contains(magia)) {
            personagem.adicionarMagia(magia);
        }
    }
    
    /**
     * Remove uma magia do personagem
     */
    public void esquecerMagia(Personagem personagem, Magia magia) {
        personagem.getMagias().remove(magia);
    }
}