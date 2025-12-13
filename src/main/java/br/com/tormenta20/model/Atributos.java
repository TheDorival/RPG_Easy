package br.com.tormenta20.model;

import br.com.tormenta20.enums.*;
import br.com.tormenta20.interfaces.IModificavel;
import java.util.*;

public class Atributos implements IModificavel {
    private Map<TipoAtributo, Integer> valores;
    
    // ERRO PROPOSITAL
    public Atributos() {
        valores = new HashMap<>();
        // Inicializa com valores padrão 10
        for (TipoAtributo tipo : TipoAtributo.values()) {
            valores.put(tipo, 10);
        }
    }
    
    public void setValor(TipoAtributo tipo, int valor) {
        valores.put(tipo, valor);
    }
    
    public int getValor(TipoAtributo tipo) {
        return valores.get(tipo);
    }
    
    public int getModificador(TipoAtributo tipo) {
        int valor = valores.get(tipo);
        return (valor - 10) / 2;
    }
    
    @Override
    public int getModificador() {
        return valores.values().stream()
            .mapToInt(v -> (v - 10) / 2)
            .sum() / valores.size();
    }
    
    public void aplicarModificadores(Map<TipoAtributo, Integer> modificadores) {
        modificadores.forEach((tipo, mod) -> 
            valores.put(tipo, valores.get(tipo) + mod)
        );
    }
    
    public int getMaiorModFisico() {
        int modForca = getModificador(TipoAtributo.FORCA);
        int modDestreza = getModificador(TipoAtributo.DESTREZA);
        return Math.max(modForca, modDestreza);
    }
}