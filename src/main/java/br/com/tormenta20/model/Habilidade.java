package br.com.tormenta20.model;

import br.com.tormenta20.interfaces.IDescritivel;

public abstract class Habilidade implements IDescritivel {
    protected String nome;
    protected String descricao;
    
    public Habilidade(String nome, String descricao) {
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
    
    public abstract void aplicarEfeito(Personagem personagem);
}