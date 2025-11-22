// form.js - versão corrigida (tratamento seguro de form.action)
document.addEventListener('DOMContentLoaded', function () {
  // elementos
  const filterForm = document.getElementById('filterForm');
  const exportBtn = document.getElementById('exportCsvBtn');
  const filterMode = document.getElementById('filterMode');
  const fromInput = document.getElementById('fromDate');
  const toInput = document.getElementById('toDate');
  const tipoFiltro = document.getElementById('tipoFiltro');
  const clearBtn = document.getElementById('clearFiltersBtn');
  const applyBtn = document.getElementById('applyFilterBtn');

  const openBtn = document.getElementById('openModal');
  const backdrop = document.getElementById('modalBackdrop');
  const closeBtn = document.getElementById('closeModal');
  const cancelBtn = document.getElementById('cancelBtn');

  console.info('form.js inicializado', {
    filterForm: !!filterForm,
    exportBtn: !!exportBtn,
    filterMode: !!filterMode,
    fromDate: !!fromInput,
    toDate: !!toInput,
    tipoFiltro: !!tipoFiltro,
    clearBtn: !!clearBtn,
    openModal: !!openBtn,
    modalBackdrop: !!backdrop
  });

  // ---------- helpers ----------
  function safeString(v) {
    try { return (v === null || v === undefined) ? '' : String(v); } catch (e) { return ''; }
  }

  function getBaseUrl() {
    // tenta obter action via atributo (string) primeiro
    try {
      if (filterForm) {
        const attr = filterForm.getAttribute('action');
        if (attr && attr.trim() !== '') return attr.split('?')[0];
        // fallback para propriedade action (pode ser absolute url)
        const prop = safeString(filterForm.action);
        if (prop && prop.trim() !== '') {
          // se property contém origin+path, normalizamos para pathname+context quando possível
          try {
            const u = new URL(prop, window.location.origin);
            return u.pathname + (u.search ? '' : '');
          } catch (e) {
            return prop.split('?')[0];
          }
        }
      }
    } catch (e) {
      console.warn('getBaseUrl erro ao ler form.action/getAttribute:', e);
    }
    // último recurso: usar o caminho atual
    return window.location.pathname;
  }

  function hideAllModeControls() {
    const dateControls = document.getElementById('dateControls');
    const typeControls = document.getElementById('typeControls');
    if (dateControls) dateControls.style.display = 'none';
    if (typeControls) typeControls.style.display = 'none';
  }

  function showDateControls() {
    const dateControls = document.getElementById('dateControls');
    const typeControls = document.getElementById('typeControls');
    if (dateControls) dateControls.style.display = 'flex';
    if (typeControls) typeControls.style.display = 'none';
  }

  function showTypeControls() {
    const dateControls = document.getElementById('dateControls');
    const typeControls = document.getElementById('typeControls');
    if (dateControls) dateControls.style.display = 'none';
    if (typeControls) typeControls.style.display = 'flex';
  }

  // ---------- inicialização de visibilidade dos controles de filtro ----------
  (function initMode() {
    if (!filterMode) return hideAllModeControls();
    const m = (filterMode.value || '').trim();
    if (m === 'date') showDateControls();
    else if (m === 'type') showTypeControls();
    else hideAllModeControls();
  }());

  if (filterMode) {
    filterMode.addEventListener('change', function () {
      const v = (this.value || '').trim();
      if (v === 'date') showDateControls();
      else if (v === 'type') showTypeControls();
      else hideAllModeControls();
    });
  }

  // ---------- limpar filtros (limpa e submete) ----------
  if (clearBtn && filterForm) {
    clearBtn.addEventListener('click', function (e) {
      e.preventDefault();
      if (filterMode) filterMode.value = '';
      if (fromInput) fromInput.value = '';
      if (toInput) toInput.value = '';
      if (tipoFiltro) tipoFiltro.value = '';

      hideAllModeControls();

      // sobrescreve inputs existentes (garante remoção de query params antigos)
      ['mode', 'from', 'to', 'tipo'].forEach(name => {
        const el = filterForm.querySelector('[name="' + name + '"]');
        if (el) {
          try { el.value = ''; } catch (ex) {}
        } else {
          const h = document.createElement('input');
          h.type = 'hidden';
          h.name = name;
          h.value = '';
          filterForm.appendChild(h);
        }
      });

      console.info('Limpar filtros: submetendo formulário sem filtros');
      filterForm.submit();
    });
  }

  // ---------- export CSV (monta URL a partir de form.action) ----------
  if (exportBtn) {
    exportBtn.addEventListener('click', function (e) {
      e.preventDefault();
      if (exportBtn.disabled || exportBtn.classList.contains('disabled')) {
        console.info('Export desabilitado');
        return;
      }

      const params = new URLSearchParams();
      params.set('export', 'movimentacoes');
      params.set('format', 'csv');

      const mode = filterMode ? (filterMode.value || '').trim() : '';
      if (mode) params.set('mode', mode);

      if (mode === 'type') {
        const t = tipoFiltro ? (tipoFiltro.value || '').trim() : '';
        if (t) params.set('tipo', t);
      } else if (mode === 'date') {
        if (fromInput && fromInput.value) params.set('from', fromInput.value);
        if (toInput && toInput.value) params.set('to', toInput.value);
      }

      // filename opcional
      let filename = 'movimentacoes.csv';
      if (mode === 'type') filename = 'movimentacoes_' + (tipoFiltro && tipoFiltro.value ? tipoFiltro.value : 'todos') + '.csv';
      else {
        const f = fromInput && fromInput.value ? fromInput.value.replace(/-/g, '') : '';
        const t = toInput && toInput.value ? toInput.value.replace(/-/g, '') : '';
        filename = 'movimentacoes' + (f || t ? '_' + (f || '') + (t ? '_to_' + t : '') : '_' + new Date().toISOString().slice(0,10).replace(/-/g,'')) + '.csv';
      }
      params.set('filename', filename);

      const base = getBaseUrl();
      const url = base + '?' + params.toString();

      console.info('Exportando CSV ->', url);
      try {
        const w = window.open(url, '_blank');
        if (!w) window.location.href = url;
      } catch (err) {
        console.error('Erro ao abrir export:', err);
        window.location.href = url;
      }
    });
  }

  // ---------- modal: abrir/fechar ----------
  function showModal() {
    if (!backdrop) return console.warn('modalBackdrop não encontrado');
    backdrop.style.display = 'flex';
    backdrop.setAttribute('aria-hidden', 'false');
    document.body.style.overflow = 'hidden';
  }
  function hideModal() {
    if (!backdrop) return;
    backdrop.style.display = 'none';
    backdrop.setAttribute('aria-hidden', 'true');
    document.body.style.overflow = '';
  }

  if (openBtn) openBtn.addEventListener('click', function (e) { e.preventDefault(); showModal(); });
  if (closeBtn) closeBtn.addEventListener('click', function (e) { e.preventDefault(); hideModal(); });
  if (cancelBtn) cancelBtn.addEventListener('click', function (e) { e.preventDefault(); hideModal(); });
  if (backdrop) backdrop.addEventListener('click', function (e) { if (e.target === backdrop) hideModal(); });
  document.addEventListener('keydown', function (e) { if (e.key === 'Escape') hideModal(); });

});
