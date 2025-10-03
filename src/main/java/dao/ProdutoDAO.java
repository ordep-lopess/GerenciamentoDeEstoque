package dao;

import models.Produto;
import util.ConectaDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por CRUD de Produto (doação de alimento).
 * Tabela: produto(id, nome, contato, descricao, marca, quantidade, animal)
 */
public class ProdutoDAO {

    /**
     * Insere uma nova doação na tabela e preenche o ID gerado em produto.id.
     * @param produto Objeto contendo nome, contato, descrição, marca, quantidade e animal.
     * @return true se inseriu com sucesso, false caso contrário.
     */
    public boolean insProduto(Produto produto) {
    String sql =
        "INSERT INTO produto(nome, contato, descricao, marca, quantidade, animal) " +
        "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getContato());
            ps.setString(3, produto.getDescricao());
            ps.setString(4, produto.getMarca());
            ps.setDouble(5, produto.getQuantidade());
            ps.setString(6, produto.getAnimal());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return false;
            }
            // captura o ID gerado
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
     * Busca uma doação pelo seu ID.
     * @param id Identificador da doação.
     * @return Produto preenchido ou null se não encontrar/erro.
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
                    p.setNome(rs.getString("nome"));
                    p.setContato(rs.getString("contato"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setMarca(rs.getString("marca"));
                    p.setQuantidade(rs.getDouble("quantidade"));
                    p.setAnimal(rs.getString("animal"));
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
     * @param nome Nome do doador.
     * @return Produto preenchido ou null se não encontrar/erro.
     */
    public Produto getProdutoByNome(String nome) {
        String sql = "SELECT * FROM produto WHERE nome = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setContato(rs.getString("contato"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setMarca(rs.getString("marca"));
                    p.setQuantidade(rs.getDouble("quantidade"));
                    p.setAnimal(rs.getString("animal"));
                    return p;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas as doações cadastradas.
     * @return Lista de Produto (pode ser vazia).
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
                p.setNome(rs.getString("nome"));
                p.setContato(rs.getString("contato"));
                p.setDescricao(rs.getString("descricao"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getDouble("quantidade"));
                p.setAnimal(rs.getString("animal"));
                lista.add(p);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Atualiza os dados de uma doação existente.
     * @param produto Produto com ID e novos valores.
     * @return true se alterou 1 registro, false caso contrário.
     */
    public boolean updateProduto(Produto produto) {
    String sql =
        "UPDATE produto " +
        "SET nome = ?, contato = ?, descricao = ?, " +
        "marca = ?, quantidade = ?, animal = ? " +
        "WHERE id = ?";
        try (Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getContato());
            ps.setString(3, produto.getDescricao());
            ps.setString(4, produto.getMarca());
            ps.setDouble(5, produto.getQuantidade());
            ps.setString(6, produto.getAnimal());
            ps.setInt(7, produto.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Exclui uma doação pelo ID.
     * @param id Identificador da doação a remover.
     * @return true se excluiu 1 registro, false caso contrário.
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
