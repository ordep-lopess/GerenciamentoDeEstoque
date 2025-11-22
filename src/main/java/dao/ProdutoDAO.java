/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import models.Produto;
import util.ConectaDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pedroH, bianca
 */

public class ProdutoDAO {

    public boolean insProduto(Produto produto) {
        String sql = ""
            + "INSERT INTO produto ("
            +   "nome_doador, telefone, email, descricao, marca, "
            +   "quantidade, animal, tipo, pacote_fechado, data_doacao"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, produto.getNomeDoador());
            ps.setString(2, produto.getTelefone());
            ps.setString(3, produto.getEmail());
            ps.setString(4, produto.getDescricao());
            ps.setString(5, produto.getMarca());
            ps.setDouble(6, produto.getQuantidade());
            ps.setString(7, produto.getAnimal());
            ps.setString(8, produto.getTipo());
            ps.setString(9, produto.getPacoteFechado());
            if (produto.getDataDoacao() != null) {
                ps.setDate(10, Date.valueOf(produto.getDataDoacao()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    produto.setId(keys.getInt(1));
                }
            }
            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    public Produto getProdutoById(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return null;

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduto(rs);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignore) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
        return null;
    }

    public List<Produto> getAllProdutos() {
        String sql = "SELECT * FROM produto ORDER BY id";
        List<Produto> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return lista;

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSetToProduto(rs));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignore) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
        return lista;
    }

    public boolean updateProduto(Produto produto) {
        String sql = ""
            + "UPDATE produto SET "
            +   "nome_doador = ?, telefone = ?, email = ?, descricao = ?, marca = ?, "
            +   "animal = ?, tipo = ?, pacote_fechado = ?, data_doacao = ? "
            + "WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;

            ps = conn.prepareStatement(sql);

            ps.setString(1, produto.getNomeDoador());
            ps.setString(2, produto.getTelefone());
            ps.setString(3, produto.getEmail());
            ps.setString(4, produto.getDescricao());
            ps.setString(5, produto.getMarca());
            ps.setString(6, produto.getAnimal());
            ps.setString(7, produto.getTipo());
            ps.setString(8, produto.getPacoteFechado());

            if (produto.getDataDoacao() != null) {
                ps.setDate(9, Date.valueOf(produto.getDataDoacao()));
            } else {
                ps.setNull(9, Types.DATE);
            }

            ps.setInt(10, produto.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    public int deleteProdutoWithStatus(int id) {
        String deleteProd = "DELETE FROM produto WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return -2;

            ps = conn.prepareStatement(deleteProd);
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return (affected == 1) ? 1 : 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return -2;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    public boolean deleteProduto(int id) {
        return deleteProdutoWithStatus(id) == 1;
    }

    /**
     * parte que ajusta a quantidade do produto somando delta (positivo para entrada, negativo para saída).
     */
    public boolean adjustQuantidadeById(int id, double delta) {
        String sql = "UPDATE produto SET quantidade = quantidade + ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return false;

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, delta);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
    }

    private Produto mapResultSetToProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNomeDoador(rs.getString("nome_doador"));
        p.setTelefone(rs.getString("telefone"));
        p.setEmail(rs.getString("email"));
        p.setDescricao(rs.getString("descricao"));
        p.setMarca(rs.getString("marca"));
        p.setQuantidade(rs.getDouble("quantidade"));
        p.setAnimal(rs.getString("animal"));
        p.setTipo(rs.getString("tipo"));
        p.setPacoteFechado(rs.getString("pacote_fechado"));
        Date dt = rs.getDate("data_doacao");
        if (dt != null) {
            p.setDataDoacao(dt.toLocalDate());
        } else {
            p.setDataDoacao(null);
        }

        p.setProdutoDescricao(rs.getString("descricao"));

        return p;
    }

    public List<Produto> getMovimentacoesAsProdutos() {
        String sql = ""
            + "SELECT m.id AS mov_id, m.produto_id, m.produto_nome AS produto_nome_snapshot, "
            + "m.tipo, m.quantidade, m.responsavel, m.observacao, m.data_movimentacao, "
            + "p.descricao AS produto_descricao_from_produto "
            + "FROM movimentacao m LEFT JOIN produto p ON m.produto_id = p.id "
            + "ORDER BY m.data_movimentacao DESC, m.id DESC";

        List<Produto> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConectaDB.conectar();
            if (conn == null) return lista;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Produto mov = new Produto();

                // usamos id do Produto para armazenar o id da movimentação (mov_id)
                mov.setId(rs.getInt("mov_id"));

                // prioriza snapshot salvo pelo trigger
                String snapshot = rs.getString("produto_nome_snapshot");
                if (snapshot != null && !snapshot.trim().isEmpty()) {
                    mov.setProdutoDescricao(snapshot);
                } else {
                    mov.setProdutoDescricao(rs.getString("produto_descricao_from_produto"));
                }

                // reutiliza dataDoacao para guardar a data da movimentação (opcional)
                Date d = rs.getDate("data_movimentacao");
                if (d != null) mov.setDataDoacao(d.toLocalDate());

                double qtd = rs.getDouble("quantidade");
                if (!rs.wasNull()) mov.setQuantidade(qtd);

                lista.add(mov);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignore) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignore) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
        }
        return lista;
    }
}
