#!/bin/bash
# ================================================================
#  Finance Dashboard — Startup Script
#  Ensures MySQL is running before launching the Spring Boot app.
#  Usage: ./start.sh
# ================================================================

echo "🔍 Checking MySQL status..."

# Check if MySQL is active
if systemctl is-active --quiet mysql; then
  echo "✅ MySQL is already running."
else
  echo "⚡ MySQL is not running. Starting MySQL..."
  sudo systemctl start mysql

  # Wait up to 15 seconds for MySQL to be ready
  MAX_WAIT=15
  WAITED=0
  while ! systemctl is-active --quiet mysql; do
    if [ "$WAITED" -ge "$MAX_WAIT" ]; then
      echo "❌ MySQL failed to start after ${MAX_WAIT}s. Please check: sudo systemctl status mysql"
      exit 1
    fi
    echo "   Waiting for MySQL... (${WAITED}s)"
    sleep 1
    WAITED=$((WAITED + 1))
  done

  echo "✅ MySQL started successfully."
fi

echo ""
echo "🚀 Starting Finance Dashboard..."
echo "   URL: http://localhost:8080"
echo ""

# Run the Spring Boot application
cd "$(dirname "$0")" || exit 1
./mvnw spring-boot:run
