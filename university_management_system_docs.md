# University Student Management System Documentation

## 📋 Project Overview

This document outlines the implementation of a comprehensive University Student Management System using Spring MVC and PostgreSQL. The system is designed to manage various aspects of university administration including student registration, payments, classes, subjects, and schedules.

## ✅ Features Checklist

- [ ] **Student Registration Management**
  - [ ] Student enrollment
  - [ ] Profile management
  - [ ] Document submission
  - [ ] Application status tracking

- [ ] **Payment Management**
  - [ ] Tuition fee collection
  - [ ] Payment history
  - [ ] Receipts generation
  - [ ] Outstanding dues tracking

- [ ] **Class Management**
  - [ ] Class creation and assignment
  - [ ] Student roster management
  - [ ] Capacity planning
  - [ ] Room allocation

- [ ] **Subject Management**
  - [ ] Curriculum design
  - [ ] Subject allocation to classes
  - [ ] Prerequisite management
  - [ ] Credit hour tracking

- [ ] **Schedule Management**
  - [ ] Timetable creation
  - [ ] Scheduling conflicts resolution
  - [ ] Teacher allocation
  - [ ] Room reservation

## 🏗️ System Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│  Presentation   │     │    Business     │     │     Data        │
│     Layer       │◄────┤     Layer       │◄────┤    Access       │
│  (Spring MVC)   │     │  (Services)     │     │    Layer        │
│                 │     │                 │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘
        │                       │                       │
        │                       │                       │
        ▼                       ▼                       ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│    Thymeleaf    │     │ Business Logic  │     │   PostgreSQL    │
│    Templates    │     │ & Validations   │     │    Database     │
│                 │     │                 │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

### MVC Pattern Implementation

1. **Model (M)**
   - Entity classes (Student, Payment, Class, Subject, Schedule)
   - Repository interfaces for data access
   - Service classes for business logic

2. **View (V)**
   - Thymeleaf templates
   - Static resources (CSS, JavaScript)
   - Layout fragments

3. **Controller (C)**
   - REST controllers for API endpoints
   - Web controllers for page rendering
   - Form processing

## 💾 Database Design

### Entity Relationship Diagram

```
┌──────────────┐       ┌───────────────┐       ┌────────────┐
│   Student    │       │   Enrollment  │       │   Class    │
├──────────────┤       ├───────────────┤       ├────────────┤
│ student_id   │◄──────┤ enrollment_id │───────┤ class_id   │
│ first_name   │       │ student_id    │       │ name       │
│ last_name    │       │ class_id      │       │ capacity   │
│ email        │       │ enrollment_date│       │ room      │
│ phone        │       │ status        │       │ department │
│ address      │       └───────────────┘       └────────────┘
│ dob          │               │                     │
└──────────────┘               │                     │
       │                       │                     │
       │                       ▼                     │
       │              ┌───────────────┐              │
       │              │    Payment    │              │
       │              ├───────────────┤              │
       └──────────────┤ payment_id    │              │
                      │ student_id    │              │
                      │ amount        │              │
                      │ payment_date  │              │
                      │ payment_method│              │
                      │ reference_no  │              │
                      └───────────────┘              │
                                                    │
                                                    │
┌──────────────┐       ┌───────────────┐            │
│   Subject    │       │ Class_Subject │            │
├──────────────┤       ├───────────────┤            │
│ subject_id   │◄──────┤ class_subj_id │────────────┘
│ name         │       │ class_id      │
│ code         │       │ subject_id    │
│ credit_hours │       │ teacher_id    │
│ description  │       └───────────────┘
└──────────────┘               │
                               │
                               ▼
┌──────────────┐       ┌───────────────┐
│   Teacher    │       │   Schedule    │
├──────────────┤       ├───────────────┤
│ teacher_id   │◄──────┤ schedule_id   │
│ first_name   │       │ class_subj_id │
│ last_name    │       │ day_of_week   │
│ email        │       │ start_time    │
│ department   │       │ end_time      │
│ specialization│      │ room          │
└──────────────┘       └───────────────┘
```

