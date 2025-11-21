package Controller;

import dao.ProdutoDAO;
import models.Produto;
import models.Login;
import util.ConectaDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * ProdutoServlet com suporte a movimentações mantidas na sessão (sem tabela movimentacao).
 * Mapeado para /produto e /movimentar.
 *
 * Substitua o arquivo atual por este, faça Clean & Build e redeploy.
 */
@WebServlet({"/produto", "/movimentar"})
public class ProdutoServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private final ProdutoDAO dao = new ProdutoDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String servletPath = request.getServletPath(); // "/produto" ou "/movimentar"
    String action = request.getParameter("action");
    if (action == null || action.isBlank()) action = "list";

    // nome do usuário para header (se houver sessão)
    HttpSession session = request.getSession(false);
    String nomeUsuario = "Visitante";
    if (session != null) {
      Login usuario = (Login) session.getAttribute("usuario");
      if (usuario != null && usuario.getNome() != null) {
        nomeUsuario = usuario.getNome();
      }
    }
    request.setAttribute("nomeUsuario", nomeUsuario);

    try {
      if ("/movimentar".equalsIgnoreCase(servletPath)) {
        handleMovimentarGet(request, response, action);
      } else {
        handleProdutoGet(request, response, action);
      }
    } catch (Exception e) {
      request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
      request.getRequestDispatcher("/Views/lista.jsp").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String servletPath = request.getServletPath();
    String action = request.getParameter("action");
    if (action == null) action = "";

    try {
      if ("/movimentar".equalsIgnoreCase(servletPath)) {
        handleMovimentarPost(request, response, action);
      } else {
        handleProdutoPost(request, response, action);
      }
    } catch (NumberFormatException nfe) {
      request.setAttribute("mensagem", "ID ou quantidade inválida.");
      request.getRequestDispatcher("/Views/lista.jsp").forward(request, response);
    } catch (Exception ex) {
      request.setAttribute("mensagem", "Erro: " + ex.getMessage());
      request.getRequestDispatcher("/Views/lista.jsp").forward(request, response);
    }
  }

  // ---- Produto handlers ----

  private void handleProdutoGet(HttpServletRequest request,
                                HttpServletResponse response, String action)
      throws ServletException, IOException {
    switch (action) {
      case "new":
        request.getRequestDispatcher("/Views/cadastroProd.jsp").forward(request, response);
        break;

      case "edit": {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
          // Sem id: mostrar a página alterar.jsp com o formulário de busca
          request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
          return;
        }
        try {
          int id = Integer.parseInt(idParam);
          Produto p = dao.getProdutoById(id);
          if (p != null) {
            request.setAttribute("produto", p);
            request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
          } else {
            request.setAttribute("mensagem", "Doação de ID " + id + " não encontrada.");
            request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
          }
        } catch (NumberFormatException e) {
          request.setAttribute("mensagem", "ID inválido.");
          request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
        }
        break;
      }

      case "deleteConfirm":
        try {
          int id = Integer.parseInt(request.getParameter("id"));
          Produto p = dao.getProdutoById(id);
          request.setAttribute("produto", p);
          request.getRequestDispatcher("/Views/excluirProd.jsp").forward(request, response);
        } catch (NumberFormatException e) {
          response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=invalidid");
        }
        break;

      case "consulta":
        request.getRequestDispatcher("/Views/consulta.jsp").forward(request, response);
        break;

      case "list":
      default:
        List<Produto> produtos = dao.getAllProdutos();
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("/Views/lista.jsp").forward(request, response);
        break;
    }
  }

  private void handleProdutoPost(HttpServletRequest request,
                                 HttpServletResponse response, String action)
      throws ServletException, IOException {
    switch (action.toLowerCase()) {

      case "create":
        Produto pCreate = buildProdutoFromRequest(request);
        boolean okCreate = dao.insProduto(pCreate);
        if (okCreate) {
          response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=created");
        } else {
          request.setAttribute("mensagem", "Falha ao cadastrar doação.");
          request.getRequestDispatcher("/Views/cadastroProd.jsp").forward(request, response);
        }
        break;

      case "update":
        try {
          Produto pUpdate = buildProdutoFromRequest(request);
          String idS = request.getParameter("id");
          pUpdate.setId(Integer.parseInt(idS));
          boolean okUpdate = dao.updateProduto(pUpdate);
          if (okUpdate) {
            response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=updated");
          } else {
            request.setAttribute("mensagem", "Falha ao atualizar doação.");
            request.setAttribute("produto", pUpdate);
            request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
          }
        } catch (NumberFormatException nfe) {
          request.setAttribute("mensagem", "ID inválido.");
          request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
        }
        break;

      case "delete":
        int id = Integer.parseInt(request.getParameter("id"));
        boolean okDelete = dao.deleteProduto(id);
        if (okDelete) {
          response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=deleted");
        } else {
          response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=deletefail");
        }
        break;

      case "consulta":
        String ident = request.getParameter("id");
        String mensagem = null;
        Produto resultado = null;
        if (ident == null || ident.trim().isEmpty()) {
          mensagem = "Informe um ID válido.";
        } else {
          try {
            int idConsulta = Integer.parseInt(ident.trim());
            resultado = dao.getProdutoById(idConsulta);
            if (resultado == null) {
              mensagem = "Doação não encontrada para o ID " + idConsulta;
            }
          } catch (NumberFormatException e) {
            mensagem = "ID inválido!";
          } catch (Exception e) {
            mensagem = "Erro ao consultar: " + e.getMessage();
          }
        }
        request.setAttribute("resultado", resultado);
        request.setAttribute("mensagem", mensagem);
        request.getRequestDispatcher("/Views/consulta.jsp").forward(request, response);
        break;

      default:
        response.sendRedirect(request.getContextPath() + "/produto?action=list");
        break;
    }
  }

  // ---- Movimentar handlers mantidos em sessão ----

  @SuppressWarnings("unchecked")
  private void handleMovimentarGet(HttpServletRequest request,
                                   HttpServletResponse response, String action)
      throws ServletException, IOException {

    // carregar produtos para popular select
    List<Produto> produtos = dao.getAllProdutos();
    request.setAttribute("produtos", produtos);

    // recuperar lista de movimentações mantida na sessão (fallback: lista vazia)
    HttpSession session = request.getSession();
    List<Map<String, Object>> movimentacoes = (List<Map<String, Object>>) session.getAttribute("movimentacoes");
    if (movimentacoes == null) {
      movimentacoes = new ArrayList<>();
      session.setAttribute("movimentacoes", movimentacoes);
    }

    // DEBUG no console
    System.out.println("DEBUG(session) movimentacoes.size() = " + movimentacoes.size());
    if (!movimentacoes.isEmpty()) {
      System.out.println("DEBUG(session) first movimentacao keys = " + movimentacoes.get(0).keySet());
    }

    request.setAttribute("movimentacoes", movimentacoes);
    request.getRequestDispatcher("/Views/movimentar.jsp").forward(request, response);
  }

  private void handleMovimentarPost(HttpServletRequest request,
                                    HttpServletResponse response, String action)
      throws ServletException, IOException {
    if ("create".equalsIgnoreCase(action)) {
      int produtoId = Integer.parseInt(request.getParameter("produtoId"));
      String tipo = request.getParameter("tipo"); // "entrada" ou "saida"
      double quantidade = Double.parseDouble(request.getParameter("quantidade"));
      String responsavel = request.getParameter("responsavel");
      String observacao = request.getParameter("observacao");

      // 1) atualiza quantidade do produto no banco usando o DAO (delta positivo/negativo)
      double delta = "entrada".equalsIgnoreCase(tipo) ? quantidade : -quantidade;
      boolean updated = dao.adjustQuantidadeById(produtoId, delta);

      // 2) cria um registro de movimentação em memória (sessão)
      HttpSession session = request.getSession();
      @SuppressWarnings("unchecked")
      List<Map<String, Object>> movimentacoes = (List<Map<String, Object>>) session.getAttribute("movimentacoes");
      if (movimentacoes == null) {
        movimentacoes = new ArrayList<>();
        session.setAttribute("movimentacoes", movimentacoes);
      }

      Map<String, Object> m = new HashMap<>();
      m.put("id", System.currentTimeMillis()); // id temporário único
      m.put("produtoId", produtoId);

      // tenta buscar dados do produto para exibir na lista
      Produto p = dao.getProdutoById(produtoId);
      if (p != null) {
        m.put("produtoDescricao", p.getDescricao());
        m.put("produtoNomeDoador", p.getNomeDoador());
        m.put("produto", p);
      }
      m.put("tipo", tipo);
      m.put("quantidade", quantidade);
      m.put("dataMovimentacao", LocalDate.now());
      m.put("responsavel", responsavel);
      m.put("observacao", observacao);

      // insere no começo da lista (mais recente primeiro)
      movimentacoes.add(0, m);

      // PRG: redireciona para recarregar a lista (GET)
      response.sendRedirect(request.getContextPath() + "/movimentar?action=list");
      return;
    }

    // fallback
    response.sendRedirect(request.getContextPath() + "/movimentar?action=list");
  }

  // ---- util ----

  private Produto buildProdutoFromRequest(HttpServletRequest request) {
    Produto p = new Produto();
    p.setNomeDoador(safeTrim(request.getParameter("nomeDoador")));
    p.setTelefone(safeTrim(request.getParameter("telefone")));
    p.setEmail(safeTrim(request.getParameter("email")));
    p.setDescricao(safeTrim(request.getParameter("descricao")));
    p.setMarca(safeTrim(request.getParameter("marca")));
    String qtd = request.getParameter("quantidade");
    if (qtd != null && !qtd.isBlank()) {
      p.setQuantidade(Double.parseDouble(qtd));
    } else {
      p.setQuantidade(0.0);
    }
    p.setAnimal(safeTrim(request.getParameter("animal")));
    p.setTipo(safeTrim(request.getParameter("tipo")));
    p.setPacoteFechado(safeTrim(request.getParameter("pacoteFechado")));
    String data = safeTrim(request.getParameter("dataDoacao"));
    if (data != null && !data.isBlank()) {
      p.setDataDoacao(LocalDate.parse(data));
    } else {
      p.setDataDoacao(LocalDate.now());
    }
    return p;
  }

  private String safeTrim(String s) {
    return (s == null) ? null : s.trim();
  }
}
