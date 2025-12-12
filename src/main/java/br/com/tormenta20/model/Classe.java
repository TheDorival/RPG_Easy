package br.com.tormenta20.model;
import br.com.tormenta20.enums.*;
import java.util.*;

public class Classe extends ElementoFicha {
    private TipoAtributo atributoChave;
    private List<TipoPericia> periciasDisponiveis;
    private List<HabilidadeClasse> habilidades;
    private int pvBasePorNivel;
    private int pmBasePorNivel;
    
    public Classe(String nome, String descricao, TipoAtributo atributoChave,
                  int pvBasePorNivel, int pmBasePorNivel) {
        super(nome, descricao);
        this.atributoChave = atributoChave;
        this.pvBasePorNivel = pvBasePorNivel;
        this.pmBasePorNivel = pmBasePorNivel;
        this.periciasDisponiveis = new ArrayList<>();
        this.habilidades = new ArrayList<>();
    }
    
    public int calcularPV(int nivel, int modConstituicao, int bonusRacial) {
        return (pvBasePorNivel + modConstituicao + bonusRacial) * nivel;
    }
    
    public int calcularPM(int nivel, int bonusRacial) {
        return (pmBasePorNivel + bonusRacial) * nivel;
    }
    
    public TipoAtributo getAtributoChave() { return atributoChave; }
    
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
    
    public int getPvBasePorNivel() { return pvBasePorNivel; }
    public int getPmBasePorNivel() { return pmBasePorNivel; }
}
