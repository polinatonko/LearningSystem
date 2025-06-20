# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.5.0] - 2025-06-16 Author: Polina Tonko

### Added

- Request filter for tenant identification.
- Database schema creation/deletion on tenant subscription/unsubscription.
- Connection to required schema using Hibernate.
- Data source per tenant in cloud.
- Binding to the Application Autoscaler

### Changed

- Adapt Liquibase schema migration to multitenant mode.
- Adjust logic with Destination service integration to multitenant mode.
- Cache configuration.
- Send emails with course reminders asynchronously

## [1.4.0] - 2025-06-13 Author: Polina Tonko

### Added

- Approuter.
- Application Info API for ADMIN role.
- API for SaaS Provisioning Service's callbacks.
- Binding to the SaaS Provisioning Service (config - btp/saas-provisioning/config.json).

### Changed

- Role-templates in xs-security.json.

## [1.3.0] - 2025-05-30 Author: Polina Tonko

### Added

- ClassroomLesson and VideoLesson entities.
- Locale field to Student for email message localization purpose.
- Email message template for course reminders.
- Messages resource bundle.
- Audit fields (created, createdBy, lastChanged, lastChangedBy).
- Pagination for GET all requests.
- Surefire maven plugin for unit testing.
- Failsafe maven plugin for integration testing.
- Jacoco maven plugin for test coverage reports.

### Changed

- Split tests into unit and integration folders.

### Fixed

- N+1 issue

## [1.2.0] - 2025-05-26 Author: Polina Tonko

### Added

- Role-based access control using Spring Security.
- Basic Auth locally and for Actuator endpoints in cloud.
- XSUAA authentication for API endpoints in cloud.
- PostgreSQL in Docker locally.
- mta.yaml.
- Spring Boot Actuator loggers endpoint.

### Changed

- Authentication/authorization in tests.

### Removed

- H2.
- manifest.yaml.

## [1.1.0] - 2025-05-26 Author: Polina Tonko

### Added

- manifest.yaml.
- Bindings to the HANA DB Schema, Application Logging Service, Destination Service, and Feature Flags Service.
- Destination with SMTP server credentials.
- User-provided service with SMTP server credentials.
- Retrieving SMTP server credentials via Destination Service API.
- Switching SMTP server credentials retrieval strategy based on Feature Flag value.
- Remote debugging.
- Access token caching.

### Fixed

- UUID type in Liquibase migrations for HANA DB.
- Lost update problem for Course enrollment.

## [1.0.0] - 2025-05-15 Author: Polina Tonko

### Added

- Project backbone.
- CRUD for Students, Courses and Lessons.
- Course enrollment logic.
- Error handler.
- Spring Boot Actuator health, info endpoints.
- Swagger.
- Validation for DTOs.
- Job for sending course reminders.
- Unit and Integration tests.

### Removed

- OSIV feature.