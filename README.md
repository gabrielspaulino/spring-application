# Spring Boot E-commerce Application

This project is a RESTful API for a simple e-commerce platform. It provides endpoints for managing users, products, categories, orders, and reviews. The application uses JWT for authentication and an in-memory H2 database.

## Features

-   User registration and authentication (JWT-based).
-   CRUD operations for Users, Products, Orders, and Categories.
-   Ability to add reviews to products.
-   In-memory database that seeds itself on startup for testing and demonstration.

## Technologies Used

-   **Java 17**
-   **Spring Boot 3:** Core framework for the application.
-   **Spring Data JPA:** For data persistence and repository management.
-   **H2 Database:** In-memory database for development and testing.
-   **Spring Security:** For authentication and authorization.
-   **JSON Web Tokens (JWT):** For securing the API.
-   **Maven:** For project build and dependency management.

## API Endpoints

Here is a list of the available API endpoints:

### Authentication

-   `POST /auth/login`: Authenticate a user and receive a JWT token.

### Users

-   `GET /users`: Get all users.
-   `GET /users/{id}`: Get a user by ID.
-   `POST /users`: Create a new user.
-   `PUT /users/{id}`: Update an existing user.
-   `DELETE /users/{id}`: Delete a user.

### Products

-   `GET /products`: Get all products.
-   `GET /products/{id}`: Get a product by ID.
-   `POST /products`: Create a new product.
-   `PUT /products/{id}`: Update an existing product.
-   `DELETE /products/{id}`: Delete a product.
-   `GET /products/compare?productIds={ids}`: Compare products by a list of IDs.
-   `POST /products/{id}/reviews`: Create a review for a product.

### Categories

-   `GET /categories`: Get all categories.
-   `GET /categories/{id}`: Get a category by ID.

### Orders

-   `GET /orders`: Get all orders.
-   `GET /orders/{id}`: Get an order by ID.
-   `POST /orders`: Create a new order.
-   `DELETE /orders/{id}`: Delete an order.

### Reviews

-   `GET /reviews`: Get all reviews.
-   `GET /reviews/{id}`: Get a review by ID.
-   `DELETE /reviews/{id}`: Delete a review.

### Addresses

-   `GET /addresses`: Get all addresses.
-   `GET /addresses/{id}`: Get an address by ID.

## How to Run the Application

The application is configured to run with the `test` profile, which uses the H2 in-memory database.

1.  **Clone the repository.**
2.  **Navigate to the project directory.**
3.  **Run the application using Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
4.  The application will be available at `http://localhost:8080`.

### H2 Console

The H2 database console is enabled and can be accessed at
`http://localhost:8080/h2-console`

## How to Run Tests

To run the automated tests, execute the following command in the project's root directory:

```bash
./mvnw test
```
