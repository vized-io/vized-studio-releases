# Splitter & Resequencer with VIZED & Apache Camel

## What are Splitter and Resequencer Patterns?

**Splitter** and **Resequencer** are Enterprise Integration Patterns (EIP) that help process and organize messages in integration workflows:

- **Splitter** breaks a composite message (like an array or batch) into individual messages for separate processing.
- **Resequencer** reorders messages based on a sequence or timestamp, ensuring correct processing order.

## Overview

This tutorial demonstrates how to implement **Splitter** and **Resequencer** using **VIZED** and **Apache Camel YAML DSL**. You'll learn how to process a JSON file containing multiple orders, split them into individual messages, enrich each order with business logic, and resequence them by order date.

## Key Features

- **JSON File Processing**: Read and parse JSON files with multiple orders.
- **Message Splitting**: Process each order individually.
- **Order Enrichment**: Add processing metadata and business rules.
- **Resequencing**: Ensure orders are processed in chronological order.
- **Dynamic File Writing**: Output each processed order as a separate JSON file.
- **Comprehensive Logging**: Track every step for easy debugging.

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

1. Open VIZED and create a new Integration Project for this pattern.
2. Add the provided `SplitterResequencer.camel.yaml` route to your project workspace.

### 2. Configure Your Source Component

1. Add a File Component to read `orders.json` from the `orders/incoming` directory.
2. Set parameters: `noop=true` (do not move/delete after reading), `delay=5000` (polling interval).

### 3. Split and Enrich Orders

1. Unmarshal the JSON array into individual order objects.
2. Use the Split processor to break the array into single order messages.
3. Enrich each order with:
   - Processing timestamp
   - Original processing sequence number
   - Priority-based business rules (expedited flag, processing fee)

### 4. Resequence and Output

1. Resequence orders by `orderDate` to ensure chronological processing.
2. Marshal each order back to JSON.
3. Write each processed order to `orders/processed` with a dynamic filename.


## Sample Input Data

Place the following file as `orders/incoming/orders.json`:

```json
[
  {
    "orderId": "ORD-001",
    "customerId": "CUST-123",
    "productName": "Laptop",
    "quantity": 1,
    "price": 999.99,
    "orderDate": "2024-01-15T10:30:00Z",
    "priority": "HIGH",
    "status": "PENDING"
  },
  {
    "orderId": "ORD-002",
    "customerId": "CUST-456",
    "productName": "Mouse",
    "quantity": 2,
    "price": 29.99,
    "orderDate": "2024-01-15T09:15:00Z",
    "priority": "LOW",
    "status": "PENDING"
  },
  {
    "orderId": "ORD-003",
    "customerId": "CUST-789",
    "productName": "Keyboard",
    "quantity": 1,
    "price": 79.99,
    "orderDate": "2024-01-15T11:45:00Z",
    "priority": "MEDIUM",
    "status": "PENDING"
  },
  {
    "orderId": "ORD-004",
    "customerId": "CUST-321",
    "productName": "Monitor",
    "quantity": 1,
    "price": 299.99,
    "orderDate": "2024-01-15T08:20:00Z",
    "priority": "HIGH",
    "status": "PENDING"
  },
  {
    "orderId": "ORD-005",
    "customerId": "CUST-654",
    "productName": "Headphones",
    "quantity": 1,
    "price": 149.99,
    "orderDate": "2024-01-15T12:00:00Z",
    "priority": "MEDIUM",
    "status": "PENDING"
  }
]
```

## Expected Output

Each order will be processed in chronological order (by `orderDate`), enriched, and written as a separate JSON file in `orders/processed/`.

**Example output for `ORD-004` (earliest order):**

```json
{
  "orderId": "ORD-004",
  "customerId": "CUST-321",
  "productName": "Monitor",
  "quantity": 1,
  "price": 299.99,
  "orderDate": "2024-01-15T08:20:00Z",
  "priority": "HIGH",
  "status": "PENDING",
  "processedAt": "2024-07-01T12:34:56.789Z",
  "processingSequence": 3,
  "expedited": true,
  "processingFee": 0.0
}
```

- `processedAt`: Timestamp when the order was processed
- `processingSequence`: Original sequence in the input array
- `expedited` and `processingFee`: Set based on priority
- Output filename: `processed-ORD-004-YYYYMMDD-HHMMSS.json`

## Business Logic

- **HIGH Priority**: Expedited processing, no processing fee
- **MEDIUM Priority**: Standard processing, $5.00 fee
- **LOW Priority**: Standard processing, $2.00 fee


## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)
> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
