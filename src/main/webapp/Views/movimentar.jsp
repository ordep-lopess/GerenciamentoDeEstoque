<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8"/>
  <title>Movimentações</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/reset.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/index.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/lista.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/voltar.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/movimentacao.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/CSS/header.css"/>
  
</head>
<body>
 <header class="user-header">
    <span class="greeting">
      Olá,
      <c:choose>
        <c:when test="${not empty sessionScope.usuario and not empty sessionScope.usuario.nome}">
          <c:out value="${sessionScope.usuario.nome}" />
        </c:when>
        <c:otherwise>Visitante</c:otherwise>
      </c:choose>
    </span>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Sair</a>
  </header>

<h1 class="title" style="margin-bottom:12px">Movimentações</h1>

<main class="main">
  <form id="filterForm" method="get" action="${pageContext.request.contextPath}/movimentar" class="toolbar" aria-label="Filtro e exportação">
    <div class="filter-group">
      <input type="hidden" name="action" value="list"/>

      <label class="filter-label" style="min-width:220px;">
        <span class="label-top">Filtrar por</span>
        <select id="filterMode" name="mode" class="mode-select" aria-label="Filtrar por">
          <option value="" <c:if test="${param.mode == null || param.mode == ''}">selected</c:if>>Selecione</option>
          <option value="date" <c:if test="${param.mode == 'date'}">selected</c:if>>Data</option>
          <option value="type" <c:if test="${param.mode == 'type'}">selected</c:if>>Tipo de movimentação</option>
        </select>
      </label>

      <div id="modeControls" style="display:flex; gap:12px; align-items:flex-end;">
        <!-- Data controls -->
        <div id="dateControls" style="display:none; gap:12px; align-items:flex-end;">
          <label class="filter-label">
            <span class="label-top">De</span>
            <input id="fromDate" class="filter-input" type="date" name="from" value="${param.from}" aria-label="Data inicial"/>
          </label>

          <label class="filter-label">
            <span class="label-top">Até</span>
            <input id="toDate" class="filter-input" type="date" name="to" value="${param.to}" aria-label="Data final"/>
          </label>
        </div>

        <div id="typeControls" style="display:none; gap:12px; align-items:flex-end;">
          <label class="filter-label">
            <span class="label-top">Tipo</span>
            <select id="tipoFiltro" name="tipo" class="filter-select" aria-label="Tipo de movimentação">
              <option value="">Todos</option>
              <option value="entrada" <c:if test="${param.tipo == 'entrada'}">selected</c:if>>Entrada</option>
              <option value="saida" <c:if test="${param.tipo == 'saida'}">selected</c:if>>Saída</option>
            </select>
          </label>
        </div>

        <!-- Filtrar + Limpar -->
        <div style="display:flex; gap:8px; align-items:center;">
          <button type="submit" id="applyFilterBtn" class="btn-filter" title="Aplicar filtro">Filtrar</button>

          <button type="button" id="clearFiltersBtn" class="btn-clear" title="Limpar filtros" aria-label="Limpar filtros">
            <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" role="img">
              <path d="M9 3v1H4v2h16V4h-5V3H9zm1 5v9h2V8H10zm4 0v9h2V8h-2zM7 8v9h2V8H7z" />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- Ações (export) -->
    <div class="actions-wrap">
      <c:choose>
        <c:when test="${not empty movimentacoes}">
          <button id="exportCsvBtn" type="button" class="btn-export" title="Exportar Movimentações CSV">
            <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" role="img">
              <path d="M19 3H8c-1.1 0-2 .9-2 2v3H5c-1.1 0-2 .9-2 2v6c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"/>
              <path d="M11 12h2v6h3l-4 4-4-4h3z"/>
            </svg>
            Exportar CSV
          </button>
        </c:when>
        <c:otherwise>
          <button id="exportCsvBtn" type="button" class="btn-export disabled" disabled aria-disabled="true" title="Sem dados para exportar">
            <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" role="img">
              <path d="M19 3H8c-1.1 0-2 .9-2 2v3H5c-1.1 0-2 .9-2 2v6c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"/>
              <path d="M11 12h2v6h3l-4 4-4-4h3z"/>
            </svg>
            Exportar CSV
          </button>
          <span class="export-note">Nenhuma movimentação visível</span>
        </c:otherwise>
      </c:choose>
    </div>
  </form>

  <!-- TABELA -->
  <c:choose>
    <c:when test="${not empty movimentacoes}">
      <table class="table table-body" role="table" aria-label="Lista de movimentações">
        <thead>
          <tr>
            <th class="table-cell">Produto</th>
            <th class="table-cell">Tipo</th>
            <th class="table-cell">Quantidade (kg)</th>
            <th class="table-cell">Data</th>
            <th class="table-cell">Responsável</th>
            <th class="table-cell">Observação</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="m" items="${movimentacoes}">
            <tr>
              <td class="table-cell">
                <c:choose>
                  <c:when test="${not empty m.produtoNome}">
                    <c:out value="${m.produtoNome}"/>
                  </c:when>
                  <c:when test="${not empty m.produtoDescricao}">
                    <c:out value="${m.produtoDescricao}"/>
                  </c:when>
                  <c:when test="${not empty m['produto_nome']}">
                    <c:out value="${m['produto_nome']}"/>
                  </c:when>
                  <c:when test="${not empty m.produto}">
                    <c:out value="${m.produto.descricao}"/>
                  </c:when>
                  <c:otherwise>-</c:otherwise>
                </c:choose>
              </td>

              <td class="table-cell"><c:out value="${m.tipo}"/></td>
              <td class="table-cell"><c:out value="${m.quantidade}"/></td>
              <td class="table-cell"><c:out value="${m.dataMovimentacao}"/></td>
              <td class="table-cell"><c:out value="${m.responsavel}"/></td>
              <td class="table-cell"><c:out value="${m.observacao}"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>
      <p class="empty-message">Nenhuma movimentação registrada.</p>
    </c:otherwise>
  </c:choose>

  <div class="btn-row" style="margin-top:18px;">
    <a href="${pageContext.request.contextPath}/Views/inicio.jsp" class="back-btn">← Voltar</a>
    <div class="spacer"></div>
    <a href="#" id="openModal" class="back-btn small-right color-yellow">+ Nova Movimentação</a>
  </div>
