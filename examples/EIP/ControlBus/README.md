# Control Bus EIP with VIZED & Apache Camel

## What is the Control Bus Pattern?

The Control Bus Enterprise Integration Pattern provides a way to manage and control message flows and routes at runtime. It allows you to dynamically start, stop, and monitor integration routes without restarting the entire application, making it essential for production systems that require high availability and operational flexibility.

## Overview

This tutorial demonstrates how to implement the **Control Bus Pattern** using **VIZED** and **Apache Camel**. You'll learn how to create a comprehensive order processing system where various processing routes can be dynamically controlled and monitored through management commands.

## Key Features

- **Dynamic Route Management**: Start/stop individual routes or entire pipelines at runtime
- **Maintenance Mode Control**: Selective route shutdown for system maintenance
- **Health Monitoring**: Real-time system and route status monitoring
- **Emergency Controls**: Quick response capabilities for system shutdown
- **Load Management**: Adjust processing based on system conditions
- **Configuration Management**: Dynamic configuration updates without restart

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **File System**: Order ingestion from file directories
- **Apache Camel**: Integration and routing engine with Control Bus management
- **REST API**: Management endpoints for route control and monitoring
- **Circuit Breaker**: External service health monitoring
- **Caffeine Cache**: Configuration and state management

## Business Scenario

This example simulates a real-world **E-commerce Order Processing System** with the following workflow:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│ Order Ingestion │───▶│ Order Validation │───▶│ Inventory Check │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                                         │
┌──────────────────┐   ┌───────────────────┐   ┌────────▼────────┐
│ Customer         │◀──│ Order Fulfillment │◀──│ Payment         │
│ Notification     │   │                   │   │ Processing      │
└──────────────────┘   └───────────────────┘   └─────────────────┘
                                ▲
                    ┌───────────┴────────────┐
                    │    Control Bus         │
                    │   Management Layer     │
                    └────────────────────────┘
```

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your Control Bus solution.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer.
2. Search for the File Component in the Component tab.
3. Configure it to monitor the `orders/incoming/` directory for new order files.

### 3. Implement Order Processing Pipeline

Create the complete order processing workflow:

1. Add route for **Order Validation** to verify data integrity.
2. Add route for **Inventory Check** to verify product availability.
3. Add route for **Payment Processing** to handle transactions.
4. Add route for **Order Fulfillment** to manage shipping.
5. Add route for **Customer Notification** to send status updates.

### 4. Add Control Bus Management

Implement the Control Bus pattern for route management:

1. Add REST endpoints for route control operations.
2. Configure health check endpoints for system monitoring.
3. Implement maintenance mode controls.
4. Add emergency shutdown capabilities.

### 5. Configure Monitoring and Metrics

Set up comprehensive system monitoring:

1. Add health check routes for each processing stage.
2. Configure metrics collection for performance monitoring.
3. Implement circuit breaker patterns for external services.
4. Add load-based control mechanisms.

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Monitor the logs to see the order processing pipeline and control operations.

## External Dependencies Setup

### 1. Directory Structure

Create the required directory structure for order processing:

```bash
mkdir -p orders/{incoming,processed,failed,invalid,backorder,payment-failed}
mkdir -p notifications/outgoing
```

### 2. Sample Order File

Create a sample order file `sample-order.json` in the `orders/incoming/` directory:

```json
{
  "orderId": "ORD-12345",
  "customerId": "CUST-67890",
  "customerEmail": "customer@example.com",
  "totalAmount": 99.99,
  "items": [
    {
      "productId": "PROD-001",
      "name": "Wireless Headphones",
      "quantity": 1,
      "price": 99.99
    }
  ],
  "shippingAddress": {
    "street": "123 Main St",
    "city": "Anytown",
    "state": "CA",
    "zipCode": "12345"
  }
}
```

## Control Operations

### Start Entire Pipeline
```bash
curl -X POST http://localhost:8080/admin/control \
  -H "Content-Type: application/json" \
  -d '{"action": "start-pipeline"}'
```

### Stop Specific Route
```bash
curl -X POST http://localhost:8080/admin/control \
  -H "Content-Type: application/json" \
  -d '{"action": "stop", "routeId": "paymentProcessingRoute"}'
```

### Enter Maintenance Mode
```bash
curl -X POST http://localhost:8080/admin/control \
  -H "Content-Type: application/json" \
  -d '{"action": "maintenance-mode"}'
```

### Check System Health
```bash
curl http://localhost:8080/admin/health
```

### Emergency Shutdown
```bash
curl -X POST http://localhost:8080/admin/emergency-shutdown
```

## Expected Output

### Console Logs
```
Order ingestion processing file: sample-order.json
Validating order: ORD-12345
Order ORD-12345 is valid, proceeding to inventory check
Checking inventory for order: ORD-12345
Inventory available for order: ORD-12345
Processing payment for order: ORD-12345
Payment successful for order: ORD-12345, Transaction: TXN-123456
Fulfilling order: ORD-12345
Order ORD-12345 shipped with tracking: TRACK-789012
Sending notification for order: ORD-12345, Status: SHIPPED
```

### Control Response
```json
{
  "status": "success",
  "message": "Command start-pipeline executed for null",
  "timestamp": "2024-01-15 10:30:45"
}
```

### Health Check Response
```json
{
  "timestamp": "2024-01-15T10:30:45.123Z",
  "system": "Order Processing System",
  "routes": {
    "orderIngestion": "started",
    "orderValidation": "started",
    "inventoryCheck": "started",
    "paymentProcessing": "started",
    "orderFulfillment": "started",
    "notification": "started"
  },
  "overallStatus": "healthy"
}
```

## Testing Scenarios

### 1. Basic Order Processing
1. Start the application and pipeline
2. Place the sample order file in `orders/incoming/`
3. Watch the logs for processing steps
4. Check output directories for results

### 2. Route Control Testing
1. Stop payment processing route
2. Place an order file and observe processing stops at payment stage
3. Restart payment processing and watch completion

### 3. Maintenance Mode Testing
1. Enter maintenance mode and observe selective route shutdown
2. Resume from maintenance and verify normal operation

## Use Cases

This pattern is ideal for:
- **Production Systems**: Route control without downtime
- **Maintenance Operations**: Selective component shutdown
- **Load Management**: Dynamic scaling based on conditions
- **Emergency Response**: Quick system shutdown capabilities
- **Business Rules**: Time-based or condition-based processing control
- **Monitoring Integration**: Health checks and metrics collection

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
