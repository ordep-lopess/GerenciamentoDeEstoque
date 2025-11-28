package dao;

import models.ProdutoObservacao;
import util.ConectaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoObservacaoDAO {

    // Busca observação de um produto pelo id do produto
    public ProdutoObservacao getByProdutoId(int idProduto) {
        String sql = "SELECT * FROM doacao_observacao WHERE id_doacao = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectaDB.conectar();
            if (conn == null) return null;

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduto);
            rs = ps.executeQuery();
            if (rs.next()) {
                ProdutoObservacao po = new ProdutoObservacao();
                po.setId(rs.getInt("id"));
                po.setIdProduto(rs.getInt("id_doacao"));
                po.setObservacao(rs.getString("observacao"));
                return po;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignore) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
        return null;
    }

    // Insere uma nova observação (útil quando o produto acabou de ser criado)
    public boolean insert(ProdutoObservacao po) {
        String sql = "INSERT INTO doacao_observacao (id_doacao, observacao) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, po.getIdProduto());
            ps.setString(2, po.getObservacao());
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    // Atualiza a observação de um produto que já tem registro
    public boolean update(ProdutoObservacao po) {
        String sql = "UPDATE doacao_observacao SET observacao = ? WHERE id_doacao = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;
            ps = conn.prepareStatement(sql);
            ps.setString(1, po.getObservacao());
            ps.setInt(2, po.getIdProduto());
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    // Atalho: se já existe, faz update; se não existe, faz insert
    public boolean saveOrUpdate(ProdutoObservacao po) {
        ProdutoObservacao existente = getByProdutoId(po.getIdProduto());
        if (existente == null) {
            return insert(po);
        } else {
            return update(po);
        }
    }

    // Remove a observação de um produto
    public boolean deleteByProdutoId(int idProduto) {
        String sql = "DELETE FROM doacao_observacao WHERE id_doacao = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduto);
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }
}
