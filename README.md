# Learning Management System

Learning Management System allows Students to enroll in variety of Courses using virtual coins. 
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

- Programming languages: Java 21
- Frameworks and Libraries: Spring (MVC, Data, Security, AOP, Boot), JPA/Hibernate, Swagger, Liquibase
- Databases: H2, PostgreSQL, HANA DB
- Platforms: SAP BTP
- Technologies: Docker
- Testing: JUnit, Mockito
- SAP BTP Services: HANA DB, XSUUAA Service, Application Logging Service, Destination Service, Feature Flags Service, 
Service Manager, SaaS Registry, User-provided service, Application Autoscaler

## Local Setup

### Prerequisites

- Java 21
- Docker

### Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/polinatonko/LearningSystem.git
   ```

2. To build command in the project's root directory use:
   ```
   ./mvnw clean package
   ```

3. Run the application with the ***local*** profile active:
   ```
   ./mvnw spring-boot:run -D"spring.profiles.active=local"
   ```