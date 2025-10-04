package dao;

import models.Login;
import java.sql.*;

public class UsuarioDAO {

    // Cria a conexão com o MySQL
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bancoo?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, "root", "");
    }

    // Persiste o usuário; retorna true se inseriu pelo menos 1 linha
    public boolean salvar(Login usuario) throws Exception {
        System.out.println("[UsuarioDAO] iniciar salvar() para email=" + usuario.getEmail());
        String sql = "INSERT INTO usuarios "
                   + "(nome, email, senha, rua, num, cep) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getRua());
            ps.setString(5, usuario.getNum());
            ps.setString(6, usuario.getCep());

            int rows = ps.executeUpdate();
            System.out.println("[UsuarioDAO] linhas inseridas = " + rows);
            return rows > 0;
        }
    }

    // Autentica usuário pelo email e senha
    public Login autenticar(String email, String senha) throws Exception {
        System.out.println("[UsuarioDAO] autenticar() para email=" + email);
        String sql = "SELECT id, nome, email, senha, rua, num, cep "
                   + "FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setId(rs.getInt("id"));
                    login.setNome(rs.getString("nome"));
                    login.setEmail(rs.getString("email"));
                    login.setSenha(rs.getString("senha"));
                    login.setRua(rs.getString("rua"));
                    login.setNum(rs.getString("num"));
                    login.setCep(rs.getString("cep"));
                    return login;
                }
            }
        }
        return null;
    }
}
