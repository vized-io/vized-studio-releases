# Dead Letter Channel EIP with VIZED & Apache Camel

## What is the Dead Letter Channel Pattern?

The Dead Letter Channel Enterprise Integration Pattern provides a mechanism for handling messages that cannot be processed successfully. Instead of losing failed messages or causing system crashes, the Dead Letter Channel pattern routes undeliverable messages to a special channel where they can be stored for later analysis, retried with different parameters, manually processed by administrators, or used for system monitoring and alerting.

## Overview

This tutorial demonstrates how to implement the **Dead Letter Channel Pattern** using **VIZED** and **Apache Camel**. You'll learn how to create a robust IoT sensor data processing system that gracefully handles various failure scenarios using automatic retry logic and dead letter queue management.

## Key Features

- **IoT Sensor Data Simulation**: Generate realistic sensor data with occasional corruption
- **Automatic Retry Logic**: Configurable retry attempts with exponential backoff
- **Dead Letter Queue**: Failed messages are routed to a separate queue for analysis
- **Error Classification**: Different handling for different types of failures
- **Critical Alert System**: Special handling for critical sensor failures
- **Comprehensive Logging**: Track all processing steps and failures
- **File-based Storage**: Successful and failed messages stored separately

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **Timer Component**: Simulates continuous sensor data generation
- **Dead Letter Channel**: Handles failed message processing with retry logic
- **File Component**: Stores processed data and failed messages
- **Direct Component**: Internal routing between processing stages
- **Groovy Scripting**: Data generation, validation, and enrichment logic
- **JSONPath**: JSON data extraction and manipulation

## Business Scenario

This example simulates a real-world **IoT Sensor Data Processing System** for a smart manufacturing facility with the following workflow:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│ Sensor Data     │───▶│ Data Validation  │───▶│ Data Processing │
│ Generation      │    │ & Enrichment     │    │ & Storage       │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                                ▼
                    ┌────────────────────────┐
                    │   Dead Letter Channel  │
                    │   (Error Handling)     │
                    └────────────────────────┘
                                │
                                ▼
                    ┌────────────────────────┐
                    │   Dead Letter Queue    │
                    │   (Failed Messages)    │
                    └────────────────────────┘
                                │
                                ▼
                    ┌────────────────────────┐
                    │   Alert System         │
                    │   (Critical Failures)  │
                    └────────────────────────┘
```

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your Dead Letter Channel solution.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer.
2. Search for the Timer Component in the Component tab.
3. Configure it to generate sensor data every 3 seconds for 10 iterations.

### 3. Implement Data Generation Logic

Create the sensor data generation workflow:

1. Add a Groovy script to generate realistic IoT sensor data.
2. Configure different sensor types (temperature, pressure, vibration).
3. Introduce controlled failure scenarios for testing.

### 4. Add Dead Letter Channel Error Handler

Implement the Dead Letter Channel pattern for error handling:

1. Configure error handler with retry logic (2 attempts, 1-second delay).
2. Set up dead letter queue routing for failed messages.
3. Implement comprehensive logging for retry attempts and failures.

### 5. Implement Data Validation

Add validation logic for sensor data:

1. Check for data corruption and range violations.
2. Simulate external service failures.
3. Implement sensor-specific validation rules.

### 6. Configure Dead Letter Queue Handler

Set up processing for failed messages:

1. Create dedicated route for dead letter queue processing.
2. Enrich failed messages with error metadata.
3. Store failed messages in separate directory.

### 7. Add Alert System

Implement critical failure alerting:

1. Detect critical sensor failures (sensor ID "1").
2. Generate detailed alert messages.
3. Store alerts in dedicated directory.

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Monitor the logs to see the sensor data processing and error handling.

## External Dependencies Setup

### 1. Directory Structure

Create the required directory structure for sensor data processing:

```bash
mkdir -p processed
mkdir -p failed
mkdir -p alerts
```

### 2. Sample Sensor Data

The system automatically generates sensor data with the following structure:

```json
{
  "sensorId": "1",
  "sensorType": "temperature",
  "value": 45.2,
  "unit": "celsius",
  "timestamp": "2024-01-15T10:30:45.123Z",
  "location": "production-line-1"
}
```

## Error Handling Operations

### Check Dead Letter Queue Status
```bash
curl http://localhost:8080/admin/dead-letter-status
```

### Retry Failed Messages
```bash
curl -X POST http://localhost:8080/admin/retry-failed-messages \
  -H "Content-Type: application/json" \
  -d '{"sensorId": "3"}'
```

### Clear Dead Letter Queue
```bash
curl -X POST http://localhost:8080/admin/clear-dead-letter-queue
```

### Get Failure Statistics
```bash
curl http://localhost:8080/admin/failure-stats
```

## Expected Output

### Console Logs
```
Sensor data generated for sensor: 1, type: temperature, value: 45.2
Processing sensor data: {"sensorId":"1","sensorType":"temperature","value":45.2}
Sensor data validation successful for sensor: 1
Storing processed data for sensor: 1
Sensor data generated for sensor: 3, type: pressure, value: CORRUPT_DATA
Processing sensor data: {"sensorId":"3","sensorType":"pressure","value":"CORRUPT_DATA"}
Validation failed for sensor: 3 - Data corruption detected
Retry attempt 1 for sensor: 3
Validation failed for sensor: 3 - Data corruption detected
Retry attempt 2 for sensor: 3
Validation failed for sensor: 3 - Data corruption detected
Message moved to dead letter queue for sensor: 3
```

### Dead Letter Queue Response
```json
{
  "status": "success",
  "message": "Dead letter queue status retrieved",
  "failedMessages": 2,
  "criticalAlerts": 1,
  "timestamp": "2024-01-15T10:30:45.123Z"
}
```

### Failure Statistics Response
```json
{
  "totalProcessed": 10,
  "successful": 7,
  "failed": 3,
  "retryAttempts": 6,
  "criticalFailures": 1,
  "failureReasons": {
    "dataCorruption": 2,
    "rangeViolation": 1
  }
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
