package br.com.tormenta20.model;

import java.util.Random;
import br.com.tormenta20.enums.*;

public class Pericia extends Habilidade implements IRolavel {
    private TipoPericia tipo;
    private boolean treinada;
    
    public Pericia(TipoPericia tipo) {
        super(tipo.name(), "Perícia de " + tipo.name());
        this.tipo = tipo;
        this.treinada = false;
    }
    
    public int calcularValor(int nivel, int modAtributo) {
        // Perícia = metade do nível + modificador
        int valorBase = nivel / 2 + modAtributo;
        return treinada ? valorBase + 2 : valorBase; // +2 se treinada
    }
    
    @Override
    public int rolarDado(int tipoDado) {
        return new Random().nextInt(tipoDado) + 1;
    }
    
    public int rolarPericia(int nivel, int modAtributo) {
        return rolarDado(20) + calcularValor(nivel, modAtributo);
    }
    
    @Override
    public void aplicarEfeito(Personagem personagem) {
        // Implementação específica
    }
    
    public TipoPericia getTipo() {
        return tipo;
    }
    
    public void setTreinada(boolean treinada) {
        this.treinada = treinada;
    }
    
    public boolean isTreinada() {
        return treinada;
    }
}