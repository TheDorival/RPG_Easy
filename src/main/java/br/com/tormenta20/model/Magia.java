package br.com.tormenta20.model;


public class Magia extends Habilidade {
    private int nivel;
    private String escola;
    
    public Magia(String nome, String descricao, int nivel, String escola) {
        super(nome, descricao);
        this.nivel = nivel;
        this.escola = escola;
    }
    
    @Override
    public void aplicarEfeito(Personagem personagem) {
        // Implementação do efeito da magia
    }
    
    public int getNivel() {
        return nivel;
    }
    
    public String getEscola() {
        return escola;
    }
    
    public void setTempoConjuracao(String tempoConjuracao) {
    }
    
    public void setAlcance(String alcance) {
    }
}