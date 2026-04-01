# Flowwow - Цветочный магазин

## Описание проекта

Интернет-магазин по продаже цветочных композиций
- **Backend** — REST API на Spring Boot
## Технологии

### Backend
- Java 17
- Spring Boot 4.0.4
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Gradle

## Запуск проекта

### Требования
- Java 17+
- PostgreSQL 15+ (или Docker)
- Gradle

### 1. Запуск PostgreSQL через Docker
```bash
docker-compose up -d
```

### 2. Запуск приложения
```bash
./gradlew clean bootRun
```