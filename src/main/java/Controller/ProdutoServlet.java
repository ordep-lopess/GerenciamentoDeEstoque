package Controller;

import dao.ProdutoDAO;
import models.Produto;
import models.Login;
import util.ConectaDB;
import models.ProdutoObservacao;
import dao.ProdutoObservacaoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet({"/produto", "/movimentar"})
public class ProdutoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProdutoDAO dao = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) action = "list";

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
            String export = request.getParameter("export");
            String format = request.getParameter("format");
            if ("/produto".equalsIgnoreCase(servletPath) && export != null) {
                if (format == null || format.isBlank()) format = "csv";
                if (!"csv".equalsIgnoreCase(format)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato inválido");
                    return;
                }
                handleExportProdutosCsv(request, response);
                return;
            }
            if ("/movimentar".equalsIgnoreCase(servletPath) && export != null) {
                if (format == null || format.isBlank()) format = "csv";
                if (!"csv".equalsIgnoreCase(format)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato inválido");
                    return;
                }
                handleExportMovimentacoesCsv(request, response);
                return;
            }

            if ("/movimentar".equalsIgnoreCase(servletPath)) {
                handleMovimentarGet(request, response, action);
            } else {
                handleProdutoGet(request, response, action);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            nfe.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Views/erro.jsp?msg=ID_invalido");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Views/erro.jsp?msg=Erro_interno");
        }
    }

    private void handleProdutoGet(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        switch (action) {
            case "new":
                request.getRequestDispatcher("/Views/cadastroProd.jsp").forward(request, response);
                break;

            case "edit": {
                try {
                    String idStr = request.getParameter("id");
                    // Se não veio id, apenas mostra a página de busca (sem mensagem de erro)
                    if (idStr == null || idStr.isBlank()) {
                        request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
                        return;
                    }


                    int idDoacao = Integer.parseInt(idStr.trim());
                    Produto produto = dao.getProdutoById(idDoacao);

                    if (produto == null) {
                        request.setAttribute("mensagem", "Doação não encontrada.");
                        request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
                        return;
                    }

                    request.setAttribute("produto", produto);

                    // busca observação
                    ProdutoObservacaoDAO obsDao = new ProdutoObservacaoDAO();
                    ProdutoObservacao obs = obsDao.getByProdutoId(idDoacao);
                    request.setAttribute("observacaoProduto", obs);

                    request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("mensagem", "Erro ao carregar doação para edição.");
                    request.getRequestDispatcher("/Views/alterar.jsp").forward(request, response);
                }
                break;
            }

            case "deleteConfirm": {
                try {
                    String idStr = request.getParameter("id");
                    if (idStr == null || idStr.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=invalidid");
                        return;
                    }
                    int id = Integer.parseInt(idStr.trim());
                    Produto p = dao.getProdutoById(id);
                    request.setAttribute("produto", p);

                    request.getRequestDispatcher("/Views/excluirProd.jsp").forward(request, response);

                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=invalidid");
                }
                break;
            }

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

    private void handleProdutoPost(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        switch (action.toLowerCase()) {

            case "create": {
                // monta produto com os campos da tabela principal
                Produto pCreate = buildProdutoFromRequest(request);

                // pega observação do formulário
                String obsTexto = safeTrim(request.getParameter("observacao"));

                boolean okCreate = dao.insProduto(pCreate);

                if (okCreate) {
                    // se cadastrou produto com sucesso e tem observação, salva na tabela secundaria
                    if (obsTexto != null && !obsTexto.isBlank()) {
                        ProdutoObservacaoDAO obsDao = new ProdutoObservacaoDAO();
                        ProdutoObservacao po = new ProdutoObservacao();
                        po.setIdProduto(pCreate.getId()); // id gerado no insert
                        po.setObservacao(obsTexto);
                        obsDao.saveOrUpdate(po);
                    }

                    response.sendRedirect(request.getContextPath() + "/produto?action=list&msg=created");
                } else {
                    request.setAttribute("mensagem", "Falha ao cadastrar doação.");
                    request.getRequestDispatcher("/Views/cadastroProd.jsp").forward(request, response);
                }
                break;
            }

            case "update": {
                try {
                    String idS = request.getParameter("id");
                    int id = Integer.parseInt(idS);

                    // busca produto existente para preservar campos que não vieram no form
                    Produto existente = dao.getProdutoById(id);

                    // monta produto a partir do request (pode vir com quantidade vazia)
                    Produto pUpdate = buildProdutoFromRequest(request);

                    // se o form não enviou quantidade, preserva a atual
                    String qtdParam = request.getParameter("quantidade");
                    if (qtdParam == null || qtdParam.isBlank()) {
                        if (existente != null) {
                            pUpdate.setQuantidade(existente.getQuantidade());
                        } else {
                            pUpdate.setQuantidade(0.0); // fallback seguro
                        }
                    } else {
                        // aceita vírgula como separador decimal
                        pUpdate.setQuantidade(Double.parseDouble(qtdParam.replace(',', '.')));
                    }

                    pUpdate.setId(id);

                    // pega observação do formulário
                    String obsTexto = safeTrim(request.getParameter("observacao"));

                    boolean okUpdate = dao.updateProduto(pUpdate);

                    if (okUpdate) {
                        ProdutoObservacaoDAO obsDao = new ProdutoObservacaoDAO();

                        if (obsTexto != null && !obsTexto.isBlank()) {
                            ProdutoObservacao po = new ProdutoObservacao();
                            po.setIdProduto(id);
                            po.setObservacao(obsTexto);
                            obsDao.saveOrUpdate(po);
                        } else {
                            obsDao.deleteByProdutoId(id);
                        }

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
            }

            case "delete": {
                String idParam = request.getParameter("id");
                String result;
                if (idParam == null || idParam.trim().isEmpty()) {
                    result = "invalidid";
                    response.sendRedirect(request.getContextPath() + "/Views/excluirProd.jsp?result=" + result);
                    break;
                }

                int id;
                try {
                    id = Integer.parseInt(idParam.trim());
                } catch (NumberFormatException nfe) {
                    result = "invalidid";
                    response.sendRedirect(request.getContextPath() + "/Views/excluirProd.jsp?result=" + result);
                    break;
                }

                try {
                    boolean okDelete = dao.deleteProduto(id);
                    if (okDelete) {
                        result = "deleted";
                        response.sendRedirect(request.getContextPath() + "/Views/excluirProd.jsp?result=" + result);
                    } else {
                        result = "notfound";
                        response.sendRedirect(request.getContextPath() + "/Views/excluirProd.jsp?result=" + result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    result = "deletefail";
                    response.sendRedirect(request.getContextPath() + "/Views/excluirProd.jsp?result=" + result);
                }
                break;
            }

            case "consulta": {
                String ident = request.getParameter("id");
                String mensagem = null;
                Produto resultado = null;
                String observacao = null;

                if (ident == null || ident.trim().isEmpty()) {
                    mensagem = "Informe um ID válido.";
                } else {
                    try {
                        int idConsulta = Integer.parseInt(ident.trim());
                        resultado = dao.getProdutoById(idConsulta);

                        if (resultado == null) {
                            mensagem = "Doação não encontrada para o ID " + idConsulta;
                        } else {
                            // BUSCAR OBSERVAÇÃO DA DOAÇÃO
                            observacao = dao.getObservacaoById(idConsulta);
                        }

                    } catch (NumberFormatException e) {
                        mensagem = "ID inválido!";
                    } catch (Exception e) {
                        mensagem = "Erro ao consultar: " + e.getMessage();
                        e.printStackTrace();
                    }
                }

                // ENVIAR TUDO PARA A JSP
                request.setAttribute("resultado", resultado);
                request.setAttribute("observacao", observacao);
                request.setAttribute("mensagem", mensagem);

                request.getRequestDispatcher("/Views/consulta.jsp").forward(request, response);
                break;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/produto?action=list");
                break;
        }
    }

    private void handleMovimentarGet(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        try {
            // carregar produtos para popular select do modal
            List<Produto> produtos = dao.getAllProdutos();
            request.setAttribute("produtos", produtos);

            // parâmetros de filtro
            String mode = request.getParameter("mode"); // ", "date", "type"
            String tipoParam = request.getParameter("tipo"); // entrada/saida
            String from = request.getParameter("from");
            String to = request.getParameter("to");

            String sqlBase = "SELECT m.id, m.produto_id, m.tipo, m.quantidade, m.responsavel, m.observacao, m.data_movimentacao, "
                    + "COALESCE(m.produto_nome, d.descricao) AS produto_descricao "
                    + "FROM movimentacao m LEFT JOIN doacao d ON m.produto_id = d.id ";

            List<String> whereClauses = new ArrayList<>();
            List<Object> sqlParams = new ArrayList<>();

            if ("type".equalsIgnoreCase(mode) && tipoParam != null && !tipoParam.isBlank()) {
                whereClauses.add("m.tipo = ? ");
                sqlParams.add(tipoParam);
            } else {
                if (from != null && !from.isBlank()) {
                    whereClauses.add("m.data_movimentacao >= ? ");
                    sqlParams.add(java.sql.Date.valueOf(from));
                }
                if (to != null && !to.isBlank()) {
                    whereClauses.add("m.data_movimentacao <= ? ");
                    sqlParams.add(java.sql.Date.valueOf(to));
                }
            }

            String where = whereClauses.isEmpty() ? "" : " WHERE " + String.join(" AND ", whereClauses);
            String sql = sqlBase + where + " ORDER BY m.data_movimentacao DESC, m.id DESC";

            List<Map<String, Object>> movimentacoes = new ArrayList<>();

            try (Connection conn = ConectaDB.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                for (int i = 0; i < sqlParams.size(); i++) {
                    Object p = sqlParams.get(i);
                    if (p instanceof java.sql.Date) {
                        ps.setDate(i + 1, (java.sql.Date) p);
                    } else {
                        ps.setString(i + 1, String.valueOf(p));
                    }
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", rs.getInt("id"));
                        map.put("produtoId", rs.getInt("produto_id"));
                        map.put("produtoDescricao", rs.getString("produto_descricao"));
                        map.put("tipo", rs.getString("tipo"));
                        map.put("quantidade", rs.getDouble("quantidade"));
                        java.sql.Date dmov = rs.getDate("data_movimentacao");
                        map.put("dataMovimentacao", dmov != null ? dmov.toLocalDate().toString() : null);
                        map.put("responsavel", rs.getString("responsavel"));
                        map.put("observacao", rs.getString("observacao"));
                        movimentacoes.add(map);
                    }
                }
            }

            request.setAttribute("movimentacoes", movimentacoes);
            request.getRequestDispatcher("/Views/movimentar.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleMovimentarPost(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        if ("create".equalsIgnoreCase(action)) {
            int produtoId = Integer.parseInt(request.getParameter("produtoId"));
            String tipo = request.getParameter("tipo");
            double quantidade = Double.parseDouble(request.getParameter("quantidade"));
            String responsavel = request.getParameter("responsavel");
            String observacao = request.getParameter("observacao");

            Connection conn = null;
            try {
                conn = ConectaDB.conectar();
                if (conn == null) throw new SQLException("Conexão nula");
                conn.setAutoCommit(false);

                String sqlUpdate = "UPDATE doacao SET quantidade = quantidade + ? WHERE id = ?";

                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    double delta = "entrada".equalsIgnoreCase(tipo) ? quantidade : -quantidade;
                    ps.setDouble(1, delta);
                    ps.setInt(2, produtoId);
                    int updated = ps.executeUpdate();
                    if (updated != 1) {
                        throw new SQLException("Doação não encontrada ou atualização falhou (id=" + produtoId + ")");
                    }
                }

                String sqlIns = "INSERT INTO movimentacao (produto_id, tipo, quantidade, responsavel, observacao, data_movimentacao) VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps2 = conn.prepareStatement(sqlIns, Statement.RETURN_GENERATED_KEYS)) {
                    ps2.setInt(1, produtoId);
                    ps2.setString(2, tipo);
                    ps2.setDouble(3, quantidade);
                    ps2.setString(4, responsavel);
                    ps2.setString(5, observacao);
                    ps2.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    int rows = ps2.executeUpdate();
                    if (rows == 0) {
                        throw new SQLException("Falha ao inserir movimentacao");
                    }
                }

                conn.commit();
                response.sendRedirect(request.getContextPath() + "/movimentar?action=list");
                return;
            } catch (Exception e) {
                if (conn != null) {
                    try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
                }
                throw new ServletException(e);
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (Exception ignore) { /* ignore */ }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/movimentar?action=list");
    }

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

    private void handleExportProdutosCsv(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT id, nome_doador, telefone, email, descricao, marca, quantidade, animal, tipo, pacote_fechado, data_doacao FROM doacao ORDER BY id";

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/csv; charset=UTF-8");
        String filename = "produtos.csv";
        String requestedFilename = req.getParameter("filename");

        if (requestedFilename != null && !requestedFilename.isBlank()) filename = requestedFilename;

        resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (PrintWriter out = resp.getWriter();
             Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            out.println("id;nome_doador;telefone;email;descricao;marca;quantidade;animal;tipo;pacote_fechado;data_doacao");

            DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE;

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = sanitizeCsv(rs.getString("nome_doador"));
                String tel = sanitizeCsv(rs.getString("telefone"));
                String email = sanitizeCsv(rs.getString("email"));
                String desc = sanitizeCsv(rs.getString("descricao"));
                String marca = sanitizeCsv(rs.getString("marca"));
                double qtd = rs.getDouble("quantidade");
                String animal = sanitizeCsv(rs.getString("animal"));
                String tipo = sanitizeCsv(rs.getString("tipo"));
                String pacote = sanitizeCsv(rs.getString("pacote_fechado"));
                java.sql.Date d = rs.getDate("data_doacao");
                String data = d != null ? d.toLocalDate().format(df) : "";
                out.printf("%d;%s;%s;%s;%s;%s;%.3f;%s;%s;%s;%s%n", id, nome, tel, email, desc, marca, qtd, animal, tipo, pacote, data);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleExportMovimentacoesCsv(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mode = req.getParameter("mode");
        String tipo = req.getParameter("tipo");
        String from = req.getParameter("from");
        String to = req.getParameter("to");

        String sqlBase = "SELECT m.id, m.produto_id, COALESCE(m.produto_nome, d.descricao) AS produto_descricao, m.tipo, m.quantidade, m.responsavel, m.observacao, m.data_movimentacao, m.created_at "
                + "FROM movimentacao m LEFT JOIN doacao d ON m.produto_id = d.id ";

        List<String> whereClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if ("type".equalsIgnoreCase(mode) && tipo != null && !tipo.isBlank()) {
            whereClauses.add("m.tipo = ? ");
            params.add(tipo);
        } else {
            if (from != null && !from.isBlank()) {
                whereClauses.add("m.data_movimentacao >= ? ");
                params.add(java.sql.Date.valueOf(from));
            }
            if (to != null && !to.isBlank()) {
                whereClauses.add("m.data_movimentacao <= ? ");
                params.add(java.sql.Date.valueOf(to));
            }
        }

        String where = whereClauses.isEmpty() ? "" : " WHERE " + String.join(" AND ", whereClauses);
        String sql = sqlBase + where + " ORDER BY m.data_movimentacao DESC, m.id DESC";

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/csv; charset=UTF-8");
        String filename = "movimentacoes.csv";
        String requestedFilename = req.getParameter("filename");

        if (requestedFilename != null && !requestedFilename.isBlank()) filename = requestedFilename;

        resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (PrintWriter out = resp.getWriter();
             Connection conn = ConectaDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof java.sql.Date) {
                    ps.setDate(i + 1, (java.sql.Date) p);
                } else {
                    ps.setString(i + 1, String.valueOf(p));
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                out.println("id;produto_id;produto_descricao;tipo;quantidade;responsavel;observacao;data_movimentacao;created_at");

                DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int pid = rs.getInt("produto_id");
                    String pdesc = sanitizeCsv(rs.getString("produto_descricao"));
                    String tipoRes = sanitizeCsv(rs.getString("tipo"));
                    double qtd = rs.getDouble("quantidade");
                    String respName = sanitizeCsv(rs.getString("responsavel"));
                    String obs = sanitizeCsv(rs.getString("observacao"));
                    java.sql.Date dmov = rs.getDate("data_movimentacao");
                    Timestamp created = rs.getTimestamp("created_at");

                    String dataMov = dmov != null ? dmov.toLocalDate().format(df) : "";
                    String createdAt = created != null ? created.toString() : "";

                    out.printf("%d;%d;%s;%s;%.3f;%s;%s;%s;%s%n", id, pid, pdesc, tipoRes, qtd, respName, obs, dataMov, createdAt);
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private String sanitizeCsv(String s) {
        if (s == null) return "";
        return s.replace("\r", " ").replace("\n", " ").replace(";", ",").replace("\"", "");
    }
}
