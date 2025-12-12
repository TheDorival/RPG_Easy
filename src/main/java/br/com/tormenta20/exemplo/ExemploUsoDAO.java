package br.com.tormenta20.exemplo;

import br.com.tormenta20.model.*;
import br.com.tormenta20.dao.*;
import br.com.tormenta20.factory.*;
import br.com.tormenta20.builder.*;
import br.com.tormenta20.enums.*;
import java.sql.SQLException;
import java.util.List;

public class ExemploUsoDAO {
    
    public static void main(String[] args) {
        PersonagemDAO dao = new PersonagemDAO();
        
        try {
            // 1. CRIAR E SALVAR PERSONAGEM
            System.out.println("=== SALVANDO PERSONAGEM ===");
            Personagem gimli = new PersonagemBuilder("Gimli", 5)
                .comRaca(RacaFactory.criarAnao())
                .comClasse(ClasseFactory.criarGuerreiro())
                .comAtributo(TipoAtributo.FORCA, 16)
                .comAtributo(TipoAtributo.CONSTITUICAO, 16)
                .comArma(ArmaFactory.criarMachado())
                .comArmadura(ArmaduraFactory.criarCotaMalha())
                .treinarPericia(TipoPericia.LUTA)
                .construir();
            
            int id = dao.salvar(gimli);
            System.out.println("Personagem salvo com ID: " + id);
            
            // 2. BUSCAR POR ID
            System.out.println("\n=== BUSCANDO POR ID ===");
            Personagem recuperado = dao.buscarPorId(id);
            if (recuperado != null) {
                System.out.println("Personagem encontrado: " + recuperado.getNome());
                System.out.println("PV: " + recuperado.getPontosVida());
                System.out.println("PM: " + recuperado.getPontosMana());
            }
            
            // 3. LISTAR TODOS
            System.out.println("\n=== LISTANDO TODOS ===");
            List<Personagem> todos = dao.listarTodos();
            for (Personagem p : todos) {
                System.out.printf("%s - Nv %d - %s %s\n", 
                    p.getNome(), p.getNivel(), 
                    p.getRaca().getNome(), p.getClasse().getNome());
            }
            
            // 4. BUSCAR POR NOME
            System.out.println("\n=== BUSCANDO POR NOME ===");
            List<Personagem> porNome = dao.buscarPorNome("Gim");
            for (Personagem p : porNome) {
                System.out.println("Encontrado: " + p.getNome());
            }
            
            // 5. ATUALIZAR
            System.out.println("\n=== ATUALIZANDO PERSONAGEM ===");
            recuperado.setNivel(6);
            recuperado.calcularPVePM();
            boolean atualizado = dao.atualizar(recuperado);
            System.out.println("Atualizado: " + atualizado);
            
            // 6. DELETAR
            System.out.println("\n=== DELETANDO PERSONAGEM ===");
            boolean deletado = dao.deletar(id);
            System.out.println("Deletado: " + deletado);
            
        } catch (SQLException e) {
            System.err.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        }
    }
}