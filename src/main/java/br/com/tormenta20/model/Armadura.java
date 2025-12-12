package br.com.tormenta20.model;

import br.com.tormenta20.enums.*;

public class Armadura {
    private String nome;
    private TipoArmadura tipo;
    private int bonusDefesa;
    private boolean armaduraPesada;
    
    public Armadura(String nome, TipoArmadura tipo, int bonusDefesa, 
                    boolean armaduraPesada) {
        this.nome = nome;
        this.tipo = tipo;
        this.bonusDefesa = bonusDefesa;
        this.armaduraPesada = armaduraPesada;
    }
    
    // Getters
    public String getNome() { return nome; }
    public TipoArmadura getTipo() { return tipo; }
    public int getBonusDefesa() { return bonusDefesa; }
    public boolean isArmaduraPesada() { return armaduraPesada; }
}
