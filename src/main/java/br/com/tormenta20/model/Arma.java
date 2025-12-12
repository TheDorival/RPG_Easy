package br.com.tormenta20.model;

public class Arma {
    private String nome;
    private TipoArma tipo;
    private int quantidadeDados;
    private int ladosDado;
    private TipoAtributo atributoModificador;
    
    public Arma(String nome, TipoArma tipo, int quantidadeDados, 
                int ladosDado, TipoAtributo atributoModificador) {
        this.nome = nome;
        this.tipo = tipo;
        this.quantidadeDados = quantidadeDados;
        this.ladosDado = ladosDado;
        this.atributoModificador = atributoModificador;
    }
    
    public int rolarDano(int modificadorAtributo) {
        int dano = 0;
        for (int i = 0; i < quantidadeDados; i++) {
            dano += (int)(Math.random() * ladosDado) + 1;
        }
        return dano + modificadorAtributo;
    }
    
    // Getters
    public String getNome() { return nome; }
    public TipoArma getTipo() { return tipo; }
    public int getQuantidadeDados() { return quantidadeDados; }
    public int getLadosDado() { return ladosDado; }
    public TipoAtributo getAtributoModificador() { return atributoModificador; }
    
    @Override
    public String toString() {
        return String.format("%s (%dd%d + %s)", nome, quantidadeDados, 
                           ladosDado, atributoModificador);
    }
}