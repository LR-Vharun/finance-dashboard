// ─── Theme Management ───────────────────────────────────────────────
function initTheme() {
  const savedTheme = localStorage.getItem('financeTheme') || 'dark';
  document.documentElement.setAttribute('data-theme', savedTheme);
  updateThemeIcon(savedTheme);
}

function toggleTheme() {
  const current = document.documentElement.getAttribute('data-theme');
  const next = current === 'dark' ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', next);
  localStorage.setItem('financeTheme', next);
  updateThemeIcon(next);
}

function updateThemeIcon(theme) {
  const btn = document.getElementById('theme-toggle-btn');
  if (!btn) return;
  // Use Sun icon for light mode, Moon for dark
  btn.innerHTML = theme === 'dark' ? '🌙' : '☀️';
}

// Initialize theme as soon as script loads to prevent flash
initTheme();

// ─── Auth Helpers ─────────────────────────────────────────────────
function getUser() {
  const u = sessionStorage.getItem('financeUser');
  return u ? JSON.parse(u) : null;
}

function getAuthToken() {
  return sessionStorage.getItem('financeAuth'); // Basic base64(email:password)
}

function logout() {
  sessionStorage.removeItem('financeUser');
  sessionStorage.removeItem('financeAuth');
  location.href = 'index.html';
}

function authHeaders(extra = {}) {
  const token = getAuthToken();
  return {
    'Content-Type': 'application/json',
    'Authorization': token ? `Basic ${token}` : '',
    ...extra
  };
}

// ─── Role badge styling ────────────────────────────────────────────
function applyRoleBadge(el, role) {
  if (!el) return;
  el.textContent = role;
  el.className = 'role-badge ' + role;
}

// ─── Nav setup (called per page) ──────────────────────────────────
function setupNav(activePage) {
  const user = getUser();
  // Allow index.html to load without being logged in, but others must redirect
  const isLoginPage = window.location.pathname.endsWith('index.html') || window.location.pathname === '/';
  
  if (!isLoginPage) {
    if (!user || !getAuthToken()) { location.href = 'index.html'; return; }
    applyRoleBadge(document.getElementById('nav-role-badge'), user.role);

    const txnLink = document.getElementById('nav-transactions');
    if (txnLink && (user.role === 'USER' || user.role === 'USER_PRO')) {
      txnLink.classList.remove('hidden');
    }

    const adminLink = document.getElementById('nav-admin');
    if (adminLink && user.role === 'ADMIN') {
      adminLink.classList.remove('hidden');
    }
  }

  // Ensure theme icon is correct after nav loads
  initTheme();
}

// ─── Login Form Handler ────────────────────────────────────────────
const loginForm = document.getElementById('login-form');
if (loginForm) {
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const errEl = document.getElementById('error-msg');
    if (errEl) { errEl.classList.add('hidden'); errEl.textContent = ''; }

    const btn      = document.getElementById('login-btn');
    btn.disabled   = true;
    btn.textContent = 'Signing in…';

    const email    = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
      const res  = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      const data = await res.json();

      if (!res.ok || !data.userId) {
        if (errEl) {
          errEl.textContent = typeof data === 'string' ? data : 'Login failed. Check credentials.';
          errEl.classList.remove('hidden');
        }
        return;
      }

      sessionStorage.setItem('financeUser', JSON.stringify(data));
      sessionStorage.setItem('financeAuth', btoa(`${email}:${password}`));

      if (data.role === 'ADMIN') {
        location.href = 'admin.html';
      } else {
        location.href = 'dashboard.html';
      }
    } catch (err) {
      if (errEl) {
        errEl.textContent = 'Could not connect to server.';
        errEl.classList.remove('hidden');
      }
    } finally {
      btn.disabled    = false;
      btn.textContent = 'Sign In';
    }
  });
}
