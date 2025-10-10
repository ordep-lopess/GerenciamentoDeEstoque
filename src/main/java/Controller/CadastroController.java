/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import models.Login;
import dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @author pedroH, bianca
 */

@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Valida se a senha atende aos requisitos
    private boolean isPasswordValid(String senha) {
        if (senha == null) return false;
        return senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome        = request.getParameter("nome");
        String email       = request.getParameter("email");
        String senha       = request.getParameter("senha");
        String senhaRepeat = request.getParameter("senhaRepeat");
        String rua         = request.getParameter("rua");
        String num         = request.getParameter("num");   // deve bater com name="num" no form
        String cep         = request.getParameter("cep");

        System.out.println("[CadastroController] dados recebidos: nome=" + nome + ", email=" + email);

        // 1) Verifica se as senhas conferem
        if (!senha.equals(senhaRepeat)) {
            System.out.println("[CadastroController] senhas não conferem");
            request.setAttribute("erro", "As senhas não conferem.");
            request.getRequestDispatcher("/Views/cadastro.jsp").forward(request, response);
            return;
        }

        // 2) Verifica requisitos da senha
        if (!isPasswordValid(senha)) {
            System.out.println("[CadastroController] senha inválida");
            request.setAttribute("erro",
              "A senha deve ter pelo menos 8 caracteres, incluir 1 letra maiúscula, "
            + "1 minúscula e 1 caractere especial.");
            request.getRequestDispatcher("/Views/cadastro.jsp").forward(request, response);
            return;
        }

        try {
            // 3) Criptografia e hash
            String hashedSenha    = EncryptionUtil.hashSHA256(senha);
            String encryptedEmail = EncryptionUtil.encrypt(email);

            // 4) Monta o objeto de domínio
            Login login = new Login();
            login.setNome(nome);
            login.setEmail(encryptedEmail);
            login.setSenha(hashedSenha);
            login.setRua(rua);
            login.setNum(num);
            login.setCep(cep);

            // 5) Persiste no banco
            UsuarioDAO dao = new UsuarioDAO();
            boolean inseriu = dao.salvar(login);
            System.out.println("[CadastroController] inseriu? " + inseriu);

            if (inseriu) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                request.setAttribute("erro", "Não foi possível cadastrar o usuário.");
                request.getRequestDispatcher("/Views/cadastro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            request.getRequestDispatcher("/Views/cadastro.jsp").forward(request, response);
        }
    }

    // Classe interna para hashing e criptografia AES
    public static class EncryptionUtil {
        private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
        private static final byte[]   KEY_BYTES         =
            { 'S','e','c','r','e','t','K','e','y','1','2','3','4','5','6','7' };

        // Gera hash SHA-256
        public static String hashSHA256(String input) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

        // Encripta com AES/ECB/PKCS5Padding
        public static String encrypt(String plaintext) throws Exception {
            SecretKeySpec key = new SecretKeySpec(KEY_BYTES, "AES");
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        }

        // Desencripta texto AES
        public static String decrypt(String ciphertext) throws Exception {
            SecretKeySpec key = new SecretKeySpec(KEY_BYTES, "AES");
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded   = Base64.getDecoder().decode(ciphertext);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        }
    }
}
