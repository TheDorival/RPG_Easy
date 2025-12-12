package br.com.tormenta20.builder;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;

/**
 * Builder para criar personagens de forma fluente e conveniente
 */
public class PersonagemBuilder {
    private Personagem personagem;
    
    public PersonagemBuilder(String nome, int nivel) {
        this.personagem = new Personagem(nome, nivel);
    }
    
    public PersonagemBuilder comRaca(Raca raca) {
        personagem.setRaca(raca);
        return this;
    }
    
    public PersonagemBuilder comClasse(Classe classe) {
        personagem.setClasse(classe);
        return this;
    }
    
    public PersonagemBuilder comOrigem(Origem origem) {
        personagem.setOrigem(origem);
        return this;
    }
    
    public PersonagemBuilder comDivindade(String divindade) {
        personagem.setDivindade(divindade);
        return this;
    }
    
    public PersonagemBuilder comAtributo(TipoAtributo tipo, int valor) {
        personagem.getAtributos().setValor(tipo, valor);
        return this;
    }
    
    public PersonagemBuilder comPericia(Pericia pericia) {
        personagem.adicionarPericia(pericia);
        return this;
    }
    
    public PersonagemBuilder treinarPericia(TipoPericia tipo) {
        personagem.getPericias().stream()
            .filter(p -> p.getTipo() == tipo)
            .findFirst()
            .ifPresent(p -> p.setTreinada(true));
        return this;
    }
    
    public PersonagemBuilder comArma(Arma arma) {
        personagem.setArmaEquipada(arma);
        return this;
    }
    
    public PersonagemBuilder comArmadura(Armadura armadura) {
        personagem.setArmaduraEquipada(armadura);
        return this;
    }
    
    public PersonagemBuilder comMagia(Magia magia) {
        personagem.adicionarMagia(magia);
        return this;
    }
    
    public Personagem construir() {
        // Aplica modificadores finais
        personagem.aplicarModificadoresRaca();
        return personagem;
    }
}