# Customer PDF & Email Service

A Spring Boot backend service that retrieves customer information from a MySQL database using CNIC, generates a professional customer profile PDF, and sends the generated PDF to the customer's Gmail address as an email attachment.

## Project Overview

The Customer PDF & Email Service automates the process of generating and delivering customer information documents.

The application:

1. Receives a customer's CNIC through a REST API.
2. Searches for the customer in the MySQL database.
3. Validates the customer's CNIC format.
4. Validates that the email follows a Gmail address format.
5. Generates a customer profile PDF.
6. Sends the generated PDF to the customer's email as an attachment.
7. Returns a success or error response through the REST API.

## Features

- Customer lookup using CNIC
- CNIC format validation
- Gmail address validation
- Customer profile PDF generation
- PDF email attachment
- MySQL database integration
- REST API endpoint
- Service-layer architecture
- Error handling for invalid or missing customer data

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- OpenPDF
- Spring Boot Mail
- Jakarta Mail
- REST API
- IntelliJ IDEA
- Postman
- Git & GitHub

## Architecture

The application follows a layered Spring Boot architecture:

- **Controller** — Handles HTTP requests and API responses.
- **Service** — Contains customer, PDF generation, and email business logic.
- **Repository** — Communicates with the MySQL database using Spring Data JPA.
- **Entity** — Represents customer data stored in the database.

## Project Structure

```text
CustomerPdfEmailService
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.customerpdfemailservice
│   │   │       │
│   │   │       ├── controller
│   │   │       │   └── CustomerController.java
│   │   │       │
│   │   │       ├── entity
│   │   │       │   └── Customer.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │   └── CustomerRepository.java
│   │   │       │
│   │   │       └── service
│   │   │           ├── CustomerService.java
│   │   │           ├── PdfService.java
│   │   │           └── EmailService.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│
├── pom.xml
├── .gitignore
├── mvnw
├── mvnw.cmd
└── README.md