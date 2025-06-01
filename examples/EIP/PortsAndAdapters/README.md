# Hexagonal Architecture with Apache Camel YAML DSL

## What is Hexagonal Architecture?

Hexagonal Architecture, also known as the Ports and Adapters pattern, is a design approach that emphasizes separation of concerns between business logic and infrastructure. It enables technology independence, testability, and flexibility by isolating the core domain logic from external systems.


## Overview

This tutorial demonstrates how to implement **Hexagonal Architecture** using **Apache Camel YAML DSL** and **Groovy scripting**. You'll learn how to structure your application into layers, ensuring clean separation of concerns and adaptability to different technologies.

## Key Features

- **Business Logic Isolation**: Pure domain logic in Groovy scripts, independent of infrastructure concerns.
- **Configuration-Driven Adapters**: Switchable implementations via properties for flexibility.
- **Comprehensive Logging**: Includes correlation IDs, processing time measurements, and clear layer identification.
- **Error Handling**: Domain-level validation and adapter-level technical error handling.


## System Architecture

The solution uses Apache Camel's integration framework with the following layers:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   HTTP Adapter  │───▶│ Application     │───▶│ Repository Port │
│ (Primary/Driving│    │ Service Layer   │    │ (Secondary/     │
│   Adapter)      │    │ (Use Cases)     │    │ Driven Adapter) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
                                               ┌─────────────────┐
                                               │ Switchable      │
                                               │ Adapters:       │
                                               │ • Memory        │
                                               │ • File          │
                                               │ • Database      │
                                               └─────────────────┘
```


## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your routing solution.

### 2. Configure Your Primary Adapter

Set up the HTTP adapter to handle incoming requests:

1. Add a route for the `inbound-http-adapter`.
2. Configure it to process HTTP requests and convert external protocols to internal message formats.

### 3. Implement Application Services

Orchestrate use cases via Camel routes:

1. Add routes for application services like `customer-application-service`.
2. Wire these routes to the domain logic for processing.

### 4. Define Domain Logic

Implement pure business logic in Groovy scripts:

1. Add routes for use cases like `get-customer-use-case`, `create-customer-use-case`, etc.
2. Ensure the domain logic is independent of infrastructure concerns.

### 5. Configure Repository Ports and Adapters

Set up repository ports and adapters for data storage:

1. Define the `customer-repository-port` as an abstract interface.
2. Implement adapters like `customer-memory-adapter`, `customer-file-adapter`, and `customer-database-adapter`.


## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Monitor the logs to see requests being processed and responses being generated.

![Executing ](./assets/executing.gif)



## External Dependencies Setup

### Switching Repository Adapters

#### Memory Adapter (Default)
```properties
customer.repository.type=memory
```

#### File Adapter
```properties
customer.repository.type=file
customer.file.location=./customers.json
```

#### Database Adapter
```properties
customer.repository.type=database
```



## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

---

## Contact Us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)


<!-- # Hexagonal Architecture with Apache Camel YAML DSL

This example demonstrates a complete implementation of **Hexagonal Architecture** (also known as Ports and Adapters pattern) using Apache Camel's YAML DSL with Groovy scripting.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   HTTP Adapter  │───▶│ Application     │───▶│ Repository Port │
│ (Primary/Driving│    │ Service Layer   │    │ (Secondary/     │
│   Adapter)      │    │ (Use Cases)     │    │ Driven Adapter) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
                                               ┌─────────────────┐
                                               │ Switchable      │
                                               │ Adapters:       │
                                               │ • Memory        │
                                               │ • File          │
                                               │ • Database      │
                                               └─────────────────┘
```

## 🎯 Key Concepts Demonstrated

### 1. **Hexagonal Architecture Layers**
- **Domain Core**: Pure business logic in Groovy scripts
- **Application Services**: Use case orchestration via Camel routes
- **Ports**: Abstract interfaces (Camel direct endpoints)
- **Adapters**: Concrete implementations (switchable via properties)

### 2. **Isolation Principles**
- **Technology Independence**: Business logic doesn't know about HTTP, files, or databases
- **Testability**: Each layer can be tested independently
- **Flexibility**: Easy to switch between different implementations
- **Maintainability**: Changes in one layer don't affect others

### 3. **Adapter Pattern**
- **Primary Adapters** (Driving): HTTP REST API
- **Secondary Adapters** (Driven): Memory, File, Database repositories

