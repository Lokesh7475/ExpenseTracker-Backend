# Expense Tracker - Backend

A **Spring Boot–based Expense Tracker** backend that allows users to manage their expenses efficiently with authentication, category tracking, and modular architecture.  

This project demonstrates clean layering, exception handling, and DTO-based data transfer.

## Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Spring Security (JWT Authentication)**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## Features

- User Authentication (Signup / Login) with JWT  
- CRUD operations for Expenses  
- Categorization of expenses  
- Secure REST APIs with Spring Security  
- PostgreSQL database integration  
- Exception handling and DTO-based structure

## Project Structure
```text
\---src
    +---main
    |   +---java
    |   |   \---com
    |   |       \---Lokesh
    |   |           \---ExpenseTracker
    |   |               |   ExpenseTrackerApplication.java
    |   |               |   
    |   |               +---Advice
    |   |               |       GlobalExceptionHandler.java
    |   |               |       
    |   |               +---Config
    |   |               +---Controllers
    |   |               |       AuthController.java
    |   |               |       ExpenseController.java
    |   |               |       UserController.java
    |   |               |       
    |   |               +---DTO
    |   |               |       ExpenseDTO.java
    |   |               |       ExpenseRequestDTO.java
    |   |               |       LoginRequestDTO.java
    |   |               |       LoginResponseDTO.java
    |   |               |       SignupResponseDTO.java
    |   |               |       UserDTO.java
    |   |               |       UserRegistrationDTO.java
    |   |               |       
    |   |               +---Enums
    |   |               |       Category.java
    |   |               |       PaymentMethod.java
    |   |               |       
    |   |               +---Exceptions
    |   |               |       AccessDeniedException.java
    |   |               |       ErrorResponse.java
    |   |               |       ExpenseIsNullException.java
    |   |               |       ExpenseNotFoundException.java
    |   |               |       InvalidUserException.java
    |   |               |       
    |   |               +---Mappers
    |   |               |       ExpenseMapper.java
    |   |               |       UserMapper.java
    |   |               |       
    |   |               +---Models
    |   |               |       Expense.java
    |   |               |       User.java
    |   |               |       
    |   |               +---Repo
    |   |               |       ExpenseRepo.java
    |   |               |       UserRepo.java
    |   |               |       
    |   |               +---Security
    |   |               |       AuthService.java
    |   |               |       AuthUtil.java
    |   |               |       CustomUserDetailsService.java
    |   |               |       JwtAuthFilter.java
    |   |               |       SecurityConfig.java
    |   |               |       
    |   |               \---Services
    |   |                       ExpenseService.java
    |   |                       UserService.java
    |   |                       
    |   \---resources
    |       |   application.properties
    |       |   
    |       +---static
    |       \---templates
    \---test
        \---java
            \---com
                \---Lokesh
                    \---ExpenseTracker
                            ExpenseTrackerApplicationTests.java

```

## API Endpoints
#### Authentication APIs (/api/auth)
```text
POST	         /api/auth/signup	                   Register a new user
POST	         /api/auth/login	                   Login and get JWT token
```

#### User APIs (/api/users)
```text
GET	           /api/users	                           Get all users
GET            /api/users/{id}                         Get user by ID
GET	           /api/users/username/{username}	       Get user by username
PUT	           /api/users	                           Update user details
DELETE	       /api/users	                           Delete a user
```
#### Expense APIs (/api/users/{id}/expenses)
```text
GET            /api/users/{id}/expenses	               Get all expenses for a user
GET	           /api/users/{uid}/expenses/{eid}	       Get expense by ID
POST           /api/users/{id}/expenses	               Add a new expense
PUT	           /api/users/{id}/expenses	               Update an expense
DELETE	       /api/users/{uid}/expenses/{eid}	       Delete an expense
```

### Clone the Repository

```bash
git clone https://github.com/Lokesh7475/ExpenseTracker-Backend.git
cd ExpenseTracker-Backend
```

### Configure the Database
```properites
spring.datasource.url=jdbc:postgresql://localhost:5432/dbname

spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_secret_key
```

### Build the Project
```bash
./mvnw clean install
```

### Run the Application
```bash
./mvnw spring-boot:run
```
