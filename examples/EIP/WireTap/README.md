# Wire Tap Pattern with VIZED & Apache Camel

## What is the Wire Tap Pattern?

The **Wire Tap** pattern is a fundamental Enterprise Integration Pattern (EIP) that enables **non-intrusive message copying**. It allows you to copy messages from the main processing flow to one or more secondary processes without affecting the primary flow. The wire tap pattern is essential for scenarios where you need to perform additional processing like auditing, monitoring, analytics, or notifications without slowing down or risking the main business process.

## Overview

This tutorial demonstrates how to implement the **Wire Tap Pattern** using **VIZED** and **Apache Camel**. You'll learn how to create a healthcare patient monitoring system that continuously processes vital signs while simultaneously copying data to multiple systems for research, compliance, analytics, and family notifications.

## Key Features

- **Real-time Patient Monitoring**: Continuous vital signs processing from medical devices
- **Multiple Wire Taps**: Four different wire tap destinations for various business needs
- **Non-intrusive Processing**: Secondary processes don't affect primary patient monitoring
- **Asynchronous Operations**: All wire taps process data independently and asynchronously
- **Conditional Wire Taps**: Smart routing based on alert levels and conditions
- **Data Anonymization**: Research data automatically anonymized for privacy compliance
- **Comprehensive Logging**: Track every step of the wire tap processing
- **Fault Isolation**: Failures in wire taps don't affect main monitoring flow

## System Architecture

The solution uses Apache Camel's integration framework with the following components:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Vital Signs     │───▶│ Main Processing │───▶│ Dashboard       │
│ Generator       │    │ Flow            │    │ Update          │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                    ┌───────────┼───────────┐
                    ▼           ▼           ▼
            ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
            │ Research    │ │ Compliance  │ │ Analytics   │
            │ Data        │ │ Audit       │ │ Patterns    │
            │ (Wire Tap 1)│ │ (Wire Tap 2)│ │ (Wire Tap 3)│
            └─────────────┘ └─────────────┘ └─────────────┘
                                │
                                ▼
                        ┌─────────────┐
                        │ Family      │
                        │ Notifications│
                        │ (Wire Tap 4)│
                        └─────────────┘
