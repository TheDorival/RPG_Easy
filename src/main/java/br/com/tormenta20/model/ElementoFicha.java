package br.com.tormenta20.model;

import br.com.tormenta20.interfaces.IDescritivel;

public abstract class ElementoFicha implements IDescritivel {
    protected String nome;
    protected String descricao;
    
    public ElementoFicha(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }
    
    @Override
    public String getDescricao() {
        return descricao;
    }
}