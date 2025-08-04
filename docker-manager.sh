#!/bin/bash

SERVICE=${2:-}

case "$1" in
  develop)
    echo "🚀 Starting all services..."
    docker compose up
    ;;
  up)
    echo "🚀 Starting all services..."
    docker compose up -d
    ;;

  down)
    echo "🛑 Stopping and removing containers..."
    docker compose down --remove-orphans
    ;;

  build)
    echo "🔨 Building all services..."
    docker compose build
    ;;

  rebuild)
    echo "🔄 Rebuilding all services (no cache)..."
    docker compose build --no-cache
    docker compose up -d
    ;;

  rebuild-service)
    echo "♻️ Resetting a service..."
    docker compose stop "$SERVICE"
    docker compose rm -f "$SERVICE"
    docker compose build "$SERVICE" --no-cache
    docker compose up -d "$SERVICE"
    ;;

  reset)
    echo "♻️ Resetting entire environment..."
    docker compose down -v --remove-orphans --rmi all
    docker compose build
    docker compose up -d
    ;;

  prune)
    echo "🧹 Pruning unused Docker resources..."
    docker system prune -a --volumes -f
    ;;

  logs)
    if [ -n "$SERVICE" ]; then
      docker compose logs -f "$SERVICE"
    else
      docker compose logs -f
    fi
    ;;

  exec)
    if [ -n "$SERVICE" ]; then
      docker exec -it "${SERVICE}-service" bash
    else
      echo "❌ Please provide a service name: ./docker-manager.sh exec shop"
    fi
    ;;

  *)
    echo "❓ Usage: ./docker-manager.sh [up|down|build|rebuild|reset|prune|logs SERVICE|exec SERVICE]"
    ;;
esac
