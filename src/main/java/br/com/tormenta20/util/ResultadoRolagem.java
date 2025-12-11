package br.com.tormenta20.util;

public class ResultadoRolagem {
    private int valorDado;
    private int modificador;
    private int total;
    private boolean critico;
    
    public ResultadoRolagem(int valorDado, int modificador) {
        this.valorDado = valorDado;
        this.modificador = modificador;
        this.total = valorDado + modificador;
        this.critico = (valorDado == 20);
    }
    
    public int getValorDado() { return valorDado; }
    public int getModificador() { return modificador; }
    public int getTotal() { return total; }
    public boolean isCritico() { return critico; }
    
    @Override
    public String toString() {
        return String.format("D20: %d + Mod: %d = %d%s", 
            valorDado, modificador, total, 
            critico ? " [CRÍTICO!]" : "");
    }
}