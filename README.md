

# Microservices Kafka Architecture

## 🧩 Overview

### This project demonstrates a simple yet production-ready microservices architecture built with Spring Boot, Apache Kafka, and PostgreSQL. The system is event-driven, meaning services communicate asynchronously using Kafka topics.

### The architecture consists of the following services:
 --- 
# 🔁 Communication Flow

```
sequenceDiagram
    participant User
    participant OrderService
    participant Kafka
    participant ShippingService
    participant InventoryService

    User->>OrderService: POST /api/orders
    OrderService->>Kafka: publish order JSON
    Kafka->>ShippingService: consume order-event
    Kafka->>InventoryService: consume order-event
    ShippingService->>PostgreSQL: save shipping info
    InventoryService->>PostgreSQL: save inventory info
```

--- 
## ⚙️ How It Works
### 1.	User sends a new order via REST endpoint (/api/orders)
### 2.	Order Service saves the order to database and sends the order as a JSON message to Kafka
### 3.	Both Shipping and Inventory Services listen to the Kafka topic order-events
### 4.	On receiving the event:
### •	Shipping service creates a shipping record (default address, pending status)
### •	Inventory service creates an inventory check (assumed available)
### 5.	All logs and DB inserts happen independently — no direct REST calls between services.

⸻








# 🛒 Order Microservice ---
![Order Folder Structure](images/micro.png) 
---

This is a Spring Boot–based microservice responsible for processing orders.  
It handles the following operations:
- Receives order requests via HTTP (REST)
- Persists orders into a PostgreSQL database
- Publishes order events to a Kafka topic

---

## 📚 Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Lombok
- Jackson (ObjectMapper)
- Docker (for Kafka & PostgreSQL)
- Postman (for API testing)

---

## 📂 Folder Structure 
![Order Folder Structure](images/or1.png)

---

## ⚙️ Configuration (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/orderdb
    username: postgres
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  kafka:
    bootstrap-servers: localhost:9092

server:
  port: 8080 
``` 

## OrderController.java
### •	Accepts POST /api/orders
### •	Converts JSON to OrderRequest
### •	Calls service and returns OrderResponse 

```yaml
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) throws Exception {
        Order order = orderService.placeOrder(request);
        OrderResponse response = new OrderResponse("✅ Kafka'ya sipariş mesajı gönderildi", order);
        return ResponseEntity.ok(response);
    }
}
```
## OrderService.java

```yaml
public Order placeOrder(OrderRequest request) throws Exception {
    Order order = Order.builder()
        .productId(request.getProductId())
        .quantity(request.getQuantity())
        .price(request.getPrice())
        .build();

    Order saved = orderRepository.save(order);
    String orderJson = objectMapper.writeValueAsString(saved);
    orderProducer.sendOrderEvent(orderJson);
    return saved;
}
``` 
### 	•	Creates an Order object from request
### 	•	Saves it to PostgreSQL
### 	•	Sends JSON message to Kafka 

--- 
## Docker Compose for Kafka & PostgreSQL 
```yaml
version: '3'
services:

  zookeeper:
    image: confluentinc/cp-zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  postgres:
    image: postgres:14
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: orderdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: yourpassword
``` 
## Developer Note
#### •	This service is part of a broader microservice architecture.
#### •	Other services (e.g., ShippingService, InventoryService) can consume events published to Kafka.
#### •	Kafka acts as a message broker between services, ensuring loose coupling and asynchronous communication.

⸻
## Next Steps
#### •	Add integration with Shipping and Inventory services
#### •	Add retry/error handling with Kafka Dead Letter Topics
#### •	Add observability (logs, metrics, traces)