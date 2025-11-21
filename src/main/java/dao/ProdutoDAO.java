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
            + "INSERT INTO produto ("
            +   "nome_doador, telefone, email, descricao, marca, "
            +   "quantidade, animal, tipo, pacote_fechado, data_doacao"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, produto.getNomeDoador());
            ps.setString(2, produto.getTelefone());
            ps.setString(3, produto.getEmail());
            ps.setString(4, produto.getDescricao());
            ps.setString(5, produto.getMarca());
            ps.setDouble(6, produto.getQuantidade());
            ps.setString(7, produto.getAnimal());
            ps.setString(8, produto.getTipo());
            ps.setString(9, produto.getPacoteFechado());
            // aceita null para dataDoacao
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
        }
    }

    public Produto getProdutoById(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduto(rs);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Produto> getAllProdutos() {
        String sql = "SELECT * FROM produto ORDER BY id";
        List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToProduto(rs));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public boolean updateProduto(Produto produto) {
        String sql = ""
            + "UPDATE produto SET "
            +   "nome_doador = ?, telefone = ?, email = ?, descricao = ?, marca = ?, "
            +   "quantidade = ?, animal = ?, tipo = ?, pacote_fechado = ?, data_doacao = ? "
            + "WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

            ps.setInt(11, produto.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Ajusta a quantidade do produto somando delta (positivo para entrada, negativo para saída).
     * Retorna true se atualização ocorreu com sucesso.
     */
    public boolean adjustQuantidadeById(int id, double delta) {
        // Atualiza em uma única query para evitar condições de corrida simples
        String sql = "UPDATE produto SET quantidade = quantidade + ? WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, delta);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Mapear ResultSet para objeto Produto (centraliza conversões e evita duplicação).
     */
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
        return p;
    }
}
