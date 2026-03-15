# Spring Boot E-Commerce API

A RESTful backend application built using Spring Boot for managing an e-commerce system.  
The project follows clean architecture principles with layered design.

---

## 🚀 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security (In Progress)
- MySQL
- Maven

---

## 🏗 Architecture

This project follows a layered architecture:

- **Controller Layer** – Handles HTTP requests
- **Service Layer** – Contains business logic
- **Repository Layer** – Handles database operations
- **Model Layer** – Entity classes
- **DTO Layer** – Request and Response objects
- **Exception Handling** – Custom exceptions and global handler
- **Configuration Layer** – Application configuration

---

## ✨ Features

### Category Management
- Create Category
- Update Category
- Delete Category
- Get Category by ID
- Get All Categories

### Product Management
- Create Product
- Update Product
- Delete Product
- Get Product by ID
- Get All Products

### Additional Features
- DTO-based API design
- Custom exceptions
- Global exception handling
- Clean and scalable structure

---

## 🔐 Security

Spring Security integration is currently in progress.

Planned Improvements:
- User Authentication
- Role-Based Authorization (Admin/User)
- Secure Endpoints
- JWT Token Implementation

---

## 📂 Project Structure

```
src/
├── main/
│ ├── java/com/ecommerce/project/
│ │ ├── controller/
│ │ ├── service/
│ │ ├── serviceImpl/
│ │ ├── repository/
│ │ ├── model/
│ │ ├── dto/
│ │ ├── exceptions/
│ │ └── config/
│ └── resources/
└── test/
```

---

## ▶️ How to Run the Project

### 1. Clone the Repository
git clone <repository-url>

### 2. Configure Database

Update `application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```


### 3. Build and Run

Using Maven:
mvn spring-boot:run

Or run the main class:
SpringEcomApplication.java


---

## 🔮 Future Enhancements

- JWT Authentication
- Role-Based Access Control
- API Documentation using Swagger
- Unit Testing
- Docker Deployment
- Deployment to Cloud

---

## 👨‍💻 Author

Saurabh Rawat
