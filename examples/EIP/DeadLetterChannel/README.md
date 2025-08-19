# Dead Letter Channel with VIZED & Apache Camel

## What is Dead Letter Channel Pattern?

**Dead Letter Channel** is an Enterprise Integration Pattern (EIP) that provides a mechanism for handling messages that cannot be processed successfully. Instead of losing failed messages or causing system crashes, the Dead Letter Channel pattern routes undeliverable messages to a special channel where they can be:

- Stored for later analysis
- Retried with different parameters
- Manually processed by administrators
- Used for system monitoring and alerting

## Overview

This example demonstrates how to implement **Dead Letter Channel** using **VIZED** and **Apache Camel YAML DSL**. The scenario simulates an IoT sensor data processing pipeline where smart manufacturing equipment sends temperature, pressure, and vibration readings. The system handles various failure scenarios gracefully using the Dead Letter Channel pattern.

## Key Features

- **IoT Sensor Data Simulation**: Generate realistic sensor data with occasional corruption
- **Automatic Retry Logic**: Configurable retry attempts with exponential backoff
- **Dead Letter Queue**: Failed messages are routed to a separate queue for analysis
- **Error Classification**: Different handling for different types of failures
- **Critical Alert System**: Special handling for critical sensor failures
- **Comprehensive Logging**: Track all processing steps and failures
- **File-based Storage**: Successful and failed messages stored separately

## Real-World Scenario

In a smart manufacturing facility, IoT sensors continuously monitor:

1. **Temperature sensors** on production equipment (20-100°C range)
2. **Pressure sensors** in hydraulic systems (1000-1500 hPa range)  
3. **Vibration sensors** for predictive maintenance (0-10 Hz range)

**Common Failure Scenarios:**
- Sensor hardware malfunction causing data corruption
- Network issues during data transmission
- External validation services being temporarily unavailable
- Out-of-range readings indicating equipment problems
- Critical production sensors requiring immediate attention

## System Architecture

The solution uses Apache Camel's integration framework with these components:

- **Timer Component**: Simulates continuous sensor data generation
- **Dead Letter Channel**: Handles failed message processing with retry logic
- **File Component**: Stores processed data and failed messages
- **Direct Component**: Internal routing between processing stages
- **Groovy Scripting**: Data generation, validation, and enrichment logic
- **JSONPath**: JSON data extraction and manipulation

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view
2. Create a new Integration Project
3. Name it "DeadLetterChannel"

### 2. Configure the Data Generation Route

The first route simulates IoT sensor data generation:

```yaml
- route:
    id: "iot-sensor-data-ingestion"
    from:
      id: "sensor-data-input"
      uri: "timer:sensorDataGenerator"
      parameters:
        delay: 3000
        repeatCount: 10
```

**Key Configuration:**
- Generates data every 3 seconds
- Runs for 10 iterations (30 seconds total)
- Simulates 5 different sensors with realistic data ranges

### 3. Implement Dead Letter Channel Error Handler

The processing route includes comprehensive error handling:

```yaml
errorHandler:
  id: "dead-letter-error-handler"
  deadLetterChannel:
    deadLetterUri: "direct:deadLetterQueue"
    maximumRedeliveries: 2
    redeliveryDelay: 1000
    retryAttemptedLogLevel: "WARN"
    retriesExhaustedLogLevel: "ERROR"
```

**Configuration Details:**
- **Maximum Redeliveries**: 2 retry attempts
- **Redelivery Delay**: 1 second between retries
- **Dead Letter URI**: Route failed messages to `direct:deadLetterQueue`
- **Logging Levels**: Different log levels for retry attempts and exhaustion

### 4. Add Data Validation Logic

The system validates sensor data using multiple criteria:

- **Data Corruption Check**: Detects "CORRUPT_DATA" values
- **Range Validation**: Ensures readings are within acceptable ranges
- **External Service Simulation**: Simulates external validation service failures
- **Sensor-Specific Logic**: Special handling for different sensor types

### 5. Implement Dead Letter Queue Handler

Failed messages are processed by a dedicated route:

