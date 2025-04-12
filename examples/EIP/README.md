### **1. Messaging Patterns (6)**

1. **Message Channel** – A channel that transports messages between sender and receiver.
2. **Point-to-Point Channel** – A channel that ensures a message is received by only one consumer.
3. **Publish-Subscribe Channel** – A channel that delivers messages to multiple consumers.
4. **Datatype Channel** – A channel dedicated to messages of a specific data type.
5. **Invalid Message Channel** – A channel for messages that cannot be processed.
6. **Dead Letter Channel** – A channel where failed messages are sent for analysis.

### **2. Message Construction Patterns (9)**

1. **Message** – The fundamental unit of data exchanged between systems.
2. **Command Message** – Encapsulates a command for a receiver to execute.
3. **Document Message** – Represents a data record for the receiver.
4. **Event Message** – Represents a notification that an event has occurred.
5. **Request-Reply** – A message sent expecting a response.
6. **Return Address** – Includes sender information for a reply.
7. **Correlation Identifier** – Helps match requests with responses.
8. **Message Sequence** – A set of messages forming a larger dataset.
9. **Message Expiration** – Defines a time limit for message validity.

### **3. Message Routing Patterns (17)**

1. **Content-Based Router** – Routes messages based on their content.
2. **Message Filter** – Filters out unwanted messages.
3. **Dynamic Router** – Routes messages dynamically based on conditions.
4. **Recipient List** – Sends messages to multiple recipients.
5. **Splitter** – Breaks a large message into smaller parts.
6. **Aggregator** – Combines multiple related messages into one.
7. **Resequencer** – Orders out-of-sequence messages correctly.
8. **Composed Message Processor** – Processes messages in multiple steps.
9. **Scatter-Gather** – Sends a message to multiple processors and gathers responses.
10. **Routing Slip** – Defines a flexible, dynamic processing sequence.
11. **Process Manager** – Manages a sequence of process steps.
12. **Message Broker** – Centralized mediator for routing and transformation.
13. **Normalizer** – Converts messages into a standard format.
14. **Canonical Data Model** – Defines a common data structure for integration.
15. **Pipes and Filters** – Breaks processing into independent steps.
16. **Content Enricher** – Adds missing data to a message.
17. **Content Filter** – Removes unnecessary parts of a message.

### **4. Message Transformation Patterns (8)**

1. **Message Translator** – Converts messages between different formats.
2. **Envelope Wrapper** – Wraps a message with additional metadata.
3. **Claim Check** – Stores part of a message externally for retrieval later.
4. **Message Normalizer** – Standardizes message structure and format.
5. **Content-Based Enricher** – Adds additional context-based data.
6. **Data Format Transformation** – Converts data between different formats.
7. **Data Mapper** – Maps data fields between different schemas.
8. **Event-Based Enricher** – Enriches messages with event-driven information.