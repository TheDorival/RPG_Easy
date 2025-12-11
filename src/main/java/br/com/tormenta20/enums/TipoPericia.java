package br.com.tormenta20.enums;

public enum TipoPericia {
    ACROBACIA(TipoAtributo.DESTREZA),
    ATLETISMO(TipoAtributo.FORCA),
    FURTIVIDADE(TipoAtributo.DESTREZA),
    PERCEPCAO(TipoAtributo.SABEDORIA),
    CONHECIMENTO(TipoAtributo.INTELIGENCIA),
    DIPLOMACIA(TipoAtributo.CARISMA),
    INTIMIDACAO(TipoAtributo.CARISMA),
    CURA(TipoAtributo.SABEDORIA);
    
    private final TipoAtributo atributoBase;
    
    TipoPericia(TipoAtributo atributoBase) {
        this.atributoBase = atributoBase;
    }
    
    public TipoAtributo getAtributoBase() {
        return atributoBase;
    }
}