```yaml
- route:
    id: "dead-letter-queue-handler"
    from:
      id: "dead-letter-input"
      uri: "direct:deadLetterQueue"
```

**Dead Letter Queue Features:**
- Enriches failed messages with error metadata
- Stores failure reason, retry count, and timestamp
- Saves failed messages to separate directory
- Triggers alerts for critical sensor failures

### 6. Configure Alert System

Critical sensor failures trigger immediate alerts:

- **Critical Sensor Detection**: Special handling for sensor ID "1"
- **Alert Message Generation**: Creates detailed alert messages
- **File-based Alerting**: Stores alerts in dedicated directory
- **Escalation Logic**: Can be extended for email/SMS notifications

## Running the Example

### Prerequisites

1. **Java 11+**: Ensure Java is installed and configured
2. **JBang**: Install JBang for running Camel applications
3. **VIZED Studio**: For visual editing and debugging

### Execution Steps

1. **Navigate to Project Directory:**
   ```bash
   cd examples/EIP/DeadLetterChannel
   ```

2. **Run the Integration:**
   ```bash
   camel run DeadLetterChannel.camel.yaml
   ```

3. **Monitor Output:**
   - Watch console logs for processing messages
   - Check `processed/` directory for successful messages
   - Check `failed/` directory for dead letter messages
   - Check `alerts/` directory for critical alerts

### Expected Output Structure

```
examples/EIP/DeadLetterChannel/
├── processed/           # Successfully processed sensor data
│   ├── sensor-1-20241219-143022.json
│   ├── sensor-2-20241219-143025.json
│   └── ...
├── failed/             # Failed messages with error metadata
│   ├── failed-sensor-3-20241219-143028.json
│   ├── failed-sensor-5-20241219-143031.json
│   └── ...
├── alerts/             # Critical sensor alerts
│   └── CRITICAL-ALERT-20241219-143034.txt
└── ...
```

## Understanding the Results

### Successful Processing
Messages that pass validation are stored in the `processed/` directory with original sensor data.

### Failed Messages
Failed messages in the `failed/` directory include:
- Original sensor data
- Error information (failure reason, retry count)
- Failure timestamp
- Processing status

### Critical Alerts
When critical sensors (ID: 1) fail, additional alerts are generated in the `alerts/` directory.

## Error Scenarios Demonstrated

1. **Data Corruption**: Sensor 5 always sends corrupted data
2. **Range Violations**: Temperature/pressure outside acceptable ranges  
3. **External Service Failure**: Sensor 3 simulates validation service unavailability
4. **Network Issues**: Random 20% failure rate for other sensors
5. **Critical Sensor Failure**: Special handling for production-critical sensor 1

## Customization Options

### Modify Retry Behavior
```yaml
maximumRedeliveries: 3        # Increase retry attempts
redeliveryDelay: 2000        # Longer delay between retries
```

### Change Sensor Parameters
```groovy
def temperature = 15 + Math.random() * 90  # Different temperature range
def sensorCount = 10                       # More sensors
```

### Add Email Alerts
Replace file-based alerting with email component:
```yaml
- to:
    uri: "mail:admin@company.com?subject=Critical Sensor Alert"
```

## Key Learning Points

1. **Error Resilience**: Dead Letter Channel prevents message loss during failures
2. **Retry Strategies**: Configurable retry logic handles transient failures
3. **Error Classification**: Different handling for different failure types
4. **Monitoring Integration**: Failed messages provide valuable system insights
5. **Business Continuity**: Critical processes continue despite individual failures

## Production Considerations

- **Monitoring**: Set up monitoring for dead letter queue depth
- **Alerting**: Configure real-time alerts for critical failures
- **Capacity Planning**: Size dead letter storage appropriately
- **Data Retention**: Implement policies for failed message cleanup
- **Analysis Tools**: Build dashboards for failure pattern analysis

## Next Steps

- Extend with database storage for failed messages
- Add web dashboard for monitoring sensor health
- Implement automated recovery procedures
- Integrate with enterprise monitoring systems
- Add machine learning for failure prediction
