package br.com.tormenta20;

import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.util.*;
import java.util.*;

public class ExemploUsoTormenta20 {
    
    public static void main(String[] args) {
        System.out.println("=== CRIADOR DE FICHAS TORMENTA 20 ===\n");
        
        // Inicializa serviços
        PersonagemService service = new PersonagemService();
        PersonagemDAOSimulator dao = new PersonagemDAOSimulator();
        
        // 1. CRIAR RAÇA
        System.out.println("1. Criando Raça: Elfo");
        Raca elfo = criarRacaElfo();
        System.out.println("   - Modificadores: " + elfo.getModificadoresAtributo());
        System.out.println("   - Características: " + elfo.getCaracteristicas());
        System.out.println();
        
        // 2. CRIAR CLASSE
        System.out.println("2. Criando Classe: Guerreiro");
        Classe guerreiro = criarClasseGuerreiro();
        System.out.println("   - Atributo Chave: " + guerreiro.getAtributoChave());
        System.out.println("   - Perícias Disponíveis: " + guerreiro.getPericiasDisponiveis());
        System.out.println();
        
        // 3. CRIAR ORIGEM
        System.out.println("3. Criando Origem: Guerreiro de Arena");
        Origem origem = criarOrigemGuerreiroArena();
        System.out.println("   - Poderes: " + origem.getPoderes().size());
        System.out.println();
        
        // 4. CRIAR PERSONAGEM
        System.out.println("4. Criando Personagem: Thorin");
        Personagem thorin = service.criarPersonagem(
            "Thorin Escudo-de-Carvalho", 
            5, 
            elfo, 
            guerreiro, 
            origem
        );
        
        // Configurar atributos
        configurarAtributos(thorin);
        System.out.println("   ✓ Personagem criado com sucesso!");
        System.out.println();
        
        // 5. DEFINIR DIVINDADE
        thorin.setDivindade("Khalmyr");
        System.out.println("5. Divindade: " + thorin.getDivindade());
        System.out.println();
        
        // 6. TREINAR PERÍCIAS
        System.out.println("6. Treinando Perícias:");
        service.treinarPericia(thorin, TipoPericia.ATLETISMO);
        service.treinarPericia(thorin, TipoPericia.INTIMIDACAO);
        System.out.println("   ✓ Atletismo - Treinada");
        System.out.println("   ✓ Intimidação - Treinada");
        System.out.println();
        
        // 7. EXIBIR FICHA COMPLETA
        exibirFichaCompleta(thorin, service);
        
        // 8. TESTAR ROLAGENS DE DADOS
        System.out.println("\n=== ROLAGENS DE TESTE ===");
        testarRolagens(thorin);
        
        // 9. SALVAR EM MEMÓRIA
        System.out.println("\n=== PERSISTÊNCIA ===");
        int id = dao.salvar(thorin);
        System.out.println("Personagem salvo com ID: " + id);
        
        // 10. BUSCAR DA MEMÓRIA
        Personagem recuperado = dao.buscarPorId(id);
        System.out.println("Personagem recuperado: " + recuperado.getNome());
        
        // 11. CRIAR MAIS PERSONAGENS
        System.out.println("\n=== CRIANDO PARTY ===");
        criarParty(service, dao);
        
        // 12. LISTAR TODOS
        System.out.println("\nPersonagens cadastrados:");
        for (Personagem p : dao.listarTodos()) {
            System.out.printf("  - %s (Nv %d) - %s %s\n", 
                p.getNome(), 
                p.getNivel(),
                p.getRaca().getNome(),
                p.getClasse().getNome());
        }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    private static Raca criarRacaElfo() {
        Raca elfo = new Raca("Elfo", "Seres longevos da floresta");
        elfo.adicionarModificador(TipoAtributo.DESTREZA, 2);
        elfo.adicionarModificador(TipoAtributo.INTELIGENCIA, 2);
        elfo.adicionarModificador(TipoAtributo.CONSTITUICAO, -2);
        elfo.adicionarCaracteristica("Visão na Penumbra");
        elfo.adicionarCaracteristica("Sentidos Aguçados");
        elfo.setTamanho("Médio");
        elfo.setDeslocamento(9);
        return elfo;
    }
    
    private static Classe criarClasseGuerreiro() {
        
        Classe guerreiro = new Classe(
            "Guerreiro", 
            "Mestre das armas e combate", 
            TipoAtributo.FORCA,
            20,  // PV base por nível
            3    // PM base por nível
        );
        
        guerreiro.adicionarPericiaDisponivel(TipoPericia.ATLETISMO);
        guerreiro.adicionarPericiaDisponivel(TipoPericia.INTIMIDACAO);
        guerreiro.adicionarPericiaDisponivel(TipoPericia.PERCEPCAO);
        
        // Adicionar habilidades de classe
        HabilidadeClasse ataquePodereso = new HabilidadeClasse(
            "Ataque Poderoso",
            "Pode trocar precisão por dano",
            1,
            "Guerreiro"
        );
        guerreiro.adicionarHabilidade(ataquePodereso);
        
        HabilidadeClasse ataquesMultiplos = new HabilidadeClasse(
            "Ataques Múltiplos",
            "Pode atacar duas vezes por rodada",
            5,
            "Guerreiro"
        );
        guerreiro.adicionarHabilidade(ataquesMultiplos);
        
        return guerreiro;
    }
    
    private static Origem criarOrigemGuerreiroArena() {
        Origem origem = new Origem("Guerreiro de Arena", 
            "Lutou em arenas pela sobrevivência");
        
        Poder gladiador = new Poder(
            "Gladiador",
            "Bônus em combate desarmado e intimidação",
            "Arena"
        );
        origem.adicionarPoder(gladiador);
        
        origem.adicionarPericiaBonus(TipoPericia.ATLETISMO);
        origem.adicionarPericiaBonus(TipoPericia.INTIMIDACAO);
        
        return origem;
    }
    
    private static void configurarAtributos(Personagem personagem) {
        Atributos atrib = personagem.getAtributos();
        atrib.setValor(TipoAtributo.FORCA, 16);
        atrib.setValor(TipoAtributo.DESTREZA, 14);
        atrib.setValor(TipoAtributo.CONSTITUICAO, 14);
        atrib.setValor(TipoAtributo.INTELIGENCIA, 10);
        atrib.setValor(TipoAtributo.SABEDORIA, 12);
        atrib.setValor(TipoAtributo.CARISMA, 8);
        
        // Aplica modificadores raciais
        personagem.aplicarModificadoresRaca();
    }
    
    private static void exibirFichaCompleta(Personagem p, PersonagemService service) {
        System.out.println("=== FICHA DE PERSONAGEM ===");
        System.out.println("Nome: " + p.getNome());
        System.out.println("Nível: " + p.getNivel());
        System.out.println("Raça: " + p.getRaca().getNome());
        System.out.println("Classe: " + p.getClasse().getNome());
        System.out.println("Origem: " + p.getOrigem().getNome());
        System.out.println("Divindade: " + p.getDivindade());
        
        System.out.println("\n--- ATRIBUTOS ---");
        Atributos atrib = p.getAtributos();
        for (TipoAtributo tipo : TipoAtributo.values()) {
            int valor = atrib.getValor(tipo);
            int mod = atrib.getModificador(tipo);
            System.out.printf("%s: %d (%+d)\n", tipo, valor, mod);
        }
        
        System.out.println("\n--- PERÍCIAS ---");
        for (Pericia pericia : p.getPericias()) {
            int mod = p.calcularModificadorPericia(pericia);
            String status = pericia.isTreinada() ? "[T]" : "   ";
            System.out.printf("%s %s: %+d\n", 
                status, pericia.getTipo(), mod);
        }
        
        System.out.println("\n--- HABILIDADES DE CLASSE ---");
        for (HabilidadeClasse hab : p.getHabilidades()) {
            System.out.println("• " + hab.getNome() + 
                             " (Nível " + hab.getNivelRequerido() + ")");
            System.out.println("  " + hab.getDescricao());
        }
        
        System.out.println("\n--- PODERES ---");
        for (Poder poder : p.getPoderes()) {
            System.out.println("• " + poder.getNome());
            System.out.println("  " + poder.getDescricao());
        }
        
        Map<String, Integer> bonus = service.calcularBonusTotal(p);
        System.out.println("\n--- OUTROS BÔNUS ---");
        System.out.println("Modificador de Magia: " + bonus.get("MAGIA"));
    }
    
    private static void testarRolagens(Personagem personagem) {

        // Teste 1: D20 simples
        System.out.println("\n1. Rolagem de D20:");
        for (int i = 0; i < 3; i++) {
            int resultado = RoladorDados.rolarD20();
            System.out.println("   Tentativa " + (i+1) + ": " + resultado);
        }
        
        // Teste 2: Rolagem de perícia
        System.out.println("\n2. Teste de Atletismo:");
        for (int i = 0; i < 3; i++) {
            int resultado = personagem.rolarPericia(TipoPericia.ATLETISMO);
            System.out.println("   Tentativa " + (i+1) + ": " + resultado);
        }
        
        // Teste 3: Rolagem com modificador
        System.out.println("\n3. Ataque com Espada Longa:");
        int modForca = personagem.getAtributos()
            .getModificador(TipoAtributo.FORCA);
        int bonusProficiencia = personagem.getNivel() / 2;
        int bonusAtaque = modForca + bonusProficiencia;
        
        for (int i = 0; i < 3; i++) {
            ResultadoRolagem resultado = 
                RoladorDados.rolarComModificador(bonusAtaque);
            System.out.println("   Tentativa " + (i+1) + ": " + resultado);
        }
        
        // Teste 4: Dano
        System.out.println("\n4. Dano (1d8 + Força):");
        for (int i = 0; i < 3; i++) {
            int dano = RoladorDados.rolar(1, 8) + modForca;
            System.out.println("   Dano " + (i+1) + ": " + dano);
        }
    }
    
    private static void criarParty(PersonagemService service, PersonagemDAOSimulator dao) {
        // Criar Mago
        Raca humano = new Raca("Humano", "Raça versátil");
        humano.adicionarModificador(TipoAtributo.INTELIGENCIA, 2);
        
        Classe mago = new Classe(
            "Mago", 
            "Conjurador arcano", 
            TipoAtributo.INTELIGENCIA,
            6,  // PV base
            6   // PM base
        );
        mago.adicionarPericiaDisponivel(TipoPericia.CONHECIMENTO);
        
        Origem erudito = new Origem("Erudito", "Estudioso dedicado");
        
        Personagem gandalf = service.criarPersonagem(
            "Gandalf, o Cinzento", 10, humano, mago, erudito);
        gandalf.getAtributos().setValor(TipoAtributo.INTELIGENCIA, 18);
        gandalf.setDivindade("Wynna");
        
        // Adicionar magias
        Magia bolaDeFogo = new Magia("Bola de Fogo", 
            "Explosão de fogo", 3, "Evocação");
        bolaDeFogo.setTempoConjuracao("1 ação padrão");
        bolaDeFogo.setAlcance("30m");
        gandalf.adicionarMagia(bolaDeFogo);
        
        dao.salvar(gandalf);
        System.out.println("✓ " + gandalf.getNome() + " criado!");
        
        // Criar Clérigo
        Classe clerigo = new Classe(
            "Clérigo", 
            "Devoto divino", 
            TipoAtributo.SABEDORIA,
            8,  // PV base
            5   // PM base
        );
        Origem devoto = new Origem("Devoto", "Servo dos deuses");
        
        Personagem frodo = service.criarPersonagem(
            "Frodo Bolseiro", 4, humano, clerigo, devoto);
        frodo.setDivindade("Lena");
        frodo.getAtributos().setValor(TipoAtributo.SABEDORIA, 16);
        
        dao.salvar(frodo);
        System.out.println("✓ " + frodo.getNome() + " criado!");
    }
}