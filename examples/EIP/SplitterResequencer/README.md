# Splitter and Resequencer Pattern Example

This example demonstrates the **Splitter** and **Resequencer** Enterprise Integration Patterns (EIP) using Apache Camel YAML DSL. The integration processes a JSON file containing multiple orders, splits them into individual messages, enriches each order with processing information, and then resequences them based on their order date.

## Pattern Overview

### Splitter Pattern
The Splitter pattern breaks down a composite message into individual messages, each containing data related to a single item. In this example, we split a JSON array of orders into individual order messages.

### Resequencer Pattern
The Resequencer pattern reorders messages based on a sequence identifier. Here, we resequence orders based on their order date to ensure chronological processing.

## Flow Description

1. **File Input**: Reads `orders.json` from the `orders/incoming` directory
2. **JSON Unmarshaling**: Converts JSON to Java objects
3. **Splitting**: Splits the order array into individual order messages
4. **Enrichment**: Adds processing metadata and business logic based on priority
5. **Resequencing**: Reorders messages by order date
6. **Output**: Writes each processed order to a separate JSON file in `orders/processed`

## Key Features

- **Expression-aware Steps**: Uses Simple and Groovy expressions for data manipulation
- **Multiple Components**: File, JSON marshaling/unmarshaling, logging
- **Unique IDs**: Each processing step has a unique identifier
- **URI Parameters**: Demonstrates proper parameter usage
- **Content Enrichment**: Adds processing metadata and business rules
- **Error Handling**: Built-in Camel error handling mechanisms

## File Structure

```
SplitterResequencer/
├── SplitterResequencer.camel.yaml    # Main Camel route definition
├── application.properties             # Configuration properties
├── project.vized.json                # Vized project metadata
├── orders.json                       # Sample input data
├── orders/
│   ├── incoming/                     # Input directory
│   └── processed/                    # Output directory
└── README.md                         # This file
```

## Sample Data

The example includes sample orders with different timestamps to demonstrate resequencing:

- **ORD-004**: 2024-01-15T08:20:00Z (earliest)
- **ORD-002**: 2024-01-15T09:15:00Z
- **ORD-001**: 2024-01-15T10:30:00Z
- **ORD-003**: 2024-01-15T11:45:00Z
- **ORD-005**: 2024-01-15T12:00:00Z (latest)

## Running the Example

### Prerequisites
- Java 11 or higher
- Apache Camel JBang

### Execution
1. Copy `orders.json` to the `orders/incoming` directory
2. Run the integration:
   ```bash
   camel run *.yaml
   ```

### Expected Output
- Orders will be processed in chronological order (despite original array order)
- Each order will be enriched with:
  - Processing timestamp
  - Original processing sequence number
  - Priority-based business rules (expedited flag, processing fee)
- Individual JSON files will be created in `orders/processed`

## Configuration Options

### Resequencer Settings
- **Timeout**: 10 seconds (configurable)
- **Capacity**: 100 messages (configurable)
- **Sequence Expression**: Order date field

### File Component Settings
- **Polling Delay**: 5 seconds
- **No-op Mode**: Enabled (file won't be moved/deleted)
- **Output Naming**: Dynamic filename with timestamp

## Business Logic

The example includes priority-based processing rules:

- **HIGH Priority**: Expedited processing, no processing fee
- **MEDIUM Priority**: Standard processing, $5.00 fee
- **LOW Priority**: Standard processing, $2.00 fee

## Monitoring and Debugging

The route includes comprehensive logging at each step:
- File processing start
- Individual order processing
- Resequencing information
- Completion status

## Extension Ideas

1. **Database Integration**: Store processed orders in a database
2. **REST API**: Add HTTP endpoints for order submission
3. **Message Queues**: Use JMS or Kafka for asynchronous processing
4. **Validation**: Add order validation before processing
5. **Batch Processing**: Group orders by customer or priority
6. **Dead Letter Queue**: Handle failed orders gracefully

## Technical Notes

- Uses Jackson for JSON processing
- Groovy expressions for complex data manipulation
- Simple expressions for basic field access
- File component for input/output operations
- Resequencer with timeout for real-time processing

This example showcases how Vized's visual approach to integration development can simplify complex EIP implementations while maintaining the full power of Apache Camel. 