# Learning Management System

_Learning Management System_ allows Students to enroll in variety of Courses using virtual coins. 
Each Course is composed of multiple Lessons. To keep Students informed and engaged, 
platform provides automated reminders for upcoming Courses.

## Goals

1. Provide starter kit with the most important and frequently repeated tasks and their solutions.
2. Good knowledge of Spring Framework:
   - Cover the most important and commonly used modules;
   - Cover main annotations;
   - Provide understanding of Spring’s main features under the hood.
3. Good knowledge of SAP BTP, understanding of how to integrate with BTP services API without 
libraries.

## Technology Stack

- **Programming languages:** Java 21
- **Frameworks and Libraries:** Spring (MVC, Data, Security, AOP, Boot), JPA/Hibernate, Swagger, Liquibase
- **Databases:** H2, PostgreSQL, HANA DB
- **Platforms:** SAP BTP
- **Technologies:** Docker
- **Testing:** JUnit, Mockito
- **SAP BTP Services:** HANA DB, XSUUAA Service, Application Logging Service, Destination Service, Feature Flags Service, 
Service Manager, SaaS Registry, User-provided service, Application Autoscaler

## Local Setup

### Prerequisites

Ensure you have the following installed on your system:
- Java 21
- Docker

### Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/polinatonko/LearningSystem.git
   cd LearningSystem
   ```

2. Run the Docker container:
   ```
   cd docker
   docker compose up -d
   cd ..
   ```
   
3. Build the project:
   ```
   ./mvnw clean package
   ```

4. Run the application with the ***local*** profile active:
   ```
   ./mvnw spring-boot:run "-Dspring-boot.run.profiles=local"
   ```

4. To verify the application is running, open [Health Check Endpoint](http://localhost:8080/actuator/health)