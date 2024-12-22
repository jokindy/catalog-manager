# CatalogManager Service ReadME

This project is a Spring Boot application designed to manage products and categories. It provides various endpoints to perform CRUD operations on products and categories, with different access levels for authorized users and ADMINs.

## Endpoints

### Public Endpoints

#### GET /api/v1/products
- **Description:** Returns a paginated list of all products.
- **Query Parameters:** Can filter results using pagination parameters (`page`, `size`).

#### GET /api/v1/products/search
- **Description:** Returns products by unique name.
- **Query Parameters:** Accepts a `name` parameter to search for products and also can filter results using pagination parameters (`page`, `size`).

#### GET /api/v1/products/category
- **Description:** Searches for products by category name.
- **Query Parameters:** Accepts a `categoryName` parameter to search for products and also can filter results using pagination parameters (`page`, `size`).

#### GET /api/v1/products/{productId}
- **Description:** Returns product details for the provided product ID.

#### GET /api/v1/categories
- **Description:** Returns a paginated list of all categories, including their products.

#### GET /api/v1/categories/{categoryId}
- **Description:** Returns details of a specific category by ID, including associated products.
- **Query Parameters:** Can filter results using pagination parameters (`page`, `size`).

### ADMIN Endpoints

#### POST /api/v1/products
- **Description:** Adds a new product. Requires the role `ADMIN`.
- **Request Body:** Contains product details, including `name`, `logoUrl`, and `categoryId`.

#### PUT /api/v1/products
- **Description:** Updates an existing product. Requires the role `ADMIN`.
- **Request Body:** Contains updated product details, including `name`, `logoUrl`, and `categoryId`.

#### DELETE /api/v1/products/{productId}
- **Description:** Deletes a product by product ID. Requires the role `ADMIN`.

#### POST /api/v1/categories
- **Description:** Adds a new category. Requires the role `ADMIN`.
- **Request Body:** Contains category details, including `name` and `logo`.

#### DELETE /api/v1/categories/{categoryId}
- **Description:** Deletes a category by category ID. Requires the role `ADMIN`.

### Access Control
- Public endpoints are accessible to all authenticated users.
- ADMIN endpoints are accessible only to users with the `ADMIN` role.

## Technologies Used

### Spring Framework
- **Spring Boot 3.4.x:** Framework for creating Spring-based applications.
- **Spring Data JPA:** Simplifies database access using JPA and Hibernate.
- **Spring Security:** Used for authentication and authorization.

### Database
- **PostgreSQL:** Relational database management system for storing products and categories.
- **H2:** In-memory database used for development and testing.
- **Liquibase:** Schema change management tool for database migrations.

### Testing
- **Spring Boot Tests:** Framework for testing Spring Boot applications.
- **JUnit 5:** Testing framework for unit and integration tests.
- **MockMvc:** Used for testing REST APIs.

### Other
- **MapStruct:** Annotation-based code generator for mapping between Java bean types.
- **Keycloak:** Open-source identity and access management solution for securing applications and services.

## Usage
To get started with the project, follow these steps:

1. **Clone the repository:**
    ```bash
    git clone <repository_url>
    ```

2. **Configure your database settings:**
    - Open `application.yaml` and update the database configuration according to your PostgreSQL setup.

3. **Run PostgreSQL database:**
    - Ensure PostgreSQL is running on `localhost:5432`.

4. **Run Keycloak and PostgreSQL containers:**
    - Use the provided `docker-compose.yaml` file to run Keycloak and PostgreSQL containers.
    ```bash
    docker-compose -f docker-compose.yaml up -d
    ```

5. **Import CatalogManager realm in Keycloak:**
    - Access Keycloak admin console (default URL: `http://localhost:8484`) and import the provided `CatalogManager` realm.
    - Default username and password are "user" and "bitnami".

6. **Build and run the application:**
    - Build the application using Maven or your IDE.

7. **Access the endpoints:**
    - Use Postman to access the endpoints. Collections for this are included in the project directory.

## Testing
- Unit and integration tests are available for services, controllers, and repositories.
- To execute the tests, run:
- Run `mvn test` to execute the tests.
- **Code Coverage:** Intellij IDEa Code Coverage tool is used for count coverage.

## Logging and Traceability
- Errors include a unique `traceId` for easy log correlation.
- Logs can be viewed in the console or a logging system (e.g., ELK stack).