</main>

<div id="modalBackdrop" class="modal-backdrop" role="dialog" aria-hidden="true" style="display:none;">
  <div class="modal" role="document">
    <div class="modal-header">
      <h2 class="title">Nova Movimentação</h2>
      <button class="modal-close" id="closeModal" aria-label="Fechar">&times;</button>
    </div>

    <form id="movForm" method="post" action="${pageContext.request.contextPath}/movimentar">
      <input type="hidden" name="action" value="create"/>
      <div class="form-row"><label for="tipo">Tipo de movimentação</label>
        <select id="tipo" name="tipo" class="input" required>
          <option value="">Selecione…</option>
          <option value="entrada">Entrada</option>
          <option value="saida">Saída</option>
        </select>
      </div>

      <div class="form-row">
        <label for="produtoId">Produto</label>
        <select id="produtoId" name="produtoId" class="input" required>
          <option value="">Selecione o produto...</option>
          <c:forEach var="p" items="${produtos}">
            <option value="${p.id}"><c:out value="${p.descricao}"/> - <c:out value="${p.quantidade}"/>kg</option>
          </c:forEach>
        </select>
      </div>

      <div class="form-row">
        <label for="quantidade">Quantidade (kg)</label>
        <input id="quantidade" name="quantidade" type="number" step="0.01" class="input" placeholder="Ex: 2.50" required/>
      </div>

      <div class="form-row">
        <label for="responsavel">Responsável</label>
        <input id="responsavel" name="responsavel" type="text" class="input" placeholder="Nome do responsável (opcional)"/>
      </div>

      <div class="form-row">
        <label for="observacao">Observação</label>
        <input id="observacao" name="observacao" type="text" class="input" placeholder="Observação (opcional)"/>
      </div>

      <div class="modal-actions">
        <button type="button" id="cancelBtn" class="back-btn small-right color-red">Cancelar</button>
        <button type="submit" class="back-btn small-right color-yellow">Salvar</button>
      </div>
    </form>
  </div>
</div>

<script src="${pageContext.request.contextPath}/Views/js/form.js"></script>

</body>
</html>