### Database Schema

#### Students Table
```sql
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    dob DATE,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Classes Table
```sql
CREATE TABLE classes (
    class_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL,
    room VARCHAR(20),
    department VARCHAR(50)
);
```

#### Enrollments Table
```sql
CREATE TABLE enrollments (
    enrollment_id SERIAL PRIMARY KEY,
    student_id INTEGER REFERENCES students(student_id),
    class_id INTEGER REFERENCES classes(class_id),
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('ACTIVE', 'COMPLETED', 'WITHDRAWN', 'SUSPENDED'))
);
```

#### Payments Table
```sql
CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    student_id INTEGER REFERENCES students(student_id),
    amount DECIMAL(10,2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(20) CHECK (payment_method IN ('CASH', 'CREDIT_CARD', 'BANK_TRANSFER', 'CHECK')),
    reference_no VARCHAR(50),
    description TEXT
);
```

#### Subjects Table
```sql
CREATE TABLE subjects (
    subject_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    credit_hours INTEGER NOT NULL,
    description TEXT
);
```

#### Teachers Table
```sql
CREATE TABLE teachers (
    teacher_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50),
    specialization VARCHAR(100)
);
```

#### Class_Subjects Table
```sql
CREATE TABLE class_subjects (
    class_subj_id SERIAL PRIMARY KEY,
    class_id INTEGER REFERENCES classes(class_id),
    subject_id INTEGER REFERENCES subjects(subject_id),
    teacher_id INTEGER REFERENCES teachers(teacher_id)
);
```

#### Schedules Table
```sql
CREATE TABLE schedules (
    schedule_id SERIAL PRIMARY KEY,
    class_subj_id INTEGER REFERENCES class_subjects(class_subj_id),
    day_of_week INTEGER CHECK (day_of_week BETWEEN 1 AND 7),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(20)
);
```

## 🔧 Technical Implementation

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── university/
│   │           ├── config/
│   │           │   ├── SecurityConfig.java
│   │           │   └── WebConfig.java
│   │           ├── controller/
│   │           │   ├── StudentController.java
│   │           │   ├── PaymentController.java
│   │           │   ├── ClassController.java
│   │           │   ├── SubjectController.java
│   │           │   └── ScheduleController.java
│   │           ├── model/
│   │           │   ├── Student.java
│   │           │   ├── Payment.java
│   │           │   ├── Class.java
│   │           │   ├── Subject.java
│   │           │   ├── Teacher.java
│   │           │   ├── Schedule.java
│   │           │   ├── Enrollment.java
│   │           │   └── ClassSubject.java
│   │           ├── repository/
│   │           │   ├── StudentRepository.java
│   │           │   ├── PaymentRepository.java
│   │           │   ├── ClassRepository.java
│   │           │   ├── SubjectRepository.java
│   │           │   └── ScheduleRepository.java
│   │           ├── service/
│   │           │   ├── StudentService.java
│   │           │   ├── PaymentService.java
│   │           │   ├── ClassService.java
│   │           │   ├── SubjectService.java
│   │           │   └── ScheduleService.java
│   │           └── UniversityManagementApplication.java
│   ├── resources/
│   │   ├── static/
│   │   │   ├── css/
│   │   │   ├── js/
│   │   │   └── images/
│   │   ├── templates/
│   │   │   ├── fragments/
│   │   │   ├── students/
│   │   │   ├── payments/
│   │   │   ├── classes/
│   │   │   ├── subjects/
│   │   │   └── schedules/
│   │   ├── application.properties
│   │   └── schema.sql
│   └── webapp/
│       └── WEB-INF/
└── test/
    └── java/
        └── com/
            └── university/
                ├── controller/
                ├── service/
                └── repository/
```

## 📝 Step-by-Step Implementation Guide

### 1. Project Setup

- [ ] Set up Spring Boot project with dependencies:
  - Spring Web
  - Spring Data JPA
  - PostgreSQL Driver
  - Thymeleaf
  - Spring Security (optional)
  - Lombok (optional for reducing boilerplate)

