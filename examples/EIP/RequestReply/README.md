# Request-Reply Pattern with VIZED & Apache Camel

## What is Request-Reply Pattern?

The **Request-Reply** pattern is a fundamental Enterprise Integration Pattern (EIP) that enables **synchronous communication** between systems. In this pattern, a system sends a message to a service and waits for a response before proceeding. This pattern is essential for scenarios where you need immediate feedback or cannot proceed without the response.

## Overview

This tutorial demonstrates how to implement **Request-Reply** using **VIZED** and **Apache Camel**. You'll learn how to create a loan application processing system that integrates with a Credit Bureau API to make informed lending decisions in real-time.

## Key Features

- **REST API Integration**: Real-world loan application submission via HTTP POST
- **Synchronous Processing**: Each loan application waits for credit check completion
- **Real Credit Logic**: Credit scores, debt-to-income ratios, and risk assessment
- **Business Rules**: Automatic loan approval/rejection based on credit criteria
- **Mock Credit Bureau**: Simulates realistic credit bureau API responses
- **Comprehensive Logging**: Track every step of the request-reply process
- **Batch Processing**: Alternative file-based processing for bulk uploads

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view
2. Create a new Integration Project for your routing solution

### 2. Configure REST API Endpoint

Set up the primary entry point for loan applications:

1. Click the "Add Route" button in the visual designer
2. Search for the Platform HTTP Component in the Component tab
3. Configure it with:
   - **Path**: `/api/loan-application`
   - **Method**: POST
   - **Binding Mode**: JSON

### 3. Extract Request Headers

Prepare data for the credit check request:

1. Add Set Header processors to extract:
   - **requestId**: Using JSONPath `$.requestId`
   - **customerId**: Using JSONPath `$.customerId`

### 4. Implement Request-Reply Pattern

This is the core of the pattern - sending a request and waiting for a reply:

1. Add a To processor with:
   - **URI**: `direct:credit-bureau-service`
   - **Pattern**: `InOut` (This enables Request-Reply)
   - **Purpose**: Send customer data and wait for credit response

### 5. Create Credit Bureau Service Route

Implement the service that responds to credit check requests:

1. Create a new route with From processor:
   - **URI**: `direct:credit-bureau-service`

2. Add a Groovy Bean for credit logic:
   ```groovy
   // Credit scoring algorithm
   def creditScore = 300 + (customerId.hashCode() % 550)
   def recommendation = creditScore >= 700 ? "APPROVED" : "REJECTED"
   ```

3. Implement business logic for:
   - Credit score calculation (300-850 range)
   - Debt-to-income ratio analysis
   - Loan amount recommendations
   - Interest rate determination

### 6. Process Credit Response

Handle the response from the credit bureau:

1. Set Headers for output file naming
2. Marshal back to pretty-printed JSON
3. Save to processed directory
4. Return Response to API caller

### 7. Add Health Check Endpoint

Monitor service health:

1. Create a route with Platform HTTP component:
   - **Path**: `/health`
   - **Method**: GET
   - **Response**: Service status JSON

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Go to localhost:8000 and try it out.
4. Also try the batch processing by uploading a file to the `loan-applications/incoming` directory.



https://github.com/user-attachments/assets/ef38c54a-8d5e-4181-9277-ef006063267a



## Testing the API

```bash
curl -X POST http://localhost:8000/api/loan-application \
  -H "Content-Type: application/json" \
  -d '{
    "requestId": "REQ-2024-001",
    "customerId": "CUST-12345",
    "firstName": "John",
    "lastName": "Smith",
    "monthlyIncome": 8500.00,
    "monthlyDebt": 2200.00,
    "requestedAmount": 250000.00,
    "loanPurpose": "HOME_PURCHASE",
    "loanTerm": 30
  }'
```

## Sample Input Data

```json
{
  "requestId": "REQ-2024-001",
  "customerId": "CUST-12345", 
  "firstName": "John",
  "lastName": "Smith",
  "monthlyIncome": 8500.00,
  "monthlyDebt": 2200.00,
  "requestedAmount": 250000.00,
  "loanPurpose": "HOME_PURCHASE",
  "loanTerm": 30
}
```

## Expected Output

```json
{
  "requestId": "REQ-2024-001",
  "customerId": "CUST-12345",
  "creditScore": 742,
  "creditHistory": "GOOD",
  "recommendation": "APPROVED",
  "maxLoanAmount": 250000,
  "interestRate": 4.2,
  "debtToIncomeRatio": 25.88,
  "monthlyIncome": 8500.00,
  "requestedAmount": 250000.00,
  "processingTimestamp": "2024-01-15T14:30:22.123+0000"
}
```

## Business Logic & Credit Scoring

The mock credit bureau implements realistic lending criteria.

### Credit Score Calculation

- **Range**: 300-850 (industry standard)
- **Algorithm**: Based on customer ID hash for consistent results
- **Categories**: 
  - Excellent: 750+ 
  - Good: 700-749
  - Fair: 650-699
  - Poor: 600-649
  - Bad: Below 600

### Loan Decision Matrix
| Credit Score | Debt-to-Income | Decision | Interest Rate |
|-------------|----------------|----------|---------------|
| 700+ | < 40% | **APPROVED** | 3.5% - 4.2% |
| 600-699 | < 50% | **CONDITIONAL** | 5.8% - 7.5% |
| Below 600 | Any | **REJECTED** | 12.0% |

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)
> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
