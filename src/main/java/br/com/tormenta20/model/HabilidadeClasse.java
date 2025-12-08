package main.java.br.com.tormenta20.model;


public class HabilidadeClasse extends Habilidade {
    private int nivelRequerido;
    private String nomeClasse;
    
    public HabilidadeClasse(String nome, String descricao, int nivelRequerido, String nomeClasse) {
        super(nome, descricao);
        this.nivelRequerido = nivelRequerido;
        this.nomeClasse = nomeClasse;
    }
    
    @Override
    public void aplicarEfeito(Personagem personagem) {
        // Implementação específica
    }
    
    public int getNivelRequerido() {
        return nivelRequerido;
    }
    
    public String getNomeClasse() {
        return nomeClasse;
    }
}