package br.com.tormenta20.model;

public class Poder extends Habilidade {
    private String origem;
    private String prerequisitos;
    
    public Poder(String nome, String descricao, String origem) {
        super(nome, descricao);
        this.origem = origem;
    }
    
    @Override
    public void aplicarEfeito(Personagem personagem) {
        // Implementação do efeito do poder
    }
    
    public String getOrigem() {
        return origem;
    }
    
    public String getPrerequisitos() {
        return prerequisitos;
    }
    
    public void setPrerequisitos(String prerequisitos) {
        this.prerequisitos = prerequisitos;
    }
}