-- ================================================================
--  Finance Dashboard — Demo Seed Data
--  Strategy: INSERT IGNORE with fixed IDs → safe on every restart
--  ddl-auto=update preserves existing data; IGNORE skips duplicates
-- ================================================================

-- ── USERS ───────────────────────────────────────────────────────
--  id | name          | email              | password | role
-- ----------------------------------------------------------------
INSERT IGNORE INTO users (id, name, email, password, role) VALUES
  (1, 'Rahul Sharma',   'user1@test.com',   '1234', 'USER'),
  (2, 'Priya Nair',     'user2@test.com',   '1234', 'USER'),
  (3, 'Arjun Mehta',    'user3@test.com',   '1234', 'USER'),
  (4, 'Sneha Kapoor',   'userpro@test.com', '1234', 'USER_PRO'),
  (5, 'Admin User',     'admin@test.com',   '1234', 'ADMIN');


-- ================================================================
--  TRANSACTIONS
--  All amounts in INR (₹)
--  Income range:  ₹5,000 – ₹50,000
--  Expense range: ₹200   – ₹10,000
-- ================================================================

-- ── USER 1: Rahul Sharma (user_id = 1) ──────────────────────────
INSERT IGNORE INTO transactions (id, amount, type, description, date, user_id) VALUES
  (101, 45000.00, 'INCOME',  'Monthly Salary',          '2025-03-01', 1),
  (102,  8500.00, 'EXPENSE', 'Rent',                    '2025-03-02', 1),
  (103,  2300.00, 'EXPENSE', 'Groceries',               '2025-03-05', 1),
  (104, 12000.00, 'INCOME',  'Freelance Project',        '2025-03-10', 1),
  (105,  4500.00, 'EXPENSE', 'Shopping',                '2025-03-12', 1),
  (106,  1800.00, 'EXPENSE', 'Electricity Bill',        '2025-03-15', 1),
  (107, 45000.00, 'INCOME',  'Monthly Salary',          '2025-04-01', 1),
  (108,  8500.00, 'EXPENSE', 'Rent',                    '2025-04-02', 1),
  (109,  3100.00, 'EXPENSE', 'Groceries',               '2025-04-07', 1),
  (110,  9500.00, 'INCOME',  'Bonus',                   '2025-04-15', 1);

-- ── USER 2: Priya Nair (user_id = 2) ────────────────────────────
INSERT IGNORE INTO transactions (id, amount, type, description, date, user_id) VALUES
  (201, 38000.00, 'INCOME',  'Monthly Salary',          '2025-03-01', 2),
  (202,  7000.00, 'EXPENSE', 'Rent',                    '2025-03-03', 2),
  (203,  1500.00, 'EXPENSE', 'Groceries',               '2025-03-06', 2),
  (204, 15000.00, 'INCOME',  'Freelance Design Work',   '2025-03-14', 2),
  (205,  3200.00, 'EXPENSE', 'Online Shopping',         '2025-03-18', 2),
  (206,   900.00, 'EXPENSE', 'Internet Bill',           '2025-03-20', 2),
  (207, 38000.00, 'INCOME',  'Monthly Salary',          '2025-04-01', 2),
  (208,  7000.00, 'EXPENSE', 'Rent',                    '2025-04-03', 2),
  (209,  2100.00, 'EXPENSE', 'Groceries',               '2025-04-08', 2),
  (210,  5000.00, 'INCOME',  'Referral Bonus',          '2025-04-20', 2);

-- ── USER 3: Arjun Mehta (user_id = 3) ───────────────────────────
INSERT IGNORE INTO transactions (id, amount, type, description, date, user_id) VALUES
  (301, 50000.00, 'INCOME',  'Monthly Salary',          '2025-03-01', 3),
  (302, 10000.00, 'EXPENSE', 'Rent',                    '2025-03-02', 3),
  (303,  4200.00, 'EXPENSE', 'Groceries',               '2025-03-04', 3),
  (304, 20000.00, 'INCOME',  'Consulting Fee',          '2025-03-12', 3),
  (305,  6500.00, 'EXPENSE', 'Travel & Fuel',           '2025-03-16', 3),
  (306,  2200.00, 'EXPENSE', 'Utility Bills',           '2025-03-22', 3),
  (307, 50000.00, 'INCOME',  'Monthly Salary',          '2025-04-01', 3),
  (308, 10000.00, 'EXPENSE', 'Rent',                    '2025-04-02', 3),
  (309,  3800.00, 'EXPENSE', 'Groceries',               '2025-04-06', 3),
  (310,  8000.00, 'INCOME',  'Stock Dividend',          '2025-04-18', 3);

-- ── USER_PRO: Sneha Kapoor (user_id = 4) ────────────────────────
INSERT IGNORE INTO transactions (id, amount, type, description, date, user_id) VALUES
  (401, 42000.00, 'INCOME',  'Monthly Salary',          '2025-03-01', 4),
  (402,  9000.00, 'EXPENSE', 'Rent',                    '2025-03-02', 4),
  (403,  2800.00, 'EXPENSE', 'Groceries',               '2025-03-05', 4),
  (404, 18000.00, 'INCOME',  'Freelance Analytics',     '2025-03-11', 4),
  (405,  5500.00, 'EXPENSE', 'Shopping',                '2025-03-17', 4),
  (406,  1200.00, 'EXPENSE', 'Phone Bill',              '2025-03-21', 4),
  (407, 42000.00, 'INCOME',  'Monthly Salary',          '2025-04-01', 4),
  (408,  9000.00, 'EXPENSE', 'Rent',                    '2025-04-02', 4),
  (409,  3500.00, 'EXPENSE', 'Groceries',               '2025-04-07', 4),
  (410, 25000.00, 'INCOME',  'Project Completion Bonus','2025-04-22', 4);

-- ================================================================
--  END OF SEED DATA
-- ================================================================
