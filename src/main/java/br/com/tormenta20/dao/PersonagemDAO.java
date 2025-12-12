package br.com.tormenta20.dao;

import br.com.tormenta20.config.DatabaseConnection;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.model.*;
import java.sql.*;
import java.util.*;

/**
 * DAO para persistência de Personagens no MySQL
 */
public class PersonagemDAO {
    
    /**
     * Salva um novo personagem no banco
     */
    public int salvar(Personagem personagem) throws SQLException {
        String sql = "INSERT INTO personagens (nome, nivel, pontos_vida, pontos_mana, " +
                    "defesa, raca, classe, origem, divindade, " +
                    "forca, destreza, constituicao, inteligencia, sabedoria, carisma) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, 
                                     Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, personagem.getNome());
            stmt.setInt(2, personagem.getNivel());
            stmt.setInt(3, personagem.getPontosVida());
            stmt.setInt(4, personagem.getPontosMana());
            stmt.setInt(5, personagem.getDefesa());
            stmt.setString(6, personagem.getRaca().getNome());
            stmt.setString(7, personagem.getClasse().getNome());
            stmt.setString(8, personagem.getOrigem() != null ? 
                          personagem.getOrigem().getNome() : null);
            stmt.setString(9, personagem.getDivindade());
            
            // Atributos
            Atributos atrib = personagem.getAtributos();
            stmt.setInt(10, atrib.getValor(TipoAtributo.FORCA));
            stmt.setInt(11, atrib.getValor(TipoAtributo.DESTREZA));
            stmt.setInt(12, atrib.getValor(TipoAtributo.CONSTITUICAO));
            stmt.setInt(13, atrib.getValor(TipoAtributo.INTELIGENCIA));
            stmt.setInt(14, atrib.getValor(TipoAtributo.SABEDORIA));
            stmt.setInt(15, atrib.getValor(TipoAtributo.CARISMA));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar personagem");
            }
            
            // Obtém o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    personagem.setId(id);
                    
                    // Salva perícias
                    salvarPericias(personagem);
                    
                    // Salva equipamento
                    salvarEquipamento(personagem);
                    
                    return id;
                }
                throw new SQLException("Falha ao obter ID do personagem");
            }
        }
    }
    
    /**
     * Busca personagem por ID
     */
    public Personagem buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM personagens WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Personagem personagem = mapearPersonagem(rs);
                    
                    // Carrega perícias
                    carregarPericias(personagem);
                    
                    // Carrega equipamento
                    carregarEquipamento(personagem);
                    
                    return personagem;
                }
            }
        }
        return null;
    }
    
    /**
     * Lista todos os personagens
     */
    public List<Personagem> listarTodos() throws SQLException {
        String sql = "SELECT * FROM personagens ORDER BY nome";
        List<Personagem> personagens = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Personagem personagem = mapearPersonagem(rs);
                carregarPericias(personagem);
                carregarEquipamento(personagem);
                personagens.add(personagem);
            }
        }
        return personagens;
    }
    
    /**
     * Atualiza um personagem existente
     */
    public boolean atualizar(Personagem personagem) throws SQLException {
        String sql = "UPDATE personagens SET nome = ?, nivel = ?, pontos_vida = ?, " +
                    "pontos_mana = ?, defesa = ?, divindade = ?, " +
                    "forca = ?, destreza = ?, constituicao = ?, " +
                    "inteligencia = ?, sabedoria = ?, carisma = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, personagem.getNome());
            stmt.setInt(2, personagem.getNivel());
            stmt.setInt(3, personagem.getPontosVida());
            stmt.setInt(4, personagem.getPontosMana());
            stmt.setInt(5, personagem.getDefesa());
            stmt.setString(6, personagem.getDivindade());
            
            Atributos atrib = personagem.getAtributos();
            stmt.setInt(7, atrib.getValor(TipoAtributo.FORCA));
            stmt.setInt(8, atrib.getValor(TipoAtributo.DESTREZA));
            stmt.setInt(9, atrib.getValor(TipoAtributo.CONSTITUICAO));
            stmt.setInt(10, atrib.getValor(TipoAtributo.INTELIGENCIA));
            stmt.setInt(11, atrib.getValor(TipoAtributo.SABEDORIA));
            stmt.setInt(12, atrib.getValor(TipoAtributo.CARISMA));
            stmt.setInt(13, personagem.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Atualiza perícias
                deletarPericias(personagem.getId());
                salvarPericias(personagem);
                
                // Atualiza equipamento
                deletarEquipamento(personagem.getId());
                salvarEquipamento(personagem);
            }
            
            return affectedRows > 0;
        }
    }
    
    /**
     * Deleta um personagem
     */
    public boolean deletar(int id) throws SQLException {
        // Primeiro deleta dependências
        deletarPericias(id);
        deletarEquipamento(id);
        
        String sql = "DELETE FROM personagens WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Busca personagens por nome (LIKE)
     */
    public List<Personagem> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM personagens WHERE nome LIKE ? ORDER BY nome";
        List<Personagem> personagens = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Personagem personagem = mapearPersonagem(rs);
                    carregarPericias(personagem);
                    carregarEquipamento(personagem);
                    personagens.add(personagem);
                }
            }
        }
        return personagens;
    }
    
    /**
     * Busca personagens por raça
     */
    public List<Personagem> buscarPorRaca(String raca) throws SQLException {
        String sql = "SELECT * FROM personagens WHERE raca = ? ORDER BY nome";
        List<Personagem> personagens = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, raca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Personagem personagem = mapearPersonagem(rs);
                    carregarPericias(personagem);
                    carregarEquipamento(personagem);
                    personagens.add(personagem);
                }
            }
        }
        return personagens;
    }
    
    // MÉTODOS AUXILIARES
    
    /**
     * Mapeia ResultSet para objeto Personagem
     */
    private Personagem mapearPersonagem(ResultSet rs) throws SQLException {
        Personagem p = new Personagem(rs.getString("nome"), rs.getInt("nivel"));
        p.setId(rs.getInt("id"));
        p.setDivindade(rs.getString("divindade"));
        
        // Atributos
        Atributos atrib = p.getAtributos();
        atrib.setValor(TipoAtributo.FORCA, rs.getInt("forca"));
        atrib.setValor(TipoAtributo.DESTREZA, rs.getInt("destreza"));
        atrib.setValor(TipoAtributo.CONSTITUICAO, rs.getInt("constituicao"));
        atrib.setValor(TipoAtributo.INTELIGENCIA, rs.getInt("inteligencia"));
        atrib.setValor(TipoAtributo.SABEDORIA, rs.getInt("sabedoria"));
        atrib.setValor(TipoAtributo.CARISMA, rs.getInt("carisma"));
        
        return p;
    }
    
    /**
     * Salva perícias do personagem
     */
    private void salvarPericias(Personagem personagem) throws SQLException {
        String sql = "INSERT INTO personagem_pericias " +
                    "(personagem_id, tipo_pericia, treinada) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Pericia pericia : personagem.getPericias()) {
                stmt.setInt(1, personagem.getId());
                stmt.setString(2, pericia.getTipo().name());
                stmt.setBoolean(3, pericia.isTreinada());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    /**
     * Carrega perícias do personagem
     */
    private void carregarPericias(Personagem personagem) throws SQLException {
        String sql = "SELECT tipo_pericia, treinada FROM personagem_pericias " +
                    "WHERE personagem_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, personagem.getId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoPericia tipo = TipoPericia.valueOf(rs.getString("tipo_pericia"));
                    Pericia pericia = new Pericia(tipo);
                    pericia.setTreinada(rs.getBoolean("treinada"));
                    personagem.adicionarPericia(pericia);
                }
            }
        }
    }
    
    /**
     * Deleta perícias do personagem
     */
    private void deletarPericias(int personagemId) throws SQLException {
        String sql = "DELETE FROM personagem_pericias WHERE personagem_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, personagemId);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Salva equipamento do personagem
     */
    private void salvarEquipamento(Personagem personagem) throws SQLException {
        String sql = "INSERT INTO personagem_equipamento " +
                    "(personagem_id, tipo, nome) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (personagem.getArmaEquipada() != null) {
                stmt.setInt(1, personagem.getId());
                stmt.setString(2, "ARMA");
                stmt.setString(3, personagem.getArmaEquipada().getNome());
                stmt.executeUpdate();
            }
            
            if (personagem.getArmaduraEquipada() != null) {
                stmt.setInt(1, personagem.getId());
                stmt.setString(2, "ARMADURA");
                stmt.setString(3, personagem.getArmaduraEquipada().getNome());
                stmt.executeUpdate();
            }
        }
    }
    
    /**
     * Carrega equipamento do personagem
     */
    private void carregarEquipamento(Personagem personagem) throws SQLException {
        String sql = "SELECT tipo, nome FROM personagem_equipamento " +
                    "WHERE personagem_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, personagem.getId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    // Aqui recriar os objetos de equipamento baseado no nome salvo
                }
            }
        }
    }
    
    /**
     * Deleta equipamento do personagem
     */
    private void deletarEquipamento(int personagemId) throws SQLException {
        String sql = "DELETE FROM personagem_equipamento WHERE personagem_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, personagemId);
            stmt.executeUpdate();
        }
    }
}
