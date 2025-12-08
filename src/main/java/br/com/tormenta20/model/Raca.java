package main.java.br.com.tormenta20.model;

public class Raca extends ElementoFicha {
    private Map<TipoAtributo, Integer> modificadoresAtributo;
    private List<String> caracteristicas;
    private String tamanho;
    private int deslocamento;
    
    public Raca(String nome, String descricao) {
        super(nome, descricao);
        this.modificadoresAtributo = new HashMap<>();
        this.caracteristicas = new ArrayList<>();
    }
    
    public void adicionarModificador(TipoAtributo atributo, int valor) {
        modificadoresAtributo.put(atributo, valor);
    }
    
    public void adicionarCaracteristica(String caracteristica) {
        caracteristicas.add(caracteristica);
    }
    
    public Map<TipoAtributo, Integer> getModificadoresAtributo() {
        return new HashMap<>(modificadoresAtributo);
    }
    
    public List<String> getCaracteristicas() {
        return new ArrayList<>(caracteristicas);
    }
    
    public String getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    
    public int getDeslocamento() {
        return deslocamento;
    }
    
    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }
}