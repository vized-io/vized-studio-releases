# SAGA Pattern with VIZED & Apache Camel

## What is the SAGA Pattern?

The SAGA pattern is a microservices architecture pattern that provides a way to manage distributed transactions across multiple services. Instead of using traditional ACID transactions, SAGA breaks down a business transaction into a series of smaller, local transactions. Each local transaction updates data within a single service and publishes events or messages to trigger the next transaction step. If any step fails, the SAGA executes compensating transactions to undo the changes made by preceding transactions.

## Overview

This tutorial demonstrates how to implement the **SAGA Pattern** using **VIZED** and **Apache Camel**. You'll learn how to create a distributed transaction system that handles order processing with automatic compensation on failure, ensuring data consistency across multiple business operations.

## Key Features

- **Distributed Transaction Management**: Coordinate multiple business operations across different services
- **Automatic Compensation**: Rollback completed steps when failures occur
- **Order Processing Workflow**: Complete order lifecycle from inventory reservation to confirmation
- **Failure Simulation**: Built-in failure scenarios to demonstrate compensation logic
- **State Management**: Track transaction state across all steps using ConcurrentHashMap
- **RESTful API**: HTTP endpoints for order submission, status monitoring, and health checks
- **Inventory Management**: Real-time inventory tracking with reservation and release capabilities
- **CORS Support**: Cross-origin resource sharing enabled for web applications

## System Architecture

The solution uses Apache Camel's integration framework with the following components:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ HTTP API        │───▶│ SAGA Orchestr.  │───▶│ Reserve Invent. │
│ (REST Endpoint) │    │ (Coordinator)   │    │ + Compensation  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Process Payment │    │ Create Shipping │
                       │ + Compensation  │    │ + Compensation  │
                       └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Send Confirm.   │    │ SAGA Complete/  │
                       │ Email           │    │ Compensate      │
                       └─────────────────┘    └─────────────────┘
```

## SAGA Transaction Steps

### Forward Flow (Happy Path)
1. **Reserve Inventory** - Allocate products for the order (validates stock availability)
2. **Process Payment** - Charge customer's payment method (30% failure rate simulation)
3. **Create Shipping** - Generate shipping label and schedule delivery (5% failure rate simulation)
4. **Send Confirmation** - Email order confirmation to customer

### Compensation Flow (Failure Path)
1. **Release Inventory** - Free up reserved inventory
2. **Refund Payment** - Return charged amount to customer
3. **Cancel Shipping** - Cancel shipping arrangements
4. **No compensation needed** for confirmation email

## Available Products

The system comes pre-configured with three products:
- **ITM1**: iPhone 15 Pro ($999)
- **ITM2**: MacBook Pro M3 ($1,999)
- **ITM3**: AirPods Pro ($199)

Each product starts with 100 units in stock.

## REST API Endpoints

### 1. Submit Order
- **POST** `/orders`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "orderId": "ORD-12345",
  "orderItems": [
    {
      "productId": "ITM1",
      "quantity": 2
    },
    {
      "productId": "ITM3", 
      "quantity": 1
    }
  ],
  "address": {
    "line1": "123 Main Street",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "pincode": "10001"
  }
}
```

### 2. Check Order Status
- **GET** `/orders`
- Returns all orders with their current states and transaction history

### 3. Check Inventory Status
- **GET** `/inventory`
- Returns current inventory levels for all products

### 4. Health Check
- **GET** `/health`
- Returns system health status and available endpoints

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your SAGA solution.

### 2. Configure Inventory Initialization

Set up the inventory system with predefined products:

1. Add a Timer component to initialize inventory on startup
2. Configure three products with stock levels and pricing
3. Set up inventory state management using ConcurrentHashMap

### 3. Configure SAGA Orchestrator

Set up the main SAGA coordinator that manages the distributed transaction:

1. Add a Platform HTTP component for REST API endpoints
2. Configure SAGA with completion and compensation endpoints
3. Set up order validation and processing logic

![source](assets/source.gif)

### 4. Implement Business Operations

Create individual routes for each business operation:

1. **Inventory Reservation**:
   - Validate stock availability before reservation
   - Reserve products for the order
   - Configure compensation to release inventory on failure

2. **Payment Processing**:
   - Process customer payment with 30% failure simulation
   - Convert reserved inventory to sold on successful payment
   - Configure compensation to refund payment on failure

3. **Shipping Creation**:
   - Generate shipping arrangements with 5% failure simulation
   - Configure compensation to cancel shipping on failure

4. **Confirmation Email**:
   - Send order confirmation to customer
   - No compensation needed (email already sent)

### 5. Add Monitoring Endpoints

Implement HTTP endpoints for system monitoring:

1. **Health Check** (`/health`): System status endpoint with available endpoints list
2. **Order Status** (`/orders`): View all order states and transaction history
3. **Inventory Status** (`/inventory`): Real-time inventory levels and availability

## Running the Integration Project

1. Select your integration project in VIZED
2. Right-click on the SAGA.camel.yaml file and select "Run" from the context menu
3. Monitor the logs to see orders being processed and compensations being triggered

![source](assets/execution.gif)

## Testing the SAGA Pattern

### Successful Transaction
Watch the logs for messages like:
```
🚀 Starting SAGA for Order: ORD-12345, Amount: 2197
✅ Payment processed for Order: ORD-12345
✅ Shipping created for Order: ORD-12345
🎉 SAGA COMPLETED: Order ORD-12345 processed successfully!
```

### Failed Transaction with Compensation
Watch for compensation messages:
```
🚨 SAGA Error handled: Payment declined - insufficient funds for Order: ORD-67890
🔄 COMPENSATING: Releasing inventory for Order: ORD-67890
💥 SAGA FAILED: Compensating Order ORD-67890
```

### Monitor Order States
Check the order status endpoint to see transaction states:
```json
{
  "orders": [
    {
      "orderId": "ORD-12345",
      "inventory": "RESERVED",
      "payment": "PROCESSED", 
      "shipping": "CREATED",
      "email": "SENT",
      "status": "COMPLETED",
      "amount": 2197,
      "timestamp": "2024-01-15 10:30:45"
    }
  ],
  "totalOrders": 1,
  "timestamp": "2024-01-15 10:30:45"
}
```

### Check Inventory Status
Monitor inventory levels:
```json
{
  "inventory": {
    "ITM1": {
      "name": "iPhone 15 Pro",
      "totalStock": 98,
      "reserved": 0,
      "available": 98
    },
    "ITM2": {
      "name": "MacBook Pro M3", 
      "totalStock": 100,
      "reserved": 0,
      "available": 100
    },
    "ITM3": {
      "name": "AirPods Pro",
      "totalStock": 99,
      "reserved": 0,
      "available": 99
    }
  },
  "timestamp": "2024-01-15 10:30:45"
}
```


## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
