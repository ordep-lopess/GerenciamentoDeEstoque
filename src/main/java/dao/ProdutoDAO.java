package dao;

import models.Produto;
import util.ConectaDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean insProduto(Produto produto) {
        String sql = ""
            + "INSERT INTO doacao ("
            + "nome_doador, telefone, email, descricao, marca, quantidade, "
            + "animal, tipo, pacote_fechado, data_doacao"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConectaDB.conectar();
            if (conn == null) {
                System.err.println("ConectaDB retornou null");
                return false;
            }

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
            if (affected == 0) return false;

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
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    public Produto getProdutoById(int id) {
        String sql = "SELECT * FROM doacao WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapProduto(rs);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Produto> getAllProdutos() {
        String sql = "SELECT * FROM doacao ORDER BY id";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (conn == null) return lista;

            while (rs.next()) {
                lista.add(mapProduto(rs));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public boolean updateProduto(Produto p) {
        String sql = ""
            + "UPDATE doacao SET "
            + "nome_doador = ?, telefone = ?, email = ?, descricao = ?, marca = ?, quantidade = ?, "
            + "animal = ?, tipo = ?, pacote_fechado = ?, data_doacao = ? "
            + "WHERE id = ?";

        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, p.getNomeDoador());
            ps.setString(2, p.getTelefone());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDescricao());
            ps.setString(5, p.getMarca());
            ps.setDouble(6, p.getQuantidade());
            ps.setString(7, p.getAnimal());
            ps.setString(8, p.getTipo());
            ps.setString(9, p.getPacoteFechado());

            if (p.getDataDoacao() != null) {
                ps.setDate(10, Date.valueOf(p.getDataDoacao()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            ps.setInt(11, p.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduto(int id) {
        String sql = "DELETE FROM doacao WHERE id = ?";

        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean adjustQuantidadeById(int id, double delta) {
        String sql = "UPDATE doacao SET quantidade = quantidade + ? WHERE id = ?";

        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setDouble(1, delta);
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Produto mapProduto(ResultSet rs) throws SQLException {
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
        }

        return p;
    }

    public String getObservacaoById(int id) {
        String sql = "SELECT observacao FROM doacao_observacao WHERE id_doacao = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("observacao");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
