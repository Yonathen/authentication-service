# Makefile for Docker Compose Project

.PHONY: up down build rebuild reset logs exec prune help

develop:
	./docker-manager.sh develop

up:
	./docker-manager.sh up

down:
	./docker-manager.sh down

build:
	./docker-manager.sh build

rebuild:
	./docker-manager.sh rebuild

rebuild-service:
	./docker-manager.sh rebuild-service $(SERVICE)

reset:
	./docker-manager.sh reset

prune:
	./docker-manager.sh prune

logs:
	./docker-manager.sh logs $(SERVICE)

exec:
	./docker-manager.sh exec $(SERVICE)

help:
	@echo "📘 Available commands:"
	@echo "  make up                - Start all containers"
	@echo "  make down              - Stop and remove containers"
	@echo "  make build             - Build services"
	@echo "  make rebuild           - Build without cache and restart"
	@echo "  make reset             - Full reset (remove volumes/images, rebuild)"
	@echo "  make prune             - Clean up Docker system"
	@echo "  make logs SERVICE=shop - Tail logs for a service"
	@echo "  make exec SERVICE=shop - Enter service container"
