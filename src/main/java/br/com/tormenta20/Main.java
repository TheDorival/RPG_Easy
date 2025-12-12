package br.com.tormenta20;

import br.com.tormenta20.model.*;
import br.com.tormenta20.service.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.factory.*;
import br.com.tormenta20.builder.*;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.util.*;
import java.util.*;

/**
 * Classe principal com menu interativo
 */
public class Main {
    
    private static PersonagemService service = new PersonagemService();
    private static PersonagemDAOSimulator dao = new PersonagemDAOSimulator();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO RPG TORMENTA   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    criarPersonagem();
                    break;
                case 2:
                    listarPersonagens();
                    break;
                case 3:
                    buscarPersonagem();
                    break;
                case 4:
                    exibirFichaCompleta();
                    break;
                case 5:
                    testarCombate();
                    break;
                case 6:
                    rolarDados();
                    break;
                case 7:
                    carregarExemplos();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\n✓ Encerrando sistema...");
                    break;
                default:
                    System.out.println("\n✗ Opção inválida!");
            }
            
            if (continuar) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║              MENU PRINCIPAL                ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ 1. Criar Novo Personagem                   ║");
        System.out.println("║ 2. Listar Todos os Personagens             ║");
        System.out.println("║ 3. Buscar Personagem por Nome              ║");
        System.out.println("║ 4. Exibir Ficha Completa                   ║");
        System.out.println("║ 5. Testar Combate                          ║");
        System.out.println("║ 6. Rolar Dados                             ║");
        System.out.println("║ 7. Carregar Personagens de Exemplo         ║");
        System.out.println("║ 0. Sair                                    ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void criarPersonagem() {
        System.out.println("\n=== CRIAR NOVO PERSONAGEM ===\n");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Nível (1-20): ");
        int nivel = Integer.parseInt(scanner.nextLine());
        
        // Escolher Raça
        System.out.println("\nRaças disponíveis:");
        System.out.println("1. Humano");
        System.out.println("2. Anão");
        System.out.println("3. Elfo");
        System.out.println("4. Goblin");
        System.out.print("Escolha: ");
        int racaOpcao = Integer.parseInt(scanner.nextLine());
        
        Raca raca = escolherRaca(racaOpcao);
        
        // Escolher Classe
        System.out.println("\nClasses disponíveis:");
        System.out.println("1. Guerreiro");
        System.out.println("2. Arcanista");
        System.out.println("3. Bucaneiro");
        System.out.print("Escolha: ");
        int classeOpcao = Integer.parseInt(scanner.nextLine());
        
        Classe classe = escolherClasse(classeOpcao);
        
        // Criar origem simples
        Origem origem = new Origem("Origem Comum", "Background padrão");
        
        // Criar personagem
        Personagem personagem = service.criarPersonagem(nome, nivel, raca, classe, origem);
        
        // Definir atributos
        System.out.println("\n=== DEFINIR ATRIBUTOS ===");
        definirAtributos(personagem);
        
        // Aplicar modificadores e calcular stats
        personagem.aplicarModificadoresRaca();
        
        // Equipar itens básicos
        if (classe.getNome().equals("Guerreiro")) {
            personagem.setArmaEquipada(ArmaFactory.criarEspada());
            personagem.setArmaduraEquipada(ArmaduraFactory.criarCotaMalha());
        } else if (classe.getNome().equals("Arcanista")) {
            personagem.setArmaduraEquipada(ArmaduraFactory.criarCouro());
        } else if (classe.getNome().equals("Bucaneiro")) {
            personagem.setArmaEquipada(ArmaFactory.criarEspada());  // ← ADICIONE ESTA LINHA
            personagem.setArmaduraEquipada(ArmaduraFactory.criarCouro());
        }
        
        // Salvar
        int id = dao.salvar(personagem);
        
        System.out.println("\n✓ Personagem criado com sucesso! ID: " + id);
        exibirFichaResumida(personagem);
    }
    
    private static void definirAtributos(Personagem personagem) {
        System.out.println("Digite os valores dos atributos (8-18):");
        
        System.out.print("Força: ");
        personagem.getAtributos().setValor(TipoAtributo.FORCA, 
            Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Destreza: ");
        personagem.getAtributos().setValor(TipoAtributo.DESTREZA, 
            Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Constituição: ");
        personagem.getAtributos().setValor(TipoAtributo.CONSTITUICAO, 
            Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Inteligência: ");
        personagem.getAtributos().setValor(TipoAtributo.INTELIGENCIA, 
            Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Sabedoria: ");
        personagem.getAtributos().setValor(TipoAtributo.SABEDORIA, 
            Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Carisma: ");
        personagem.getAtributos().setValor(TipoAtributo.CARISMA, 
            Integer.parseInt(scanner.nextLine()));
    }
    
    private static Raca escolherRaca(int opcao) {
        switch (opcao) {
            case 1: return RacaFactory.criarHumano();
            case 2: return RacaFactory.criarAnao();
            case 3: return RacaFactory.criarElfo();
            case 4: return RacaFactory.criarGoblin();
            default: return RacaFactory.criarHumano();
        }
    }
    
    private static Classe escolherClasse(int opcao) {
        switch (opcao) {
            case 1: return ClasseFactory.criarGuerreiro();
            case 2: return ClasseFactory.criarArcanista();
            case 3: return ClasseFactory.criarBucaneiro();
            default: return ClasseFactory.criarGuerreiro();
        }
    }
    
    private static void listarPersonagens() {
        System.out.println("\n=== PERSONAGENS CADASTRADOS ===\n");
        
        List<Personagem> personagens = dao.listarTodos();
        
        if (personagens.isEmpty()) {
            System.out.println("Nenhum personagem cadastrado.");
            return;
        }
        
        for (Personagem p : personagens) {
            System.out.printf("[ID %d] %s - Nv %d - %s %s\n",
                p.getId(), p.getNome(), p.getNivel(),
                p.getRaca().getNome(), p.getClasse().getNome());
        }
        
        System.out.println("\nTotal: " + personagens.size() + " personagens");
    }
    
    private static void buscarPersonagem() {
        System.out.println("\n=== BUSCAR PERSONAGEM ===\n");
        System.out.print("Digite o nome (ou parte): ");
        String nome = scanner.nextLine();
        
        List<Personagem> encontrados = dao.buscarPorNome(nome);
        
        if (encontrados.isEmpty()) {
            System.out.println("\n✗ Nenhum personagem encontrado.");
        } else {
            System.out.println("\n✓ Personagens encontrados:\n");
            for (Personagem p : encontrados) {
                exibirFichaResumida(p);
            }
        }
    }
    
    private static void exibirFichaCompleta() {
        System.out.println("\n=== EXIBIR FICHA COMPLETA ===\n");
        System.out.print("Digite o ID do personagem: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Personagem p = dao.buscarPorId(id);
        
        if (p == null) {
            System.out.println("\n✗ Personagem não encontrado!");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           FICHA DE PERSONAGEM              ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("\nNome: " + p.getNome());
        System.out.println("Nível: " + p.getNivel());
        System.out.println("Raça: " + p.getRaca().getNome());
        System.out.println("Classe: " + p.getClasse().getNome());
        System.out.println("\n--- ATRIBUTOS ---");
        
        for (TipoAtributo tipo : TipoAtributo.values()) {
            int valor = p.getAtributos().getValor(tipo);
            int mod = p.getAtributos().getModificador(tipo);
            System.out.printf("%-14s: %2d (%+d)\n", tipo, valor, mod);
        }
        
        System.out.println("\n--- STATUS ---");
        System.out.println("PV: " + p.getPontosVida());
        System.out.println("PM: " + p.getPontosMana());
        System.out.println("Defesa: " + p.getDefesa());
        
        if (p.getArmaEquipada() != null) {
            System.out.println("\n--- EQUIPAMENTO ---");
            System.out.println("Arma: " + p.getArmaEquipada().getNome());
        }
        
        if (p.getArmaduraEquipada() != null) {
            System.out.println("Armadura: " + p.getArmaduraEquipada().getNome());
        }
    }
    
    private static void exibirFichaResumida(Personagem p) {
        System.out.printf("\n[%s] Nv %d | %s %s\n",
            p.getNome(), p.getNivel(), 
            p.getRaca().getNome(), p.getClasse().getNome());
        System.out.printf("  PV: %d | PM: %d | DEF: %d\n",
            p.getPontosVida(), p.getPontosMana(), p.getDefesa());
    }
    
   private static void testarCombate() {
    System.out.println("\n=== TESTE DE COMBATE ===\n");
    
    List<Personagem> personagens = dao.listarTodos();
    if (personagens.size() < 2) {
        System.out.println("✗ É necessário ter pelo menos 2 personagens!");
        return;
    }
    
    System.out.println("Personagens disponíveis:");
    for (int i = 0; i < personagens.size(); i++) {
        System.out.printf("%d. %s (Nv %d)\n", i + 1, 
            personagens.get(i).getNome(), 
            personagens.get(i).getNivel());
    }
    
    try {
        System.out.print("\nEscolha o atacante (número): ");
        int idxAtacante = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (idxAtacante < 0 || idxAtacante >= personagens.size()) {
            System.out.println("\n✗ Número inválido! Escolha entre 1 e " + personagens.size());
            return;
        }
        
        System.out.print("Escolha o defensor (número): ");
        int idxDefensor = Integer.parseInt(scanner.nextLine()) - 1;
        
        if (idxDefensor < 0 || idxDefensor >= personagens.size()) {
            System.out.println("\n✗ Número inválido! Escolha entre 1 e " + personagens.size());
            return;
        }
        
        Personagem atacante = personagens.get(idxAtacante);
        Personagem defensor = personagens.get(idxDefensor);
        
        CombateService combate = new CombateService();
        
        System.out.println("\n--- COMBATE ---");
        System.out.println(atacante.getNome() + " ataca " + defensor.getNome() + "!\n");
        
        // Simular 3 rodadas
        for (int i = 1; i <= 3; i++) {
            System.out.println("Rodada " + i + ":");
            var resultado = combate.realizarAtaque(atacante, defensor);
            System.out.println(resultado);
            System.out.println();
        }
    } catch (NumberFormatException e) {
        System.out.println("\n✗ Entrada inválida! Digite apenas números.");
    }
}
    
    private static void rolarDados() {
        System.out.println("\n=== ROLADOR DE DADOS ===\n");
        System.out.println("1. D4");
        System.out.println("2. D6");
        System.out.println("3. D8");
        System.out.println("4. D10");
        System.out.println("5. D12");
        System.out.println("6. D20");
        System.out.print("\nEscolha o dado: ");
        
        int opcao = Integer.parseInt(scanner.nextLine());
        int lados = 20;
        
        switch (opcao) {
            case 1: lados = 4; break;
            case 2: lados = 6; break;
            case 3: lados = 8; break;
            case 4: lados = 10; break;
            case 5: lados = 12; break;
            case 6: lados = 20; break;
        }
        
        System.out.print("Quantos dados rolar? ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        
        System.out.println("\n--- RESULTADO ---");
        int total = 0;
        for (int i = 1; i <= quantidade; i++) {
            int resultado = RoladorDados.rolar(1, lados);
            System.out.printf("Dado %d: %d\n", i, resultado);
            total += resultado;
        }
        System.out.println("\nTotal: " + total);
    }
    
    private static void carregarExemplos() {
        System.out.println("\n=== CARREGANDO PERSONAGENS DE EXEMPLO ===\n");
        
        // Guerreiro Anão
        Personagem gimli = new PersonagemBuilder("Gimli", 5)
            .comRaca(RacaFactory.criarAnao())
            .comClasse(ClasseFactory.criarGuerreiro())
            .comAtributo(TipoAtributo.FORCA, 16)
            .comAtributo(TipoAtributo.CONSTITUICAO, 16)
            .comArma(ArmaFactory.criarMachado())
            .comArmadura(ArmaduraFactory.criarCotaMalha())
            .construir();
        dao.salvar(gimli);
        System.out.println("✓ Gimli criado");
        
        // Arcanista Elfo
        Personagem gandalf = new PersonagemBuilder("Gandalf", 10)
            .comRaca(RacaFactory.criarElfo())
            .comClasse(ClasseFactory.criarArcanista())
            .comAtributo(TipoAtributo.INTELIGENCIA, 18)
            .comAtributo(TipoAtributo.DESTREZA, 14)
            .construir();
        dao.salvar(gandalf);
        System.out.println("✓ Gandalf criado");
        
        // Bucaneiro Humano
        Personagem jack = new PersonagemBuilder("Jack Sparrow", 7)
            .comRaca(RacaFactory.criarHumano())
            .comClasse(ClasseFactory.criarBucaneiro())
            .comAtributo(TipoAtributo.DESTREZA, 16)
            .comAtributo(TipoAtributo.CARISMA, 16)
            .comArma(ArmaFactory.criarEspada())
            .comArmadura(ArmaduraFactory.criarCouro())
            .construir();
        dao.salvar(jack);
        System.out.println("✓ Jack Sparrow criado");
        
        System.out.println("\n✓ 3 personagens de exemplo carregados!");
    }
}