package br.com.tormenta20.factory;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;

public class ArmaFactory {
    
    /**
     * Espada: 1d8 + FORÇA
     */
    public static Arma criarEspada() {
        return new Arma("Espada Longa", TipoArma.ESPADA, 
                       1, 8, TipoAtributo.FORCA);
    }
    
    /**
     * Machado: 1d12 + FORÇA
     */
    public static Arma criarMachado() {
        return new Arma("Machado de Batalha", TipoArma.MACHADO, 
                       1, 12, TipoAtributo.FORCA);
    }
    
    /**
     * Arco Curto: 1d6 + DESTREZA
     */
    public static Arma criarArcoCurto() {
        return new Arma("Arco Curto", TipoArma.ARCO_CURTO, 
                       1, 6, TipoAtributo.DESTREZA);
    }
}