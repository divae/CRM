# CRM Service

## Technology stack
- Spring Boot 2.7.3-RELEASE
- Spring Data JPA
- Spring Mockk 3.1.1
- Java 17
- kotlin 1.7
- MySQL – 8.0
- Docker – version 20.10.17
- Docker-Compose – version 1.29.2
- IntellijIdea for IDE

## Services

### Authorization service
Backoffice service for customer management.

### Test in local
TODO generate file to autorun

### Implemented
- Users registration

### TODO
- Login
Users
- Only admin should create users
- List all users by admin
- Delete user by admin
- Patch user by admin
Customer
- Customer crud by admin and user

Test: Acceptation test for users registration

Security: Do not expose the passwords of docker-compose and application.properties
