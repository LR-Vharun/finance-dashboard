#!/bin/bash
# ================================================================
#  Finance Dashboard — Startup Script
#  Ensures MySQL is running before launching the Spring Boot app.
#  Usage: ./start.sh
# ================================================================

echo "🔍 Checking MySQL connectivity on port 3306..."

# Function to check if port 3306 is open
is_port_open() {
  (echo > /dev/tcp/localhost/3306) >/dev/null 2>&1
}

if is_port_open; then
    echo "✅ MySQL is reachable."
else
    echo "⏳ MySQL is not reachable on port 3306."
    echo "   Please ensure MySQL is running and the 'finance_db' database is created."
    echo "   (Trying to start anyway...)"
fi

echo ""
echo "🚀 Starting Finance Dashboard..."
echo "   URL: http://localhost:8080"
echo ""

# Run the Spring Boot application
cd "$(dirname "$0")" || exit 1
chmod +x mvnw
./mvnw spring-boot:run