```

## Business Scenario

This example simulates a real-world **Hospital Patient Vital Signs Monitoring System** with the following workflow:

### Main Flow
- **Vital Signs Collection**: Continuous monitoring of patient vital signs (heart rate, blood pressure, oxygen saturation, temperature, respiratory rate)
- **Real-time Processing**: Immediate analysis and dashboard updates for medical staff
- **Alert Generation**: Automatic classification of vital signs as NORMAL, WARNING, or CRITICAL

### Wire Tap Flows
1. **Research Data Collection**: All patient data copied and anonymized for medical research studies
2. **Regulatory Compliance**: Critical events copied to audit systems for HIPAA and FDA compliance
3. **Predictive Analytics**: All data copied to AI/ML systems for early warning predictions
4. **Family Notifications**: Critical alerts copied to family notification systems

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view
2. Create a new Integration Project for your Wire Tap solution

### 2. Configure Vital Signs Generator

Set up the primary data source using Timer component:

1. Click the "Add Route" button in the visual designer
2. Search for the Timer Component in the Component tab
3. Configure it with:
   - **Name**: `vitalSignsGenerator`
   - **Delay**: 3000ms (generates data every 3 seconds)
   - **Repeat Count**: 20 (for demonstration)

### 3. Implement Vital Signs Generation Logic

Add realistic patient data generation:

1. Add a Set Body processor with Groovy expression
2. Generate random but realistic vital signs:
   - Heart Rate: 60-100 bpm (normal range)
   - Blood Pressure: 90-140/60-90 mmHg
   - Oxygen Saturation: 95-100%
   - Temperature: 36.0-38.5°C
   - Respiratory Rate: 12-20 breaths/min

3. Include occasional critical conditions (10% chance) to demonstrate wire tap routing

### 4. Set Up Main Processing Flow

Create the core processing route:

1. Add a Direct component with URI `direct:processVitalSigns`
2. This becomes the main processing hub where wire taps will be attached

### 5. Implement Wire Tap 1: Research Data Collection

Add the first wire tap for medical research:

1. Add a Wire Tap processor with:
   - **URI**: `direct:researchDataCollection`
   - **Copy**: true (ensures original message is preserved)
   - **Condition**: All messages (unconditional)

2. Create the research data route:
   - Anonymize patient data (replace patient ID with anonymous ID)
   - Add research metadata
   - Store in `research-data/collected/` directory

### 6. Implement Wire Tap 2: Compliance Audit

Add conditional wire tap for regulatory compliance:

1. Add a Choice processor to check for critical conditions
2. When condition `$.alertLevel == 'CRITICAL'` is true:
   - Add Wire Tap with URI `direct:complianceAudit`
   - Copy: true

3. Create the compliance audit route:
   - Add HIPAA and FDA compliance metadata
   - Include audit ID and retention policies
   - Store in `compliance-audit/critical-events/` directory

### 7. Implement Wire Tap 3: Predictive Analytics

Add wire tap for AI/ML analytics:

1. Add a Wire Tap processor with:
   - **URI**: `direct:predictiveAnalytics`
   - **Copy**: true
   - **Condition**: All messages

2. Create the analytics route:
   - Add trend analysis indicators
   - Calculate risk scores
   - Include ML model version information
   - Store in `analytics-data/patterns/` directory

### 8. Implement Wire Tap 4: Family Notifications

Add conditional wire tap for family alerts:

1. Add a Choice processor to check for critical conditions
2. When condition `$.isCritical == true`:
   - Add Wire Tap with URI `direct:familyNotification`
   - Copy: true

3. Create the family notification route:
   - Generate notification messages
   - Include family contact information
   - Add urgency levels
   - Store in `family-notifications/critical-alerts/` directory

### 9. Complete Main Flow Processing

Finish the main processing flow:

1. Add dashboard update logic
2. Extract patient ID for monitoring
3. Create real-time monitoring updates
4. Store in `dashboard-updates/real-time/` directory

## Running the Integration Project

1. Select your integration project in VIZED
2. Right-click on the `WireTap.camel.yaml` file and select "Run" from the context menu
3. Watch the console logs to see the wire tap pattern in action
4. Check the output directories to see different types of processed data

## Sample Input Data

The system generates realistic vital signs data like:

```json
{
  "patientId": "P001",
  "timestamp": "2024-01-15T14:30:22.123",
  "ward": "ICU",
  "deviceType": "Philips_Monitor",
  "deviceId": "DEV-1234",
  "vitalSigns": {
    "heartRate": 72,
    "bloodPressure": {
      "systolic": 120,
      "diastolic": 80
    },
    "oxygenSaturation": 98,
    "temperature": 36.8,
    "respiratoryRate": 16
  },
  "alertLevel": "NORMAL",
  "isCritical": false
}
```

## Expected Output

The wire tap pattern produces different outputs in various directories:

### Research Data (research-data/collected/)
```json
{
  "patientId": "ANON-001",
  "researchId": "R-1642258222123",
  "dataUsage": "MEDICAL_RESEARCH",
  "anonymized": true,
  "vitalSigns": { ... }
}
```

### Compliance Audit (compliance-audit/critical-events/)
```json
{
  "auditInfo": {
    "auditId": "AUDIT-1642258222123",
    "complianceStandard": "HIPAA",
    "regulatoryBody": "FDA",
    "eventType": "CRITICAL_VITAL_SIGNS",
    "retentionPeriod": "7_YEARS"
  },
  "vitalSigns": { ... }
}
```

### Analytics Data (analytics-data/patterns/)
```json
{
  "analyticsData": {
    "trendAnalysis": {
      "heartRateTrend": "HIGH",
      "oxygenTrend": "DECLINING",
      "temperatureTrend": "ELEVATED"
    },
    "riskScore": 65,
    "mlModelVersion": "v2.1.3"
  },
  "vitalSigns": { ... }
}
```

### Family Notifications (family-notifications/critical-alerts/)
```json
{
  "notificationId": "NOTIFY-1642258222123",
  "alertLevel": "CRITICAL",
  "message": "Critical vital signs detected for patient P001. Medical team has been alerted.",
  "familyContacts": [
    {
      "name": "Emergency Contact",
      "phone": "+1-555-0101",
      "email": "emergency@family.com"
    }
  ]
}
```

## Wire Tap Pattern Benefits

### 1. Non-Intrusive Processing
- **Main Flow Isolation**: Wire taps don't affect the primary patient monitoring flow
- **Performance**: Main flow continues at full speed regardless of wire tap processing
- **Reliability**: Failures in wire taps don't impact critical patient care

### 2. Separation of Concerns
- **Research**: Dedicated processing for medical research data collection
- **Compliance**: Specialized handling for regulatory requirements
- **Analytics**: Focused processing for predictive modeling
- **Notifications**: Targeted family communication systems

### 3. Asynchronous Operations
- **Independent Processing**: Each wire tap processes data at its own pace
- **Scalability**: Wire taps can be scaled independently based on requirements
- **Flexibility**: Easy to add, modify, or remove wire taps without affecting main flow

### 4. Conditional Routing
- **Smart Filtering**: Only critical events trigger compliance and family notification wire taps
- **Resource Efficiency**: Reduces unnecessary processing for non-critical data
- **Business Logic**: Wire taps can implement complex routing conditions

## Configuration Options

The system supports various configuration options in `application.properties`:

```properties
# Wire Tap Configuration
wire.tap.research.enabled=true
wire.tap.compliance.enabled=true
wire.tap.analytics.enabled=true
wire.tap.family.notifications.enabled=true

