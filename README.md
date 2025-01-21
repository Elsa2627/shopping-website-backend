Shopping Website Backend

Description

The Shopping Website Backend provides the API and server-side logic for the e-commerce platform. It manages users, products, orders, and handles authentication and security.

Key Features

User Management: Register, log in, and delete user accounts.
Product Management: Add and manage products.
Favorites: Add and remove favorite products.
Orders: Place and track orders.
Security: Basic authentication with Spring Security.
Swagger API Documentation: Automatically generated API docs accessible via Swagger UI.
Project Structure

Backend: ShoppingBackend directory developed in Spring Boot with Maven.
API: Exposes RESTful APIs for interacting with the platform.
Folder Structure
controller: Contains REST controllers to manage requests and responses.
repository: JPA repositories for accessing and manipulating the database.
service: Contains the core business logic and service layer.
model: Entities representing the data structure (e.g., Product, User).
security: Configuration for securing API access.
test: Unit and integration tests for the backend.
Prerequisites

To run this project, you will need:

Java 17+
Maven
Installation

Backend
Clone the repository:
git clone https://github.com/Elsa2627/shopping-website-backend.git
cd shopping-website-backend
Install dependencies and build the project:
mvn clean install
Run the backend:
mvn spring-boot:run
Access the Swagger API documentation: http://localhost:8080/swagger-ui.html
Security Configuration
The backend uses Spring Security with Basic Authentication:

The /swagger-ui/** and /v3/api-docs/** routes are publicly accessible.
Other endpoints require authentication with a username (customUser) and password (customPassword).
CORS Configuration
The backend allows cross-origin requests from http://localhost:3000 (for frontend development).

Database
The backend uses H2 Database for local testing by default. For production, you can configure it for MySQL or PostgreSQL by updating the application.properties file.

API Documentation

The backend exposes a REST API. Here are the main available routes:

Users
POST /users/signup: Register a new user.
POST /users/login: Log in a user.
DELETE /users/{id}: Delete a user.
GET /users/{userId}/favorites: Get a user's favorite products.
POST /users/{userId}/favorites: Add a product to favorites.
DELETE /users/{userId}/favorites/{productId}: Remove a product from favorites.
Products
GET /products: Get the list of available products.
GET /products/{id}: Get details of a specific product.
Orders
POST /orders: Place an order.
GET /orders/{orderId}: Get order details.
Technologies Used

Backend: Spring Boot, Hibernate
Security: Spring Security (Basic Authentication)
Swagger: Springdoc OpenAPI for API documentation
Database: H2 (default, configurable for MySQL or PostgreSQL)
Tests

Backend
Navigate to the backend directory:
cd ShoppingBackend
Run tests using Maven:
mvn test
Contribution

Contributions are welcome! Please submit a pull request or open an issue for any improvements or suggestions.

Authors

This project was developed by Elsa Saadoun as part of an academic project.

License

This project is licensed under the MIT License.
