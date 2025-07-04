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
- **State Management**: Track transaction state across all steps
- **RESTful Monitoring**: HTTP endpoints for health checks and order status monitoring

## System Architecture

The solution uses Apache Camel's integration framework with the following components:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Order Generator │───▶│ SAGA Orchestr.  │───▶│ Reserve Invent. │
│ (Timer-based)   │    │ (Coordinator)   │    │ + Compensation  │
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
1. **Reserve Inventory** - Allocate products for the order
2. **Process Payment** - Charge customer's payment method
3. **Create Shipping** - Generate shipping label and schedule delivery
4. **Send Confirmation** - Email order confirmation to customer

### Compensation Flow (Failure Path)
1. **Release Inventory** - Free up reserved inventory
2. **Refund Payment** - Return charged amount to customer
3. **Cancel Shipping** - Cancel shipping arrangements
4. **No compensation needed** for confirmation email

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your routing solution.

### 2. Configure SAGA Orchestrator

Set up the main SAGA coordinator that manages the distributed transaction:

1. Add a Timer component to generate orders periodically
2. Configure SAGA with completion and compensation endpoints
3. Set up order properties (ID, customer, amount, failure flags)

![source ](./assets/source.gif)

### 3. Implement Business Operations

Create individual routes for each business operation:

1. **Inventory Reservation**:
   - Reserve products for the order
   - Configure compensation to release inventory on failure

2. **Payment Processing**:
   - Process customer payment
   - Configure compensation to refund payment on failure

3. **Shipping Creation**:
   - Generate shipping arrangements
   - Configure compensation to cancel shipping on failure

4. **Confirmation Email**:
   - Send order confirmation to customer
   - No compensation needed (email already sent)

### 4. Configure Failure Scenarios

Add realistic failure simulation:

1. **Payment Failures**: 30% chance of payment decline
2. **Inventory Failures**: 10% chance of stock unavailability
3. **Shipping Failures**: 5% chance of shipping service issues

### 5. Add Monitoring Endpoints

Implement HTTP endpoints for system monitoring:

1. **Health Check** (`/health`): System status endpoint
2. **Order Status** (`/orders`): View all order states and transactions

## Running the Integration Project

1. Select your integration project in VIZED
2. Right-click on the SAGA.camel.yaml file and select "Run" from the context menu
3. Monitor the logs to see orders being processed and compensations being triggered
4. Access monitoring endpoints:
   - Health: `http://localhost:8000/health`
   - Orders: `http://localhost:8000/orders`

![Executing ](./assets/execution.gif)

## Testing the SAGA Pattern

### Successful Transaction
Watch the logs for messages like:
```
🚀 Starting SAGA for Order: abc12345, Amount: 750
✅ Payment processed for Order: abc12345
✅ Shipping created for Order: abc12345
🎉 SAGA COMPLETED: Order abc12345 processed successfully!
```

### Failed Transaction with Compensation
Watch for compensation messages:
```
🚨 SAGA Error handled: Payment declined - insufficient funds for Order: def67890
🔄 COMPENSATING: Releasing inventory for Order: def67890
💥 SAGA FAILED: Compensating Order def67890
```

### Monitor Order States
Check the order status endpoint to see transaction states:
```json
{
  "orders": {
    "abc12345": {
      "inventory": "RESERVED",
      "payment": "PROCESSED",
      "shipping": "CREATED",
      "email": "SENT",
      "status": "COMPLETED"
    },
    "def67890": {
      "inventory": "RELEASED",
      "payment": "REFUNDED",
      "status": "COMPENSATED"
    }
  }
}
```

## Common Use Cases

The SAGA pattern is ideal for:

- **E-commerce Order Processing**: Inventory, payment, shipping coordination
- **Travel Booking**: Flight, hotel, car rental reservations
- **Financial Transactions**: Multi-step money transfers
- **Supply Chain Management**: Order fulfillment across multiple suppliers

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
