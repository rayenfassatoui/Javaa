# University Management System

A comprehensive web-based application for managing university operations, built with Spring Boot.

## Overview

This University Management System provides an integrated platform for managing key aspects of a university's operations, including:

- Student management
- Teacher management
- Class and subject administration
- Enrollment tracking
- Grading system
- Schedule management
- Payment tracking

## System Requirements

- Java 17 or higher
- Maven 3.6+ or Gradle 7.0+
- MySQL 8.0+ or PostgreSQL 12+
- Web browser (Chrome, Firefox, Edge, Safari)

## Setup Instructions

### Database Setup

1. Create a database for the application:
   ```sql
   CREATE DATABASE university_management;
   ```

2. Configure database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/university_management
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

### Application Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/university-management.git
   cd university-management
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   ```
   http://localhost:8080
   ```

## Key Features

### Student Management
- Student registration and profile management
- View and update student information
- Track student enrollments
- Manage student grades

### Teacher Management
- Teacher registration and profile management
- Department and specialization tracking
- Subject assignments

### Class Management
- Create and manage classes
- Assign teachers to class subjects
- Track class capacity and enrollment

### Subject Management
- Create and manage subjects
- Track credit hours
- Assign teachers

### Enrollment System
- Manage student enrollments in classes
- Track enrollment status (active, completed, withdrawn)
- Historical enrollment records

### Grading System
- Record and manage student grades
- Calculate grade averages
- Track student performance

### Schedule Management
- Create class schedules
- Manage time slots and room assignments
- Avoid scheduling conflicts

### Payment Tracking
- Record student payments
- Track payment history
- Filter payments by date, student, or payment method

## Project Structure

The application follows a standard Spring Boot project structure with a layered architecture:

```
src/main/java/com/university/management/
├── controller/      # REST controllers for handling HTTP requests
├── model/           # Entity classes representing database tables
├── repository/      # JPA repositories for database operations
├── service/         # Business logic layer
│   └── impl/        # Service implementations
└── UniversityManagementApplication.java  # Main application class
```

## API Documentation

The application provides a RESTful API for interacting with the system. Key endpoints include:

- `/students` - Student management
- `/teachers` - Teacher management
- `/classes` - Class management
- `/subjects` - Subject management
- `/enrollments` - Enrollment management
- `/grades` - Grade management
- `/schedules` - Schedule management
- `/payments` - Payment management

## Technologies Used

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA
- **Database**: MySQL/PostgreSQL
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Security**: Spring Security
- **Build Tool**: Maven/Gradle
- **Documentation**: JavaDoc, Swagger

## License

This project is licensed under the MIT License - see the LICENSE file for details. 