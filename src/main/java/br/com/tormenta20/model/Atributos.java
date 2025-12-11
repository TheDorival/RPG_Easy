package br.com.tormenta20.model;

import java.util.*;
import br.com.tormenta20.enums.*;

public class Atributos implements IModificavel {
    private Map<TipoAtributo, Integer> valores;
    
    public Atributos() {
        valores = new HashMap<>();
        // Inicializa com valores padrão 10 (normal para humanos)
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
        // Retorna média dos modificadores
        return valores.values().stream()
            .mapToInt(v -> (v - 10) / 2)
            .sum() / valores.size();
    }
    
    public void aplicarModificadores(Map<TipoAtributo, Integer> modificadores) {
        modificadores.forEach((tipo, mod) -> 
            valores.put(tipo, valores.get(tipo) + mod)
        );
    }
}