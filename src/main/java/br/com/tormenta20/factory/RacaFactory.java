package br.com.tormenta20.factory;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;

public class RacaFactory {
    
    /**
     * Humano: +1 em Três Atributos Diferentes
     * Versátil: Treinado em duas perícias à escolha
     */
    public static Raca criarHumano() {
        Raca humano = new Raca("Humano", 
            "Raça versátil e adaptável. Os humanos são ambiciosos e determinados.");
        
        // Nota: Os +1 em três atributos devem ser escolhidos pelo jogador
        // Aqui está a implementação padrão
        humano.adicionarCaracteristica("Versátil: Treinado em duas perícias à escolha");
        humano.adicionarCaracteristica("+1 em três atributos diferentes (a escolher)");
        humano.setTamanho("Médio");
        humano.setDeslocamento(9);
        
        return humano;
    }
    
    /**
     * Anão: Constituição +2, Sabedoria +1, Destreza –1
     * Duro como Pedra: +1 HP por nível
     * Tradição Anã: Machados/martelos são armas simples, +2 em ataques
     * Visão no Escuro
     */
    public static Raca criarAnao() {
        Raca anao = new Raca("Anão", 
            "Povo resistente das montanhas, mestres da forja e do combate.");
        
        anao.adicionarModificador(TipoAtributo.CONSTITUICAO, 2);
        anao.adicionarModificador(TipoAtributo.SABEDORIA, 1);
        anao.adicionarModificador(TipoAtributo.DESTREZA, -1);
        
        anao.setBonusPVPorNivel(1); // Duro como Pedra
        anao.setVisaoEscuro(true);
        
        anao.adicionarCaracteristica("Duro como Pedra: +1 PV por nível");
        anao.adicionarCaracteristica("Tradição Anã: Machados, martelos, marretas e picaretas são armas simples. +2 em ataques com essas armas");
        anao.adicionarCaracteristica("Visão no Escuro: Enxerga no escuro a até 12 metros");
        anao.setTamanho("Médio");
        anao.setDeslocamento(6);
        
        return anao;
    }
    
    /**
     * Elfo: Inteligência +2, Destreza +1, Constituição –1
     * Sangue Mágico: +1 PM por nível
     */
    public static Raca criarElfo() {
        Raca elfo = new Raca("Elfo", 
            "Seres longevos e graciosos, com afinidade natural com a magia.");
        
        elfo.adicionarModificador(TipoAtributo.INTELIGENCIA, 2);
        elfo.adicionarModificador(TipoAtributo.DESTREZA, 1);
        elfo.adicionarModificador(TipoAtributo.CONSTITUICAO, -1);
        
        elfo.setBonusPMPorNivel(1); // Sangue Mágico
        
        elfo.adicionarCaracteristica("Sangue Mágico: +1 PM por nível");
        elfo.adicionarCaracteristica("Visão na Penumbra: Enxerga com pouca luz");
        elfo.adicionarCaracteristica("Sentidos Aguçados: +2 em testes de Percepção");
        elfo.setTamanho("Médio");
        elfo.setDeslocamento(9);
        
        return elfo;
    }
    
    /**
     * Goblin: Destreza +2, Inteligência +1, Carisma –1
     * Engenhosidade: Sem penalidades por falta de ferramentas
     * Visão no Escuro
     */
    public static Raca criarGoblin() {
        Raca goblin = new Raca("Goblin", 
            "Criaturas pequenas e engenhosas, sobreviventes natos.");
        
        goblin.adicionarModificador(TipoAtributo.DESTREZA, 2);
        goblin.adicionarModificador(TipoAtributo.INTELIGENCIA, 1);
        goblin.adicionarModificador(TipoAtributo.CARISMA, -1);
        
        goblin.setVisaoEscuro(true);
        
        goblin.adicionarCaracteristica("Engenhosidade: Não sofre penalidades por não usar ferramentas. Com ferramenta adequada, +2 no teste");
        goblin.adicionarCaracteristica("Visão no Escuro: Enxerga no escuro a até 12 metros");
        goblin.setTamanho("Pequeno");
        goblin.setDeslocamento(6);
        
        return goblin;
    }
}
