package br.com.tormenta20.model;
import br.com.tormenta20.enums.*;
import java.util.*;

public class Classe extends ElementoFicha {
    private TipoAtributo atributoChave;
    private List<TipoPericia> periciasDisponiveis;
    private List<HabilidadeClasse> habilidades;
    private int pontosVida;
    
    public Classe(String nome, String descricao, TipoAtributo atributoChave) {
        super(nome, descricao);
        this.atributoChave = atributoChave;
        this.periciasDisponiveis = new ArrayList<>();
        this.habilidades = new ArrayList<>();
    }
    
    public TipoAtributo getAtributoChave() {
        return atributoChave;
    }
    
    public void adicionarPericiaDisponivel(TipoPericia pericia) {
        periciasDisponiveis.add(pericia);
    }
    
    public void adicionarHabilidade(HabilidadeClasse habilidade) {
        habilidades.add(habilidade);
    }
    
    public List<TipoPericia> getPericiasDisponiveis() {
        return new ArrayList<>(periciasDisponiveis);
    }
    
    public List<HabilidadeClasse> getHabilidades() {
        return new ArrayList<>(habilidades);
    }
    
    public int getPontosVida() {
        return pontosVida;
    }
    
    public void setPontosVida(int pontosVida) {
        this.pontosVida = pontosVida;
    }
}