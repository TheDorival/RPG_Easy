package br.com.tormenta20.model;

import br.com.tormenta20.enums.*;
import java.util.*;

public class Personagem {
    private Integer id; // Para o banco de dados
    private String nome;
    private int nivel;
    private Atributos atributos;
    private Raca raca;
    private Classe classe;
    private Origem origem;
    private String divindade;
    private int pontosVida;
    private int pontosMana;
    private int defesa;
    private List<Pericia> pericias;
    private List<Poder> poderes;
    private List<Magia> magias;
    private List<HabilidadeClasse> habilidades;
    private Arma armaEquipada;
    private Armadura armaduraEquipada;
    
    public Personagem(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
        this.atributos = new Atributos();
        this.pericias = new ArrayList<>();
        this.poderes = new ArrayList<>();
        this.magias = new ArrayList<>();
        this.habilidades = new ArrayList<>();
    }
    
    public void calcularPVePM() {
        if (classe == null || raca == null) return;
        
        int modCon = atributos.getModificador(TipoAtributo.CONSTITUICAO);
        this.pontosVida = classe.calcularPV(nivel, modCon, 
                                           raca.getBonusPVPorNivel());
        this.pontosMana = classe.calcularPM(nivel, raca.getBonusPMPorNivel());
    }
    
    public void calcularDefesa() {
        int modDex = atributos.getModificador(TipoAtributo.DESTREZA);
        int bonusArmadura = armaduraEquipada != null ? 
                           armaduraEquipada.getBonusDefesa() : 0;
        
        // Defesa base = 10 + DEX + armadura
        this.defesa = 10 + modDex + bonusArmadura;
        
        // Bucaneiro: adiciona Carisma (limitado pelo nível)
        // Isso seria implementado através de uma HabilidadeClasse específica
    }
    
    public int calcularModificadorPericia(Pericia pericia) {
        TipoAtributo atributoBase = pericia.getTipo().getAtributoBase();
        
        // LUTA usa o maior mod entre FORÇA e DESTREZA
        int modAtributo;
        if (pericia.getTipo() == TipoPericia.LUTA) {
            modAtributo = atributos.getMaiorModFisico();
        } else {
            modAtributo = atributos.getModificador(atributoBase);
        }
        
        return pericia.calcularValor(nivel, modAtributo);
    }
    
    public int calcularModificadorMagia() {
        TipoAtributo atributoChave = classe.getAtributoChave();
        int modAtributo = atributos.getModificador(atributoChave);
        return 10 + (nivel / 2) + modAtributo;
    }
    
    public int rolarPericia(TipoPericia tipoPericia) {
        Pericia pericia = pericias.stream()
            .filter(p -> p.getTipo() == tipoPericia)
            .findFirst()
            .orElse(new Pericia(tipoPericia));
        
        TipoAtributo atributoBase = tipoPericia.getAtributoBase();
        int modAtributo;
        
        if (tipoPericia == TipoPericia.LUTA) {
            modAtributo = atributos.getMaiorModFisico();
        } else {
            modAtributo = atributos.getModificador(atributoBase);
        }
        
        return pericia.rolarPericia(nivel, modAtributo);
    }
    
    public int rolarAtaque() {
        if (armaEquipada == null) return 0;
        
        // Rolagem de ataque = d20 + mod LUTA + metade do nível
        int modLuta = calcularModificadorPericia(
            pericias.stream()
                .filter(p -> p.getTipo() == TipoPericia.LUTA)
                .findFirst()
                .orElse(new Pericia(TipoPericia.LUTA))
        );
        
        return (int)(Math.random() * 20) + 1 + modLuta;
    }
    
    public int rolarDano() {
        if (armaEquipada == null) return 0;
        
        TipoAtributo atributo = armaEquipada.getAtributoModificador();
        int modificador = atributos.getModificador(atributo);
        
        return armaEquipada.rolarDano(modificador);
    }
    
    public void aplicarModificadoresRaca() {
        if (raca != null) {
            atributos.aplicarModificadores(raca.getModificadoresAtributo());
            calcularPVePM();
            calcularDefesa();
        }
    }
    
    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { 
        this.nivel = nivel;
        calcularPVePM();
    }
    
    public Atributos getAtributos() { return atributos; }
    
    public Raca getRaca() { return raca; }
    public void setRaca(Raca raca) { 
        this.raca = raca;
        aplicarModificadoresRaca();
    }
    
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { 
        this.classe = classe;
        calcularPVePM();
    }
    
    public Origem getOrigem() { return origem; }
    public void setOrigem(Origem origem) { this.origem = origem; }
    
    public String getDivindade() { return divindade; }
    public void setDivindade(String divindade) { this.divindade = divindade; }
    
    public int getPontosVida() { return pontosVida; }
    public int getPontosMana() { return pontosMana; }
    public int getDefesa() { return defesa; }
    
    public Arma getArmaEquipada() { return armaEquipada; }
    public void setArmaEquipada(Arma arma) { 
        this.armaEquipada = arma;
    }
    
    public Armadura getArmaduraEquipada() { return armaduraEquipada; }
    public void setArmaduraEquipada(Armadura armadura) { 
        this.armaduraEquipada = armadura;
        calcularDefesa();
    }
    
    public void adicionarPericia(Pericia pericia) { pericias.add(pericia); }
    public List<Pericia> getPericias() { return new ArrayList<>(pericias); }
    
    public void adicionarPoder(Poder poder) { poderes.add(poder); }
    public List<Poder> getPoderes() { return new ArrayList<>(poderes); }
    
    public void adicionarMagia(Magia magia) { magias.add(magia); }
    public List<Magia> getMagias() { return new ArrayList<>(magias); }
    
    public void adicionarHabilidade(HabilidadeClasse hab) { habilidades.add(hab); }
    public List<HabilidadeClasse> getHabilidades() { 
        return new ArrayList<>(habilidades); 
    }
}