package br.com.tormenta20.service;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.util.RoladorDados;
import br.com.tormenta20.util.ResultadoRolagem;

/**
 * Serviço para gerenciar combates entre personagens
 */
public class CombateService {
    
    /**
     * Realiza um ataque de um personagem contra outro
     */
    public ResultadoCombate realizarAtaque(Personagem atacante, Personagem defensor) {
        if (atacante.getArmaEquipada() == null) {
            throw new IllegalStateException("Atacante não possui arma equipada");
        }
        
        // Rola ataque
        int rolagemAtaque = atacante.rolarAtaque();
        int defesa = defensor.getDefesa();
        
        boolean acertou = rolagemAtaque >= defesa;
        int dano = 0;
        
        if (acertou) {
            dano = atacante.rolarDano();
        }
        
        return new ResultadoCombate(atacante.getNome(), defensor.getNome(), 
                                   rolagemAtaque, defesa, acertou, dano);
    }
    
    /**
     * Calcula iniciativa para combate
     */
    public int calcularIniciativa(Personagem personagem) {
        int modIniciativa = personagem.getAtributos()
            .getModificador(TipoAtributo.DESTREZA);
        return RoladorDados.rolarD20() + modIniciativa;
    }
    
    /**
     * Verifica se um ataque foi crítico (natural 20)
     */
    public boolean isCritico(int rolagemNatural) {
        return rolagemNatural == 20;
    }
    
    /**
     * Calcula dano crítico (geralmente 2x o dano)
     */
    public int calcularDanoCritico(int danoNormal) {
        return danoNormal * 2;
    }
}

/**
 * Classe para armazenar resultado de combate
 */
class ResultadoCombate {
    private String atacante;
    private String defensor;
    private int rolagemAtaque;
    private int defesa;
    private boolean acertou;
    private int dano;
    
    public ResultadoCombate(String atacante, String defensor, int rolagemAtaque,
                          int defesa, boolean acertou, int dano) {
        this.atacante = atacante;
        this.defensor = defensor;
        this.rolagemAtaque = rolagemAtaque;
        this.defesa = defesa;
        this.acertou = acertou;
        this.dano = dano;
    }
    
    @Override
    public String toString() {
        if (acertou) {
            return String.format("%s ataca %s! Rolagem: %d vs Defesa: %d - ACERTOU! Dano: %d",
                atacante, defensor, rolagemAtaque, defesa, dano);
        } else {
            return String.format("%s ataca %s! Rolagem: %d vs Defesa: %d - ERROU!",
                atacante, defensor, rolagemAtaque, defesa);
        }
    }
    
    // Getters
    public boolean isAcertou() { return acertou; }
    public int getDano() { return dano; }
}