Recipe Management API

A Spring Boot–based RESTful API for managing recipes, categories, and ingredients. Built with Java 17, Spring Data JPA, PostgreSQL, and Flyway migrations.

📖 Overview

This project provides endpoints to:

Create and list recipe categories

Create and list ingredients (via recipe creation)

Create, list, retrieve, update, and delete recipes

Filter recipes by category or ingredient

It demonstrates:

JPA entity mappings (@OneToMany, @ManyToOne, @ManyToMany)

Pagination with Spring Data (Page<T>, Pageable)

Flyway database migrations

Bean Validation (@Valid, @NotBlank)

Exception handling via @ControllerAdvice

Docker Compose for easy local setup (optional)

🛠️ Tech Stack

Java 17

Spring Boot 3.4.5

Spring Data JPA

PostgreSQL 17.4

Flyway 10.x

Lombok

Swagger/OpenAPI (springdoc)

Docker & Docker Compose (optional)

🚀 Getting Started

Prerequisites

JDK 17+

Maven 3.6+

PostgreSQL 12+

(Optional) Docker & Docker Compose

Configuration

Copy application.properties and configure your database credentials:

spring.datasource.url=jdbc:postgresql://localhost:5432/recipe_db
spring.datasource.username=postgres
spring.datasource.password=your_password

(Optional) If using Docker Compose, update passwords in docker-compose.yml.

Database Setup

Locally:

createdb recipe_db
mvn flyway:migrate

With Docker Compose:

docker-compose up -d

waits for Postgres > run migrations automatically on app start.

Build & Run

cd recipes
mvn clean package
java -jar target/recipe-api-0.0.1-SNAPSHOT.jar

or in IntelliJ: run the RecipeApiApplication main class.

By default, the API listens on http://localhost:8081.

📚 API Endpoints

Categories

Method

Endpoint

Description

GET

/categories

List all categories

GET

/categories/{id}

Retrieve one category

POST

/categories

Create a category

Recipes

Method

Endpoint

Description

GET

/recipes

List all recipes (supports ?page, ?size, ?category, ?ingredient)

GET

/recipes/{id}

Retrieve one recipe by ID

POST

/recipes

Create a recipe

PUT

/recipes/{id}

Update an existing recipe

DELETE

/recipes/{id}

Delete a recipe

Example: Create a Recipe

curl -X POST http://localhost:8081/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Pancakes",
    "instructions": "Mix and fry",
    "category": { "id": 1 },
    "ingredients": [
      { "name": "Flour" },
      { "name": "Milk" }
    ]
  }'

📖 Documentation

Swagger UI available at:

http://localhost:8081/swagger-ui.html

🤝 Contributing

Fork the repo

Create a feature branch

Commit your changes

Open a Pull Request

📄 License

This project is licensed under the MIT License.

