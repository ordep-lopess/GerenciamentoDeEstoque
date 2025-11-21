// form.js
document.addEventListener('DOMContentLoaded', function () {
  var openBtn = document.getElementById('openModal');
  var backdrop = document.getElementById('modalBackdrop');
  var closeBtn = document.getElementById('closeModal');
  var cancelBtn = document.getElementById('cancelBtn');

  // debug rápido (remova depois)
  console.log('form.js carregado. openBtn=', !!openBtn, 'backdrop=', !!backdrop, 'closeBtn=', !!closeBtn, 'cancelBtn=', !!cancelBtn);

  function showModal() {
    if (!backdrop) return console.warn('backdrop não encontrado');
    backdrop.style.display = 'flex';
    backdrop.setAttribute('aria-hidden', 'false');
    document.body.style.overflow = 'hidden';
  }

  function hideModal() {
    if (!backdrop) return console.warn('backdrop não encontrado');
    backdrop.style.display = 'none';
    backdrop.setAttribute('aria-hidden', 'true');
    document.body.style.overflow = '';
  }

  if (openBtn) {
    openBtn.addEventListener('click', function (e) {
      e.preventDefault();
      showModal();
    });
  } else {
    console.warn('openModal não encontrado no DOM');
  }

  if (closeBtn) {
    closeBtn.addEventListener('click', function (e) {
      e.preventDefault();
      hideModal();
    });
  }

  if (cancelBtn) {
    cancelBtn.addEventListener('click', function (e) {
      e.preventDefault();
      hideModal();
    });
  }

  if (backdrop) {
    // fechar ao clicar fora da modal
    backdrop.addEventListener('click', function (e) {
      if (e.target === backdrop) hideModal();
    });
  }

  // ESC para fechar
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') hideModal();
  });
});
