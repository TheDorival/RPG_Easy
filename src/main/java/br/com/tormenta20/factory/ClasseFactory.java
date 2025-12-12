package br.com.tormenta20.factory;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;

public class ClasseFactory {
    
    /**
     * Guerreiro
     * PV = (10+CON) x Nível
     * PM = 3 x Nível
     * Ataque Especial: Gasta PM para bônus em ataque/dano
     */
    public static Classe criarGuerreiro() {
        Classe guerreiro = new Classe("Guerreiro", 
            "Mestre do combate corpo a corpo, especialista em armas e táticas de batalha.",
            TipoAtributo.FORCA,
            10, // PV base por nível
            3   // PM base por nível
        );
        
        guerreiro.adicionarPericiaDisponivel(TipoPericia.ATLETISMO);
        guerreiro.adicionarPericiaDisponivel(TipoPericia.INTIMIDACAO);
        guerreiro.adicionarPericiaDisponivel(TipoPericia.PERCEPCAO);
        guerreiro.adicionarPericiaDisponivel(TipoPericia.LUTA);
        
        // Habilidade de Nível 1
        HabilidadeClasse ataqueEspecial = new HabilidadeClasse(
            "Ataque Especial",
            "Quando faz um ataque, você pode gastar 1 PM para receber +4 no teste de ataque ou na rolagem de dano. " +
            "A cada quatro níveis, pode gastar +1 PM para aumentar o bônus em +4. " +
            "Você pode dividir os bônus igualmente. Por exemplo, no 17º nível, pode gastar 5 PM para receber " +
            "+20 no ataque, +20 no dano ou +10 no ataque e +10 no dano.",
            1,
            "Guerreiro"
        );
        guerreiro.adicionarHabilidade(ataqueEspecial);
        
        // Poder a cada nível
        HabilidadeClasse poderNivel = new HabilidadeClasse(
            "Poder de Guerreiro",
            "A cada nível, você ganha um poder de guerreiro à sua escolha.",
            1,
            "Guerreiro"
        );
        guerreiro.adicionarHabilidade(poderNivel);
        
        return guerreiro;
    }
    
    /**
     * Arcanista (Mago)
     * PV = (6+CON) x Nível
     * PM = 6 x Nível
     * Mago: Memoriza magias arcanas
     */
    public static Classe criarArcanista() {
        Classe arcanista = new Classe("Arcanista", 
            "Conjurador de magias arcanas, estudioso das artes místicas e fórmulas mágicas.",
            TipoAtributo.INTELIGENCIA,
            6,  // PV base por nível
            6   // PM base por nível
        );
        
        arcanista.adicionarPericiaDisponivel(TipoPericia.CONHECIMENTO);
        arcanista.adicionarPericiaDisponivel(TipoPericia.PERCEPCAO);
        
        // Habilidade de Nível 1
        HabilidadeClasse mago = new HabilidadeClasse(
            "Mago",
            "Você memoriza fórmulas arcanas para conjurar suas magias. " +
            "Você aprende duas magias por nível. " +
            "Você começa com 3 magias de 1º nível.",
            1,
            "Arcanista"
        );
        arcanista.adicionarHabilidade(mago);
        
        // Poder a cada nível
        HabilidadeClasse poderNivel = new HabilidadeClasse(
            "Poder de Arcanista",
            "A cada nível, você ganha um poder de arcanista à sua escolha.",
            1,
            "Arcanista"
        );
        arcanista.adicionarHabilidade(poderNivel);
        
        return arcanista;
    }
    
    /**
     * Bucaneiro
     * PV = (8+CON) x Nível
     * PM = 4 x Nível
     * Audácia, Insolência, Evasão
     */
    public static Classe criarBucaneiro() {
        Classe bucaneiro = new Classe("Bucaneiro", 
            "Aventureiro ousado e versátil, que confia em sua sorte e carisma.",
            TipoAtributo.CARISMA,
            8,  // PV base por nível
            4   // PM base por nível
        );
        
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.ACROBACIA);
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.ATLETISMO);
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.FURTIVIDADE);
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.DIPLOMACIA);
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.INTIMIDACAO);
        bucaneiro.adicionarPericiaDisponivel(TipoPericia.LUTA);
        
        // Habilidade Nível 1: Audácia
        HabilidadeClasse audacia = new HabilidadeClasse(
            "Audácia",
            "Quando faz um teste de perícia, você pode gastar 2 PM para somar seu Carisma no teste. " +
            "Você não pode usar esta habilidade em testes de ataque.",
            1,
            "Bucaneiro"
        );
        bucaneiro.adicionarHabilidade(audacia);
        
        // Habilidade Nível 1: Insolência
        HabilidadeClasse insolencia = new HabilidadeClasse(
            "Insolência",
            "Você soma seu Carisma na Defesa, limitado pelo seu nível. " +
            "Esta habilidade exige liberdade de movimentos; você não pode usá-la se estiver " +
            "de armadura pesada ou na condição imóvel.",
            1,
            "Bucaneiro"
        );
        bucaneiro.adicionarHabilidade(insolencia);
        
        // Habilidade Nível 2: Evasão
        HabilidadeClasse evasao = new HabilidadeClasse(
            "Evasão",
            "A partir do 2º nível, quando sofre um efeito que permite um teste de Reflexos " +
            "para reduzir o dano à metade, você não sofre dano algum se passar. " +
            "Você ainda sofre dano normal se falhar no teste de Reflexos.",
            2,
            "Bucaneiro"
        );
        bucaneiro.adicionarHabilidade(evasao);
        
        // Poder a cada nível
        HabilidadeClasse poderNivel = new HabilidadeClasse(
            "Poder de Bucaneiro",
            "A cada nível, você ganha um poder de bucaneiro à sua escolha.",
            1,
            "Bucaneiro"
        );
        bucaneiro.adicionarHabilidade(poderNivel);
        
        return bucaneiro;
    }
}