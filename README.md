# Finance Dashboard Backend

A Spring Boot finance dashboard with role-based access control, secure transactions, and admin management.

### Automatic Data Seeding
The system is pre-populated with 5 Demo Users and 40 Transactions defined in `src/main/resources/data.sql`.

Every time the application starts for the first time on a new PC:
1.  Spring Boot automatically creates the `users` and `transactions` tables.
2.  It then reads the `data.sql` and inserts required demo data.
3.  On subsequent restarts, it uses `INSERT IGNORE` so duplicate records are skipped.

---

## Setup Instructions (For Testers)

### 1. Prerequisites
- **Java 21** and **Maven** installed and configured in your PATH.
- **MySQL** installed and running on port 3306.
- **Lombok Note**: If you plan to open this in an IDE (VS Code or IntelliJ), please ensure the **Lombok Extension** is installed. Without it, the IDE will show internal code errors, although the application will still run perfectly via the command line.

### 2. Prepare the Database
1.  Run the following command in your MySQL console:
    ```sql
    CREATE DATABASE finance_db;
    ```
2.  **Database Credentials**: The default configuration (in `src/main/resources/application.properties`) uses **username: root** and **no password**.
    *   If your local MySQL uses different credentials, please update them in the properties file before starting.
    *   Alternatively, you can set the environment variables `DB_USER` and `DB_PASS`.

### 3. Start the Application
Use the provided automation scripts for a one-click startup:

#### Windows Users
1.  Navigate to the project folder.
2.  Double-click `start.bat`, or run from Command Prompt:
    ```cmd
    start.bat
    ```

#### Linux or macOS Users
1.  Open a terminal in the project folder.
2.  Grant execution permissions:
    ```bash
    chmod +x start.sh
    ```
3.  Execute the script:
    ```bash
    ./start.sh
    ```

---

## Demo Account Credentials
The following accounts are pre-loaded with transaction history:

| Role | Email | Password |
| :--- | :--- | :--- |
| **USER** | user1@test.com, user2@test.com, user3@test.com | 1234 |
| **USER_PRO**| userpro@test.com | 1234 |
| **ADMIN** | admin@test.com | 1234 |

---

## Security Matrix (Backend Rules)
- **ADMIN**: Full access. Can manage roles and delete/view any user's history.
- **USER_PRO**: Can view personal transactions and personal summaries. Cannot delete history.
- **USER**: Can view/create transactions only. Restricted from summaries and deletions.
