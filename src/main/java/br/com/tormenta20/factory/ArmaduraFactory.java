package br.com.tormenta20.factory;

import br.com.tormenta20.model.*;
import br.com.tormenta20.enums.*;

public class ArmaduraFactory {
    
    /**
     * Armadura de Couro: +2 Defesa
     */
    public static Armadura criarCouro() {
        return new Armadura("Armadura de Couro", TipoArmadura.COURO, 
                           2, false);
    }
    
    /**
     * Couro Batido: +3 Defesa
     */
    public static Armadura criarCouroBatido() {
        return new Armadura("Couro Batido", TipoArmadura.COURO_BATIDO, 
                           3, false);
    }
    
    /**
     * Cota de Malha: +6 Defesa (Armadura Pesada)
     */
    public static Armadura criarCotaMalha() {
        return new Armadura("Cota de Malha", TipoArmadura.COTA_MALHA, 
                           6, true);
    }
}