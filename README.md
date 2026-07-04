# CineMAX Backend

[![Backend CI](https://github.com/SebasZMDev/CineMAX-Backend/actions/workflows/ci.yml/badge.svg)](https://github.com/SebasZMDev/CineMAX-Backend/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message%20Broker-FF6600)

Backend for CineMAX, a cinema ticket reservation and management platform built using a microservices architecture with Spring Boot.

Backend for CineMAX, a cinema ticket reservation and management platform built using a microservices architecture with Spring Boot.

## Overview

CineMAX is designed to manage users, movie screenings, auditoriums, seats, and reservations. The system is divided into independent microservices that communicate with each other and use asynchronous messaging for event auditing.

## Architecture

```text
Frontend
    |
API Gateway
    |
------------------------------------------------
|                     |                        |
Auth Service      Cinema Service        Audit Service
      |                  |                    |
      ----------------------------
                   |
                 MySQL

Audit Service <---- RabbitMQ
```

## Microservices

### Auth Service

Responsible for authentication and authorization.

Main features:

* User registration
* User login
* JWT generation and validation
* Role management

### Cinema Service

Responsible for the core business logic.

Main features:

* Movie management
* Auditorium management
* Screening management
* Seat management
* Reservation processing

### Audit Service

Responsible for system event tracking and auditing.

Main features:

* Consumes events through RabbitMQ
* Stores audit logs
* Tracks system operations

## Technologies

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT Authentication
* OpenFeign
* MySQL 8
* RabbitMQ
* Docker
* Docker Compose
* GitHub Actions
* Maven

## Getting Started

### Clone the repository

```bash
git clone https://github.com/SebasZMDev/CineMAX-Backend.git
```

### Navigate to the project

```bash
cd CineMAX-Backend
```

### Start all services

```bash
docker compose up -d
```

### Verify running containers

```bash
docker ps
```

### Stop all services

```bash
docker compose down
```

## Continuous Integration

This project uses GitHub Actions to automatically build and validate all microservices whenever code is pushed to the repository.

## Project Structure

```text
CineMAX-Backend
│
├── auth-service
├── cinema-service
├── audit-service
├── docker-compose.yml
└── .github
    └── workflows
        └── ci.yml
```

## Author

Sebas ZM

Academic project developed for the Web Application Development course.
