# School application

## Description

This is an update to the `sql-jdbc-school` application. This repository contains the implementation of a console application named school-console-app, which interacts with a PostgreSQL database using Hibernate and Spring Data for data manipulation. The application is designed to perform CRUD operations on tables representing school-related entities.

## SQL Files

1. **Create User and Database**
    - Before running the application, create a PostgreSQL user and database with the necessary privileges. Make sure to create the user and database before running the application.

2. **Tables Creation**
    - Flyway migration is utilized by the application to ensure database schema versioning and management. If the tables already exist, Flyway will handle the migration process, and in case of necessity, it will create the necessary base data. Make sure to have Flyway properly configured before running the application.

## Application Properties

To configure the application to connect to the PostgreSQL database, add the following properties to the `application.yml` file:

```yml
# Database configuration
spring:
    datasource:
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://localhost:5432/your_database_name
        username: your_username
        password: your_password

    flyway:
        url: jdbc:postgresql://localhost:5432/your_database_name
        user: your_username
        password: your_password
        locations: db/migration
```
Make sure to replace `your_database_name`, `your_username`, and `your_password` with your actual PostgreSQL database name, username, and password.

## Test Data Generation

On application startup, the following test data will be generated:

- 10 groups with randomly generated names (format: XX-NN, where XX is 2 characters, and NN is 2 numbers).
- 10 courses (e.g., math, biology).
- 200 students with randomly combined first names and last names.
- Random assignment of students to groups (each group can contain 10 to 30 students).
- Many-to-many relation between students and courses tables, with each student assigned to 1 to 3 courses randomly.

## Console Menu SQL Queries

The console menu provides the following SQL queries:

a. **Find all groups with less or equal students' number**

b. **Find all students related to the course with the given name**

c. **Add a new student**

d. **Delete a student by the STUDENT_ID**

e. **Add a student to the course (from a list)**

f. **Remove the student from one of their courses**

## How to Run

1. Configure the PostgreSQL user, database, and update `application.yml`.
2. Run the application.

Enjoy using the `school-console-app` console application to manage school-related data!

## Contributors

- [avexcoss](https://github.com/avecoss)