package br.com.tormenta20.exemplo;

import br.com.tormenta20.model.*;
import br.com.tormenta20.factory.*;
import br.com.tormenta20.builder.*;
import br.com.tormenta20.enums.*;

public class ExemplosCriacao {
    
    /**
     * Cria um Guerreiro Anão
     */
    public static Personagem criarGuerreiroAnao() {
        return new PersonagemBuilder("Gimli", 5)
            .comRaca(RacaFactory.criarAnao())
            .comClasse(ClasseFactory.criarGuerreiro())
            .comAtributo(TipoAtributo.FORCA, 16)
            .comAtributo(TipoAtributo.CONSTITUICAO, 16)
            .comAtributo(TipoAtributo.DESTREZA, 10)
            .comAtributo(TipoAtributo.INTELIGENCIA, 10)
            .comAtributo(TipoAtributo.SABEDORIA, 12)
            .comAtributo(TipoAtributo.CARISMA, 8)
            .comArma(ArmaFactory.criarMachado())
            .comArmadura(ArmaduraFactory.criarCotaMalha())
            .treinarPericia(TipoPericia.LUTA)
            .treinarPericia(TipoPericia.ATLETISMO)
            .construir();
    }
    
    /**
     * Cria um Arcanista Elfo
     */
    public static Personagem criarArcanistaElfo() {
        Personagem gandalf = new PersonagemBuilder("Gandalf", 10)
            .comRaca(RacaFactory.criarElfo())
            .comClasse(ClasseFactory.criarArcanista())
            .comAtributo(TipoAtributo.INTELIGENCIA, 18)
            .comAtributo(TipoAtributo.DESTREZA, 14)
            .comAtributo(TipoAtributo.CONSTITUICAO, 12)
            .comAtributo(TipoAtributo.SABEDORIA, 14)
            .comAtributo(TipoAtributo.FORCA, 8)
            .comAtributo(TipoAtributo.CARISMA, 10)
            .treinarPericia(TipoPericia.CONHECIMENTO)
            .construir();
        
        // Adicionar magias iniciais
        gandalf.adicionarMagia(new Magia("Bola de Fogo", 
            "Explosão flamejante", 3, "Evocação"));
        gandalf.adicionarMagia(new Magia("Escudo Arcano", 
            "+5 na Defesa", 1, "Abjuração"));
        
        return gandalf;
    }
    
    /**
     * Cria um Bucaneiro Humano
     */
    public static Personagem criarBucaneiroHumano() {
        return new PersonagemBuilder("Jack Sparrow", 7)
            .comRaca(RacaFactory.criarHumano())
            .comClasse(ClasseFactory.criarBucaneiro())
            .comAtributo(TipoAtributo.DESTREZA, 16)
            .comAtributo(TipoAtributo.CARISMA, 16)
            .comAtributo(TipoAtributo.INTELIGENCIA, 12)
            .comAtributo(TipoAtributo.CONSTITUICAO, 12)
            .comAtributo(TipoAtributo.SABEDORIA, 10)
            .comAtributo(TipoAtributo.FORCA, 10)
            .comArma(ArmaFactory.criarEspada())
            .comArmadura(ArmaduraFactory.criarCouro())
            .treinarPericia(TipoPericia.LUTA)
            .treinarPericia(TipoPericia.ACROBACIA)
            .treinarPericia(TipoPericia.DIPLOMACIA)
            .construir();
    }
    
    /**
     * Cria um Guerreiro Goblin
     */
    public static Personagem criarGuerreiroGoblin() {
        return new PersonagemBuilder("Nix", 3)
            .comRaca(RacaFactory.criarGoblin())
            .comClasse(ClasseFactory.criarGuerreiro())
            .comAtributo(TipoAtributo.DESTREZA, 16)
            .comAtributo(TipoAtributo.INTELIGENCIA, 12)
            .comAtributo(TipoAtributo.CONSTITUICAO, 14)
            .comAtributo(TipoAtributo.FORCA, 10)
            .comAtributo(TipoAtributo.SABEDORIA, 10)
            .comAtributo(TipoAtributo.CARISMA, 8)
            .comArma(ArmaFactory.criarArcoCurto())
            .comArmadura(ArmaduraFactory.criarCouroBatido())
            .treinarPericia(TipoPericia.LUTA)
            .treinarPericia(TipoPericia.FURTIVIDADE)
            .construir();
    }
}