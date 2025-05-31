# Hexagonal Architecture with Apache Camel YAML DSL

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

### Start the Application
```bash
# Run with default configuration (memory adapter)
camel run camel-hexagonal-example.yaml

# Run with file adapter
camel run camel-hexagonal-example.yaml --property=customer.repository.type=file

# Run with database adapter
camel run camel-hexagonal-example.yaml --property=customer.repository.type=database
```

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
- **Use Case**: Development, testing, rapid prototyping
- **Persistence**: In-memory only (lost on restart)
- **Performance**: Fastest

#### File Adapter
```properties
customer.repository.type=file
customer.file.location=./customers.json
```
- **Use Case**: Simple persistence, small applications
- **Persistence**: JSON file on disk
- **Performance**: Good for small datasets

#### Database Adapter
```properties
customer.repository.type=database
```
- **Use Case**: Production applications
- **Persistence**: Simulated database operations
- **Performance**: Realistic latency simulation

## 🧪 Testing Different Scenarios

### Scenario 1: Development Mode
```bash
# Fast in-memory storage for development
camel run camel-hexagonal-example.yaml --property=customer.repository.type=memory
```

### Scenario 2: File-based Persistence
```bash
# Persistent storage using files
camel run camel-hexagonal-example.yaml --property=customer.repository.type=file
```

### Scenario 3: Production-like Database
```bash
# Database simulation with realistic latency
camel run camel-hexagonal-example.yaml --property=customer.repository.type=database
```

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

This example provides a solid foundation for understanding and implementing Hexagonal Architecture with Apache Camel, demonstrating clean separation of concerns and the flexibility that comes with proper architectural patterns.