- [ ] Configure application.properties for database connection:
  ```properties
  spring.datasource.url=jdbc:postgresql://ep-lingering-rain-a52krj42-pooler.us-east-2.aws.neon.tech/student_mngmt?sslmode=require
  spring.datasource.username=student_mngmt_owner
  spring.datasource.password=npg_DaxMs2g9KbSk
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  ```

### 2. Domain Model Implementation

- [ ] Create entity classes with JPA annotations:
  - Student.java
  - Payment.java
  - Class.java
  - Subject.java
  - Teacher.java
  - Schedule.java
  - Enrollment.java
  - ClassSubject.java

### 3. Data Access Layer

- [ ] Create repository interfaces extending JpaRepository:
  - StudentRepository.java
  - PaymentRepository.java
  - ClassRepository.java
  - SubjectRepository.java
  - TeacherRepository.java
  - ScheduleRepository.java
  - EnrollmentRepository.java
  - ClassSubjectRepository.java

### 4. Business Logic Layer

- [ ] Implement service classes with business logic:
  - StudentService.java
  - PaymentService.java
  - ClassService.java
  - SubjectService.java
  - TeacherService.java
  - ScheduleService.java
  - EnrollmentService.java

### 5. Presentation Layer

- [ ] Create controllers for web interfaces:
  - StudentController.java
  - PaymentController.java
  - ClassController.java
  - SubjectController.java
  - TeacherController.java
  - ScheduleController.java
  - EnrollmentController.java

- [ ] Design Thymeleaf templates for views:
  - List views for all entities
  - Detail views
  - Create/Edit forms
  - Dashboard components

### 6. Security Implementation

- [ ] Configure Spring Security (if required):
  - User roles (ADMIN, TEACHER, STUDENT)
  - Authentication providers
  - Access control rules

### 7. Testing

- [ ] Unit tests for service layer
- [ ] Integration tests for repositories
- [ ] Controller tests for web endpoints

### 8. Deployment

- [ ] Prepare deployment scripts
- [ ] Configure production properties
- [ ] Set up CI/CD pipeline

## 🚀 Additional Features (Future Enhancements)

- [ ] **Student Portal**
  - [ ] View enrolled classes
  - [ ] Check grades
  - [ ] Pay fees online
  - [ ] Download transcripts

- [ ] **Teacher Portal**
  - [ ] Upload course materials
  - [ ] Manage attendance
  - [ ] Submit grades
  - [ ] Communicate with students

- [ ] **Reporting Module**
  - [ ] Student performance reports
  - [ ] Financial reports
  - [ ] Attendance reports
  - [ ] Custom report generation

- [ ] **Mobile Application**
  - [ ] Student mobile app
  - [ ] Teacher mobile app
  - [ ] Push notifications

## 🛠️ Technologies Used

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap, jQuery
- **Database**: PostgreSQL
- **Build Tool**: Maven/Gradle
- **Testing**: JUnit, Mockito
- **Security**: Spring Security
- **Documentation**: Swagger/OpenAPI

## 📊 Sample API Endpoints

### Student Management
- GET /api/students - List all students
- GET /api/students/{id} - Get student details
- POST /api/students - Create a new student
- PUT /api/students/{id} - Update student information
- DELETE /api/students/{id} - Delete a student

### Payment Management
- GET /api/payments - List all payments
- GET /api/payments/{id} - Get payment details
- POST /api/payments - Record a new payment
- GET /api/students/{id}/payments - Get student payment history

### Class Management
- GET /api/classes - List all classes
- POST /api/classes - Create a new class
- GET /api/classes/{id}/students - Get students in a class
- POST /api/classes/{id}/students - Add student to class

### Subject Management
- GET /api/subjects - List all subjects
- POST /api/subjects - Create a new subject
- PUT /api/subjects/{id} - Update subject details

### Schedule Management
- GET /api/schedules - List all schedules
- POST /api/schedules - Create a new schedule
- GET /api/classes/{id}/schedule - Get class schedule
- GET /api/teachers/{id}/schedule - Get teacher schedule 