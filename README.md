# 🛒 CommerceX

CommerceX is a production-oriented e-commerce backend platform designed to simulate real-world commerce systems like Amazon and Flipkart at a scalable architectural level.

The project focuses on backend engineering excellence rather than UI complexity, implementing clean domain modeling, secure authentication, transactional workflows, and modular service design.

Built using Java 21 and Spring Boot 3, CommerceX demonstrates modern backend development practices including JWT authentication, role-based authorization, inventory management, order processing, payment workflows, notification systems, database migrations, and RESTful API design.



## 🏗️ Architecture

CommerceX follows a layered domain-driven architecture:

Controller
        |
        ↓
Service Layer
        |
        ↓
Business Validator
        |
        ↓
Repository Layer
        |
        ↓
Rich Domain Model


Design Principles:

- Controllers contain only request handling logic
- Services orchestrate application workflows
- Validators enforce business rules
- Entities contain domain behavior
- Repositories remain persistence focused
- Hibernate dirty checking is used for state management

- ## 🚀 Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate ORM
- Maven
- Flyway Migration
- MapStruct
- Lombok
- Bean Validation

### Database
- MySQL

### Development Tools
- Docker
- Git
- IntelliJ IDEA
- Postman

- ## 📦 Implemented Modules

### Authentication
✓ JWT Authentication
✓ Refresh Token Flow
✓ Role Based Authorization
✓ Permission Based Authorization
✓ Spring Security Integration


### Customer
✓ Customer Profile Management
✓ Address Management


### Catalog
✓ Category Management
✓ Product Management
✓ Product Search


### Inventory
✓ Stock Management
✓ Inventory Validation


### Cart
✓ Cart Management
✓ Cart Item Handling


### Order
✓ Order Creation
✓ Order Lifecycle Management
✓ Order Items


### Payment
✓ Payment Workflow
✓ Payment Provider Abstraction


### Notification
✓ User Notifications
✓ Event Based Notification Design


### Admin
✓ Customer Management
✓ Product Management
✓ Inventory Management
✓ Order Management
