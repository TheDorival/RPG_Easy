package br.com.tormenta20.enums;


public enum TipoPericia {
    ACROBACIA(TipoAtributo.DESTREZA),
    ATLETISMO(TipoAtributo.FORCA),
    FURTIVIDADE(TipoAtributo.DESTREZA),
    PERCEPCAO(TipoAtributo.SABEDORIA),
    CONHECIMENTO(TipoAtributo.INTELIGENCIA),
    DIPLOMACIA(TipoAtributo.CARISMA),
    INTIMIDACAO(TipoAtributo.CARISMA),
    CURA(TipoAtributo.SABEDORIA),
    LUTA(null); // LUTA usa o maior mod entre FORÇA e DESTREZA
    
    private final TipoAtributo atributoBase;
    
    TipoPericia(TipoAtributo atributoBase) {
        this.atributoBase = atributoBase;
    }
    
    public TipoAtributo getAtributoBase() {
        return atributoBase;
    }
}