# Alert Thresholds
vital.signs.heart.rate.critical.high=130
vital.signs.heart.rate.critical.low=45
vital.signs.oxygen.saturation.critical=90
vital.signs.temperature.critical=38.5

# Data Retention Policies
research.data.retention.days=2555  # 7 years
compliance.audit.retention.days=2555  # 7 years
analytics.data.retention.days=365  # 1 year
family.notifications.retention.days=90  # 3 months
```

## Monitoring and Debugging

### Log Analysis
Watch the console logs to understand the wire tap flow:
- Main flow processing logs show primary patient monitoring
- Wire tap logs show secondary processing activities
- Error logs help identify issues in specific wire tap routes

### File System Monitoring
Monitor the output directories to verify wire tap functionality:
- Check file creation timestamps
- Verify data content and format
- Monitor file counts and sizes

### Performance Metrics
- Main flow processing time should remain consistent
- Wire tap processing can vary based on complexity
- Overall system throughput should not be affected by wire taps

## Troubleshooting

### Common Issues

1. **Wire Tap Not Triggering**
   - Check conditional expressions (JSONPath syntax)
   - Verify message format matches expected structure
   - Review log messages for routing decisions

2. **Main Flow Affected by Wire Tap**
   - Ensure `copy: true` is set on all wire taps
   - Check for exceptions in wire tap routes
   - Verify proper error handling

3. **Missing Output Files**
   - Check directory permissions
   - Verify file component configuration
   - Review auto-create-missing-directories setting

4. **Performance Issues**
   - Monitor wire tap processing complexity
   - Consider async processing for heavy operations
   - Review resource allocation and scaling

## Advanced Patterns

### Dynamic Wire Tap Configuration
Implement runtime wire tap enabling/disabling using Control Bus pattern.

### Content-Based Wire Tapping
Use different wire tap destinations based on message content or headers.

### Wire Tap Aggregation
Combine multiple wire tap outputs for consolidated reporting.

### Error Handling in Wire Taps
Implement Dead Letter Channel pattern for wire tap error recovery.

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)
