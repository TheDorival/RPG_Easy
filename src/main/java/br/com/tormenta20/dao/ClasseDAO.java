package br.com.tormenta20.dao;

import br.com.tormenta20.model.Classe;
import br.com.tormenta20.enums.*;
import br.com.tormenta20.config.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * DAO para persistência de Classes no MySQL
 */
public class ClasseDAO {
    
    public int salvar(Classe classe) throws SQLException {
        String sql = "INSERT INTO classes (nome, descricao, atributo_chave, " +
                    "pv_base, pm_base) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, 
                                     Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, classe.getNome());
            stmt.setString(2, classe.getDescricao());
            stmt.setString(3, classe.getAtributoChave().name());
            stmt.setInt(4, classe.getPvBasePorNivel());
            stmt.setInt(5, classe.getPmBasePorNivel());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                throw new SQLException("Falha ao obter ID da classe");
            }
        }
    }
    
    public Classe buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM classes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearClasse(rs);
                }
            }
        }
        return null;
    }
    
    public List<Classe> listarTodos() throws SQLException {
        String sql = "SELECT * FROM classes ORDER BY nome";
        List<Classe> classes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                classes.add(mapearClasse(rs));
            }
        }
        return classes;
    }
    
    public boolean atualizar(Classe classe, int id) throws SQLException {
        String sql = "UPDATE classes SET nome = ?, descricao = ?, " +
                    "atributo_chave = ?, pv_base = ?, pm_base = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, classe.getNome());
            stmt.setString(2, classe.getDescricao());
            stmt.setString(3, classe.getAtributoChave().name());
            stmt.setInt(4, classe.getPvBasePorNivel());
            stmt.setInt(5, classe.getPmBasePorNivel());
            stmt.setInt(6, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM classes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    private Classe mapearClasse(ResultSet rs) throws SQLException {
        TipoAtributo atributo = TipoAtributo.valueOf(rs.getString("atributo_chave"));
        
        return new Classe(
            rs.getString("nome"),
            rs.getString("descricao"),
            atributo,
            rs.getInt("pv_base"),
            rs.getInt("pm_base")
        );
    }
}