#  Flight Booking Microservices System

A complete microservices-based flight booking platform built using **Spring Boot**, **Spring Cloud**, **JWT Security**, **Kafka**, **MongoDB**, **Docker**, and **WebFlux**.

This project demonstrates real-world distributed architecture with secure gateway routing, centralized configuration, service discovery, reactive programming, and event-driven communication.

---

##  What I Actually Implemented

- **JWT-based authentication**  
  - JWT generated only in **User Service** using **JJWT**  
  - All other services validate JWT using **OAuth2 Resource Server**

- **Secure API Gateway (Spring Cloud Gateway + WebFlux)**  
  - Validates JWT before forwarding  
  - Routes to user / flight / booking microservices

- **Database-per-service with MongoDB**  
  - `userservicedb`, `flightservicedb`, `bookingservicedb`

- **Event-driven notifications with Kafka**  
  - Booking Service publishes `BOOKING_CONFIRMED` / `BOOKING_CANCELLED`  
  - Notification Service consumes and sends **Gmail SMTP emails**

- **Fully dockerized setup with docker-compose**  
  - One `docker-compose up --build` brings up  
    Eureka, Config Server, Gateway, all microservices, Kafka, Zookeeper, MongoDB

- **Reactive stack end-to-end**  
  - Spring WebFlux + Reactive MongoDB in core services

---

#  Features Overview

## 1. Secure API Gateway (WebFlux)

The API Gateway is built using **Spring Cloud Gateway** and acts as the single entry point to all microservices.

**Responsibilities:**
- JWT Authentication & Validation  
- Role-Based Access Control  
- Dynamic Routing  
- Blocking Unauthorized Requests  

**Security Powered By:**
- **OAuth2 Resource Server (JWT)**
- **Reactive Spring Security**

---

## 2. Microservices Included

| Service | Responsibilities |
|--------|------------------|
| **User Service** | Register user, login, JWT token generation |
| **Flight Service** | Add flights, update flights, manage inventory |
| **Booking Service** | Book tickets, cancel tickets, calculate pricing |
| **Notification Service** | Sends email notifications on booking/cancellation |
| **API Gateway** | Authentication, routing, filtering |
| **Config Server** | Centralized configuration management |
| **Eureka Server** | Service discovery & load balancing |

---

## 3. Event-Driven Messaging (Kafka)

The system uses **Apache Kafka** to publish and consume booking events:

**Published Events:**
- `BOOKING_CONFIRMED`
- `BOOKING_CANCELLED`

**Flow:**
1. **Booking Service** publishes event to topic `booking-events`  
2. **Notification Service** listens via `@KafkaListener`  
3. Email is automatically triggered  

---

## 4. Databases (MongoDB)

Each microservice uses its own database following **Database per Service** design pattern:

- `userservicedb`
- `flightservicedb`
- `bookingservicedb`

This ensures:
- Loose coupling  
- Independent scaling  
- Clear data ownership per service  

---

## 5. Tech Stack

###  Backend
- **Java 17**
- **Spring Boot 3**
- **Spring WebFlux (Reactive)**
- **Spring Cloud**
  - Spring Cloud Gateway  
  - Config Server  
  - Eureka Discovery Server  
  - OpenFeign  
- **Spring Security**
  - OAuth2 Resource Server (JWT)
  - BCrypt password hashing

###  Messaging
- **Apache Kafka**
- **Zookeeper**
- **Spring Kafka**

###  Data
- **MongoDB Reactive**

###  DevOps
- **Docker**
- **Docker Compose**
- **Multi-stage Dockerfiles**

###  Build & Tools
- **Maven**
- **Lombok**
- **JUnit 5 + Mockito**

---

##  ER Diagram

```mermaid
erDiagram
    USER ||--o{ TICKET : books
    FLIGHT ||--o{ TICKET : includes
    TICKET ||--o{ PASSENGER : contains

    USER {
        string id
        string name
        string gender
        int age
        string email
        string password
        Role role
    }

    FLIGHT {
        string id
        string airline
        string fromPlace
        string toPlace
        datetime departureTime
        datetime arrivalTime
        float price
        int totalSeats
        int availableSeats
    }

    TICKET {
        string id
        string pnr
        string userId
        string departureFlightId
        string returnFlightId
        FlightType tripType
        datetime bookingTime
        string seatsBooked
        float totalPrice
        boolean canceled
    }

    PASSENGER {
        string id
        string name
        string gender
        int age
        string seatNumber
        string mealPreference
        string ticketId
    }

---
##  Architecture Diagram

```mermaid
flowchart TD
    CLIENT[Client (Postman / UI)] -->|HTTP + JWT| APIGW[API Gateway<br/>(Spring Cloud Gateway)]

    subgraph Edge Layer
        APIGW
    end

    APIGW -->|/user-microservice/**| USER[User Service<br/>JWT Generation]
    APIGW -->|/flight-microservice/**| FLIGHT[Flight Service<br/>Flight Inventory]
    APIGW -->|/booking-microservice/**| BOOKING[Booking Service<br/>Tickets + PNR]

    %% Booking → Kafka → Notification
    BOOKING -->|BookingEvent<br/>BOOKING_CONFIRMED / CANCELLED| TOPIC[(Kafka Topic<br/>booking-events)]
    TOPIC --> NOTIF[Notification Service<br/>Email Sender]

    %% Infra
    subgraph Infra
        EUREKA[Eureka Server]
        CONFIG[Config Server]
        MONGO[(MongoDB)]
        ZK[Zookeeper]
        KFK[Kafka Broker]
    end

    USER --- MONGO
    FLIGHT --- MONGO
    BOOKING --- MONGO

    APIGW --- EUREKA
    USER --- EUREKA
    FLIGHT --- EUREKA
    BOOKING --- EUREKA
    NOTIF --- EUREKA
    CONFIG --- EUREKA

    USER ---> CONFIG
    FLIGHT ---> CONFIG
    BOOKING ---> CONFIG
    NOTIF ---> CONFIG
    APIGW ---> CONFIG

    BOOKING --- KFK
    NOTIF --- KFK
    KFK --- ZK
