package br.com.umbrella.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.umbrella.model.Missao;
import br.com.umbrella.util.ConnectionFactory;

public class MissaoDAO {

    // MÉTODO PARA SALVAR (Garante que o status nunca fique nulo ou pendente esquecido)
    public void salvar(Missao missao) {
        String sql = "INSERT INTO missoes (titulo, descricao, prioridade, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, missao.getTitulo());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getPrioridade());
            
            // Força o status a ser 'ANDAMENTO' caso venha nulo ou como 'PENDENTE'
            if (missao.getStatus() == null || missao.getStatus().trim().isEmpty() || missao.getStatus().equalsIgnoreCase("PENDENTE")) {
                stmt.setString(4, "ANDAMENTO");
            } else {
                stmt.setString(4, missao.getStatus());
            }

            stmt.execute();
            System.out.println("LOG UMBRELLA: Missão salva com sucesso!");

        } catch (SQLException e) {
            System.err.println("LOG UMBRELLA: Erro ao salvar no banco: " + e.getMessage());
        }
    }

    // MÉTODO PARA LISTAR (Traz absolutamente tudo do banco)
    public List<Missao> listarTodas() {
        String sql = "SELECT * FROM missoes ORDER BY id DESC"; // Adicionado ORDER BY para as mais novas aparecerem primeiro!
        List<Missao> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Missao m = new Missao();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setDescricao(rs.getString("descricao"));
                m.setPrioridade(rs.getString("prioridade"));
                m.setStatus(rs.getString("status"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.err.println("LOG UMBRELLA: Erro ao listar missões: " + e.getMessage());
        }
        return lista;
    }
}