## 🚀 Running the Example

### Prerequisites
- Apache Camel JBang installed
- Java 11+ 


### Alternative: Using application.properties
```bash
# Edit application.properties to change adapter type
# Then run:
camel run camel-hexagonal-example.yaml
```

## 📡 API Endpoints

### Health Check
```bash
curl http://localhost:8080/health
```

### Customer Operations

#### Create Customer
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com"
  }'
```

#### Get Customer
```bash
curl "http://localhost:8080/api/customers?id=CUSTOMER_ID"
```

#### Update Customer
```bash
curl -X PUT "http://localhost:8080/api/customers?id=CUSTOMER_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com"
  }'
```

#### Delete Customer
```bash
curl -X DELETE "http://localhost:8080/api/customers?id=CUSTOMER_ID"
```

## 🔧 Configuration Examples

### Switching Repository Adapters

#### Memory Adapter (Default)
```properties
customer.repository.type=memory
```

#### File Adapter
```properties
customer.repository.type=file
customer.file.location=./customers.json
```

#### Database Adapter
```properties
customer.repository.type=database
```

## 🧪 Testing Different Scenarios



## 🏛️ Architecture Benefits Demonstrated

### 1. **Dependency Inversion**
- High-level modules (business logic) don't depend on low-level modules (adapters)
- Both depend on abstractions (ports)

### 2. **Single Responsibility**
- Each route has a single, well-defined responsibility
- Clear separation between business logic and infrastructure concerns

### 3. **Open/Closed Principle**
- Easy to add new adapters without modifying existing code
- Configuration-driven adapter selection

### 4. **Interface Segregation**
- Ports define minimal, focused interfaces
- Adapters implement only what they need

### 5. **Testability**
- Each layer can be tested independently
- Easy to mock dependencies
- Business logic is pure and testable

## 📋 Route Breakdown

### Primary Adapter Layer
- `inbound-http-adapter`: Handles HTTP requests and responses
- Converts external protocols to internal message format

### Application Service Layer
- `customer-application-service`: Orchestrates use cases
- Routes requests to appropriate business logic

### Domain Layer (Use Cases)
- `get-customer-use-case`: Customer retrieval logic
- `create-customer-use-case`: Customer creation with validation
- `update-customer-use-case`: Customer update logic
- `delete-customer-use-case`: Customer deletion logic

### Port Layer
- `customer-repository-port`: Abstract repository interface
- Routes to concrete adapters based on configuration

### Adapter Layer
- `customer-memory-adapter`: In-memory implementation
- `customer-file-adapter`: File-based implementation
- `customer-database-adapter`: Database simulation

## 🔍 Key Features

### Business Logic Isolation
- All business rules are in Groovy scripts
- No dependency on Camel or infrastructure concerns
- Pure domain logic that's easy to test and understand

### Configuration-Driven Adapters
- Switch between implementations via properties
- No code changes required for different environments
- Easy A/B testing of different storage strategies

### Comprehensive Logging
- Correlation IDs for request tracing
- Processing time measurements
- Clear layer identification in logs

### Error Handling
- Business validation at the domain layer
- Technical error handling at the adapter layer
- Meaningful error messages for clients

## 🎓 Learning Outcomes

After studying this example, you'll understand:

1. **How to implement Hexagonal Architecture** with Camel YAML DSL
2. **Separation of concerns** between business logic and infrastructure
3. **Dependency injection** through configuration
4. **Adapter pattern** for pluggable implementations
5. **Port abstraction** for technology independence
6. **Clean Architecture principles** in practice

## 🔄 Extending the Example

### Adding New Adapters
1. Create new route: `customer-{type}-adapter`
2. Implement the same operations: GET, CREATE, UPDATE, DELETE
3. Add configuration property: `customer.repository.type={type}`

### Adding New Features
1. Add new use case route
2. Implement business logic in Groovy
3. Wire through the application service
4. No changes needed to adapters

### Adding Cross-Cutting Concerns
1. Security: Add authentication at the HTTP adapter
2. Caching: Add caching adapter between port and repository
3. Metrics: Add monitoring at each layer
4. Circuit Breaker: Add resilience patterns

This example provides a solid foundation for understanding and implementing Hexagonal Architecture with Apache Camel, demonstrating clean separation of concerns and the flexibility that comes with proper architectural patterns. -->







