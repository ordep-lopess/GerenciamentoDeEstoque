package Controller;

import models.Login;
import dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
        // Recupera os dados do formulário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        System.out.println("[LoginController] Tentativa de login - Email: " + email);

        try {
            // Criptografa o email e gera o hash da senha
            String encryptedEmail = CadastroController.EncryptionUtil.encrypt(email);
            String hashedSenha    = CadastroController.EncryptionUtil.hashSHA256(senha);

            UsuarioDAO dao = new UsuarioDAO();
            Login usuario = dao.autenticar(encryptedEmail, hashedSenha);

            if (usuario != null) {
                System.out.println("[LoginController] Login válido. Usuário: " + usuario.getNome());

                // Descriptografa o e-mail para exibição
                try {
                    String decryptedEmail = CadastroController.EncryptionUtil.decrypt(usuario.getEmail());
                    usuario.setEmail(decryptedEmail);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Armazena o usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Redireciona para /Views/inicio.jsp em caso de sucesso
                response.sendRedirect(request.getContextPath() + "/Views/inicio.jsp");
                return;
            } 

            // Login inválido: volta para a tela de login com mensagem de erro
            System.out.println("[LoginController] Login inválido.");
            request.setAttribute("erro", "Email ou senha incorretos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro de criptografia: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Ocorreu um erro: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
