package br.com.tormenta20.model;

import java.util.*;
import br.com.tormenta20.enums.*;

public class Raca extends ElementoFicha {
    private Map<TipoAtributo, Integer> modificadoresAtributo;
    private List<String> caracteristicas;
    private String tamanho;
    private int deslocamento;
    private int bonusPVPorNivel;
    private int bonusPMPorNivel;
    private boolean visaoEscuro;
    
    public Raca(String nome, String descricao) {
        super(nome, descricao);
        this.modificadoresAtributo = new HashMap<>();
        this.caracteristicas = new ArrayList<>();
        this.bonusPVPorNivel = 0;
        this.bonusPMPorNivel = 0;
        this.visaoEscuro = false;
    }
    
    public void adicionarModificador(TipoAtributo atributo, int valor) {
        modificadoresAtributo.put(atributo, valor);
    }
    
    public void adicionarCaracteristica(String caracteristica) {
        caracteristicas.add(caracteristica);
    }
    
    public Map<TipoAtributo, Integer> getModificadoresAtributo() {
        return new HashMap<>(modificadoresAtributo);
    }
    
    public List<String> getCaracteristicas() {
        return new ArrayList<>(caracteristicas);
    }
    
    // Getters e Setters
    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }
    
    public int getDeslocamento() { return deslocamento; }
    public void setDeslocamento(int deslocamento) { this.deslocamento = deslocamento; }
    
    public int getBonusPVPorNivel() { return bonusPVPorNivel; }
    public void setBonusPVPorNivel(int bonus) { this.bonusPVPorNivel = bonus; }
    
    public int getBonusPMPorNivel() { return bonusPMPorNivel; }
    public void setBonusPMPorNivel(int bonus) { this.bonusPMPorNivel = bonus; }
    
    public boolean isVisaoEscuro() { return visaoEscuro; }
    public void setVisaoEscuro(boolean visaoEscuro) { this.visaoEscuro = visaoEscuro; }
}