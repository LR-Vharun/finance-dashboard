# Finance Dashboard Backend

A Spring Boot finance dashboard with role-based access control, secure transactions, and admin management.

### Automatic Data Seeding
The system is pre-populated with 5 Demo Users and 40 Transactions defined in `src/main/resources/data.sql`.

Every time the application starts for the first time on a new PC:
1.  Spring Boot automatically creates the `users` and `transactions` tables.
2.  It then reads the `data.sql` and inserts the demo data.
3.  On subsequent restarts, it uses `INSERT IGNORE` so duplicate records are skipped.

---

## Setup Instructions (For Testers)

### 1. Prerequisites
- **Java 21** and **Maven** installed.
- **MySQL** installed and running.
- **Note on Lombok**: If you are using an IDE (like VS Code or IntelliJ), please ensure the **Lombok extension/plugin** is installed to avoid errors in the editor. (Not required just to run the application).

### 2. Prepare the Database
Run this single command in your MySQL console:
```sql
CREATE DATABASE finance_db;
```

### 3. Start the Application
Use the provided automation scripts to simplify the startup process:

#### On Windows
1.  Open the project folder.
2.  Double-click the `start.bat` file, or run it through Command Prompt/PowerShell:
    ```cmd
    start.bat
    ```

#### On Linux or macOS
1.  Open a terminal in the project folder.
2.  Grant execution permissions to the script:
    ```bash
    chmod +x start.sh
    ```
3.  Run the script:
    ```bash
    ./start.sh
    ```

> [!NOTE]
> The scripts will wait for MySQL to be ready (up to 60s) before launching the backend.

---

## Demo Account Credentials
The following accounts are pre-loaded with transaction history:

| Role | Email | Password |
| :--- | :--- | :--- |
| **USER** | user1@test.com, user2@test.com, user3@test.com | 1234 |
| **USER_PRO**| userpro@test.com | 1234 |
| **ADMIN** | admin@test.com | 1234 |

---

## Security Matrix
Access rules enforced at the backend:
- **ADMIN**: Full control. Can delete/view any user's history and change roles.
- **USER_PRO**: Can view their own transactions and financial summary. Cannot delete history.
- **USER**: Can view/create transactions only. Restricted from summaries and deletions.
