# Splitter & Resequencer with VIZED & Apache Camel

## What are Splitter and Resequencer Patterns?

**Splitter** and **Resequencer** are Enterprise Integration Patterns (EIP) that help process and organize messages in integration workflows:

- **Splitter** breaks a composite message (like an array or batch) into individual messages for separate processing.
- **Resequencer** reorders messages based on a sequence or timestamp, ensuring correct processing order.

## Overview

This tutorial demonstrates how to implement **Splitter** and **Resequencer** using **VIZED** and **Apache Camel YAML DSL**. You'll learn how to process a JSON file containing multiple restaurant orders, split them into individual messages, enrich each order with business logic, and resequence them by delivery time to ensure optimal kitchen workflow.

## Key Features

- **JSON File Processing**: Read and parse JSON files with multiple restaurant orders.
- **Message Splitting**: Process each order individually.
- **Order Enrichment**: Add processing metadata and business rules.
- **Resequencing**: Ensure orders are processed in delivery time order for optimal kitchen workflow.
- **Dynamic File Writing**: Output each processed order as a separate JSON file.
- **Comprehensive Logging**: Track every step for easy debugging.

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer.
2. Search for the File Component in the Component tab.
3. Configure it to read `orders.json` from the `orders/incoming` directory.
4. Set parameters: `delay=5000` (polling interval), `initialDelay=2000` (startup delay).

https://github.com/user-attachments/assets/d027650c-8bc3-4352-ba45-c55a4c28ebae

### 3. Parse and Split JSON Data

Convert the JSON content into a structured format and split it into individual records:

1. Add an Unmarshal processor and configure it to parse JSON data using Jackson library.
2. Add a Split processor to process each order individually.

### 4. Enrich Orders with Business Logic

Enhance each order with processing metadata and business rules:

1. Add Set Header processors to extract order information (orderId, orderTime, deliveryTime, priority).
2. Add a Set Body processor with Groovy script to enrich orders with:
   - Processing timestamp
   - Original processing sequence number
   - Priority-based business rules (expedited flag, processing fee)

### 5. Implement Resequencing Logic

Ensure orders are processed in delivery time order for optimal kitchen workflow:

1. Add a Resequence processor to reorder messages by `deliveryTime`.
2. Configure batch settings for optimal performance.

### 6. Output Processed Orders

Write each processed order to a separate file:

1. Add a Marshal processor to convert back to JSON format.
2. Add a Set Header processor to create dynamic filenames.
3. Add a To processor to write files to `orders/processed` directory.

https://github.com/user-attachments/assets/afcc2223-45b9-472d-8d84-f3688234c51d

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Monitor the logs to see orders being processed and resequenced.
4. Check the `orders/processed` directory for output files.

https://github.com/user-attachments/assets/57fd55f5-f0eb-4956-b532-05792ba4908c


## Sample Input Data

Place the following file as `orders/incoming/orders.json`:

```json
[
  {
    "orderId": "REST-001",
    "tableId": "TABLE-5", 
    "items": ["Burger", "Fries"],
    "orderTime": "18:30",
    "deliveryTime": "19:00",
    "priority": "HIGH"
  },
  {
    "orderId": "REST-002",
    "tableId": "TABLE-3",
    "items": ["Pizza"], 
    "orderTime": "18:45",
    "deliveryTime": "18:55",
    "priority": "HIGH"
  },
  {
    "orderId": "REST-003",
    "tableId": "TABLE-7",
    "items": ["Salad"],
    "orderTime": "18:20", 
    "deliveryTime": "19:30",
    "priority": "LOW"
  }
]
```

## Expected Output

Each order will be processed in delivery time order, enriched, and written as a separate JSON file in `orders/processed/`.

**Example output for `REST-002` (earliest delivery time):**

```json
{
  "orderId": "REST-002",
  "tableId": "TABLE-3",
  "items": ["Pizza"],
  "orderTime": "18:45",
  "deliveryTime": "18:55",
  "priority": "HIGH",
  "processedAt": "2024-07-01T12:34:56.789Z",
  "processingSequence": 2,
  "expedited": true,
  "processingFee": 0.0
}
```

- `processedAt`: Timestamp when the order was processed
- `processingSequence`: Original sequence in the input array
- `expedited` and `processingFee`: Set based on priority (HIGH: expedited=true, fee=0.0; MEDIUM: expedited=false, fee=5.0; LOW: expedited=false, fee=2.0)
- Output filename: `processed-REST-002-YYYYMMDD-HHMMSS.json`

## Business Logic

The enrichment process adds the following business rules based on order priority:

- **HIGH Priority**: Expedited processing with no additional fee
- **MEDIUM Priority**: Standard processing with $5.00 fee
- **LOW Priority**: Standard processing with $2.00 fee

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)
> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
