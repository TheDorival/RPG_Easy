package br.com.tormenta20.model;

import java.util.*;

import br.com.tormenta20.enums.*;
import br.com.tormenta20.model.*;

public class Personagem {
    private String nome;
    private int nivel;
    private Atributos atributos;
    private Raca raca;
    private Classe classe;
    private Origem origem;
    private String divindade;
    private List<Pericia> pericias;
    private List<Poder> poderes;
    private List<Magia> magias;
    private List<HabilidadeClasse> habilidades;
    
    public Personagem(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
        this.atributos = new Atributos();
        this.pericias = new ArrayList<>();
        this.poderes = new ArrayList<>();
        this.magias = new ArrayList<>();
        this.habilidades = new ArrayList<>();
    }
    
    public int calcularModificadorPericia(Pericia pericia) {
        TipoAtributo atributoBase = pericia.getTipo().getAtributoBase();
        int modAtributo = atributos.getModificador(atributoBase);
        return pericia.calcularValor(nivel, modAtributo);
    }
    
    public int calcularModificadorMagia() {
        // Mod de magia = 10 + 1/2 nível + mod. atributo-chave
        TipoAtributo atributoChave = classe.getAtributoChave();
        int modAtributo = atributos.getModificador(atributoChave);
        return 10 + (nivel / 2) + modAtributo;
    }
    
    public int rolarPericia(TipoPericia tipoPericia) {
        Pericia pericia = pericias.stream()
            .filter(p -> p.getTipo() == tipoPericia)
            .findFirst()
            .orElse(new Pericia(tipoPericia));
        
        return pericia.rolarPericia(nivel, 
            atributos.getModificador(tipoPericia.getAtributoBase()));
    }
    
    public void aplicarModificadoresRaca() {
        if (raca != null) {
            atributos.aplicarModificadores(raca.getModificadoresAtributo());
        }
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    
    public Atributos getAtributos() { return atributos; }
    
    public Raca getRaca() { return raca; }
    public void setRaca(Raca raca) { 
        this.raca = raca;
        aplicarModificadoresRaca();
    }
    
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
    
    public Origem getOrigem() { return origem; }
    public void setOrigem(Origem origem) { this.origem = origem; }
    
    public String getDivindade() { return divindade; }
    public void setDivindade(String divindade) { this.divindade = divindade; }
    
    public void adicionarPericia(Pericia pericia) { pericias.add(pericia); }
    public List<Pericia> getPericias() { return new ArrayList<>(pericias); }
    
    public void adicionarPoder(Poder poder) { poderes.add(poder); }
    public List<Poder> getPoderes() { return new ArrayList<>(poderes); }
    
    public void adicionarMagia(Magia magia) { magias.add(magia); }
    public List<Magia> getMagias() { return new ArrayList<>(magias); }
    
    public void adicionarHabilidade(HabilidadeClasse hab) { habilidades.add(hab); }
    public List<HabilidadeClasse> getHabilidades() { return new ArrayList<>(habilidades); }
}