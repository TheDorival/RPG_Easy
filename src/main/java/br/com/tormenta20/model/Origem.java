package br.com.tormenta20.model;

import java.util.*;
import br.com.tormenta20.enums.TipoPericia;

public class Origem extends ElementoFicha {
    private List<Poder> poderes;
    private List<TipoPericia> periciasBonus;
    
    public Origem(String nome, String descricao) {
        super(nome, descricao);
        this.poderes = new ArrayList<>();
        this.periciasBonus = new ArrayList<>();
    }
    
    public void adicionarPoder(Poder poder) {
        poderes.add(poder);
    }
    
    public void adicionarPericiaBonus(TipoPericia pericia) {
        periciasBonus.add(pericia);
    }
    
    public List<Poder> getPoderes() {
        return new ArrayList<>(poderes);
    }
    
    public List<TipoPericia> getPericiasBonus() {
        return new ArrayList<>(periciasBonus);
    }
}