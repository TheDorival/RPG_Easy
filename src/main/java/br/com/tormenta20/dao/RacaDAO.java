package br.com.tormenta20.dao;

import br.com.tormenta20.model.Raca;
import br.com.tormenta20.enums.TipoAtributo;
import br.com.tormenta20.config.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * DAO para persistência de Raças no MySQL
 */
public class RacaDAO {
    
    public int salvar(Raca raca) throws SQLException {
        String sql = "INSERT INTO racas (nome, descricao, tamanho, deslocamento, " +
                    "bonus_pv, bonus_pm, visao_escuro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, 
                                     Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, raca.getNome());
            stmt.setString(2, raca.getDescricao());
            stmt.setString(3, raca.getTamanho());
            stmt.setInt(4, raca.getDeslocamento());
            stmt.setInt(5, raca.getBonusPVPorNivel());
            stmt.setInt(6, raca.getBonusPMPorNivel());
            stmt.setBoolean(7, raca.isVisaoEscuro());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    salvarModificadores(id, raca.getModificadoresAtributo());
                    salvarCaracteristicas(id, raca.getCaracteristicas());
                    return id;
                }
                throw new SQLException("Falha ao obter ID da raça");
            }
        }
    }
    
    public Raca buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM racas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Raca raca = mapearRaca(rs);
                    carregarModificadores(id, raca);
                    carregarCaracteristicas(id, raca);
                    return raca;
                }
            }
        }
        return null;
    }
    
    public List<Raca> listarTodos() throws SQLException {
        String sql = "SELECT * FROM racas ORDER BY nome";
        List<Raca> racas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Raca raca = mapearRaca(rs);
                int id = rs.getInt("id");
                carregarModificadores(id, raca);
                carregarCaracteristicas(id, raca);
                racas.add(raca);
            }
        }
        return racas;
    }
    
    public boolean deletar(int id) throws SQLException {
        deletarModificadores(id);
        deletarCaracteristicas(id);
        
        String sql = "DELETE FROM racas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    private Raca mapearRaca(ResultSet rs) throws SQLException {
        Raca raca = new Raca(rs.getString("nome"), rs.getString("descricao"));
        raca.setTamanho(rs.getString("tamanho"));
        raca.setDeslocamento(rs.getInt("deslocamento"));
        raca.setBonusPVPorNivel(rs.getInt("bonus_pv"));
        raca.setBonusPMPorNivel(rs.getInt("bonus_pm"));
        raca.setVisaoEscuro(rs.getBoolean("visao_escuro"));
        return raca;
    }
    
    private void salvarModificadores(int racaId, Map<TipoAtributo, Integer> mods) 
            throws SQLException {
        String sql = "INSERT INTO raca_modificadores (raca_id, atributo, valor) " +
                    "VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Map.Entry<TipoAtributo, Integer> entry : mods.entrySet()) {
                stmt.setInt(1, racaId);
                stmt.setString(2, entry.getKey().name());
                stmt.setInt(3, entry.getValue());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    private void carregarModificadores(int racaId, Raca raca) throws SQLException {
        String sql = "SELECT atributo, valor FROM raca_modificadores WHERE raca_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, racaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoAtributo tipo = TipoAtributo.valueOf(rs.getString("atributo"));
                    int valor = rs.getInt("valor");
                    raca.adicionarModificador(tipo, valor);
                }
            }
        }
    }
    
    private void deletarModificadores(int racaId) throws SQLException {
        String sql = "DELETE FROM raca_modificadores WHERE raca_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, racaId);
            stmt.executeUpdate();
        }
    }
    
    private void salvarCaracteristicas(int racaId, List<String> caracteristicas) 
            throws SQLException {
        String sql = "INSERT INTO raca_caracteristicas (raca_id, caracteristica) " +
                    "VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (String carac : caracteristicas) {
                stmt.setInt(1, racaId);
                stmt.setString(2, carac);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    private void carregarCaracteristicas(int racaId, Raca raca) throws SQLException {
        String sql = "SELECT caracteristica FROM raca_caracteristicas WHERE raca_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, racaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    raca.adicionarCaracteristica(rs.getString("caracteristica"));
                }
            }
        }
    }
    
    private void deletarCaracteristicas(int racaId) throws SQLException {
        String sql = "DELETE FROM raca_caracteristicas WHERE raca_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, racaId);
            stmt.executeUpdate();
        }
    }
}