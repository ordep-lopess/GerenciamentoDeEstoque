package dao;

import models.Produto;
import util.ConectaDB;

import java.sql.*;
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
            // assumindo que Produto.getDataDoacao() retorna java.time.LocalDate
            ps.setDate(10, Date.valueOf(produto.getDataDoacao()));

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

    /**
     * Busca uma doação pelo seu ID, retornando todos os campos.
     */
    public Produto getProdutoById(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                    // converte java.sql.Date para java.time.LocalDate
                    Date dt = rs.getDate("data_doacao");
                    p.setDataDoacao(dt.toLocalDate());
                    return p;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Busca uma doação pelo nome do doador.
     */
    public Produto getProdutoByNome(String nome) {
        String sql = "SELECT * FROM produto WHERE nome_doador = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                    p.setDataDoacao(dt.toLocalDate());
                    return p;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas as doações cadastradas, com todos os atributos.
     */
    public List<Produto> getAllProdutos() {
        String sql = "SELECT * FROM produto ORDER BY id";
        List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                p.setDataDoacao(dt.toLocalDate());
                lista.add(p);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Atualiza os dados de uma doação existente.
     */
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
            ps.setDate(10, Date.valueOf(produto.getDataDoacao()));
            ps.setInt(11, produto.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Exclui uma doação pelo ID.
     */
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
}
