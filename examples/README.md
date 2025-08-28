# VIZED Examples

Welcome to VIZED, the visual integration development environment designed to simplify complex integration workflows. Here we have provided some examples of common integration patterns you can implement using VIZED.

## Download and Install the Latest Release

[![Download for Windows](https://img.shields.io/badge/Download%20for%20Windows-blue?logo=windows)](https://github.com/vized-io/vized-studio-releases/releases/download/0.1.6/vized-windows-0.1.6.exe) 
[![Download for Mac](https://img.shields.io/badge/Download%20for%20Mac-grey?logo=apple)](https://github.com/vized-io/vized-studio-releases/releases/download/0.1.6/vized-mac-0.1.6.dmg) 
[![Goto Releases](https://img.shields.io/badge/Releases-purple)](https://github.com/vized-io/vized-studio-releases/releases) 

## Prerequisites

Before you begin, you'll need to install three important pieces of software:

### Install Java (OpenJDK)
1. Visit the [Java SE Development Kit (JDK) download page](https://adoptium.net/)
2. Download the LTS (Long Term Support) version (e.g., Java 17 or Java 21) for your operating system.
3. Run the installer, accepting the default settings
4. Verify Java Installation
    - Open a command prompt (cmd) / terminal.
    - Type this command:
      ```bash
      java --version
      ```

### Install JBang
<!-- JBang is a tool that lets you run Java applications easily from the command line. It's especially useful for running Apache Camel via the Camel CLI. -->
1. Visit the [official JBang download page](https://jbang.dev/download)
2. Choose the installation method based on your operating system below.
      #### For Windows
      1. Open **PowerShell** as Administrator.
      2. Run the following command:
          ```powershell
          iex "& { $(iwr https://get.jbang.dev) }" 
          ```
      #### For Mac Users (using Homebrew):
      1. Open **Terminal**.
      2. Run the following command:
          ```bash
          brew install jbangdev/tap/jbang
          ```
      #### For Linux Users:
      1. Open **Terminal**.
      2. Run the following command:
          ```bash
          curl -Ls https://sh.jbang.dev | bash -s - app setup
          ```
3. Verify JBang Installation
    ```bash
    jbang version
    ```

### Install Apache Camel
1. Install Apache Camel using JBang (recommended)
2. Run the following command in your terminal:
   ```bash
    jbang app install camel@apache
    ```
3. Verify Apache Camel Installation
    ```bash
    camel --version
    ```

## Integration Pattern Examples

VIZED supports implementing various Enterprise Integration Patterns (EIPs) with a visual, drag-and-drop interface. Below is a table of common integration patterns you can build:

| Pattern | Description | Example Use Cases |
|---------|-------------|-------------------|
| [**Aggregator**](EIP/Aggregation/README.md) | Combine multiple related messages into a single message | • Combine order items into complete order<br>• Merge partial results from multiple systems | 
| [**API Authentication**](EIP/ApiAuthentication) | Secure API endpoints with various authentication methods | • Implement OAuth 2.0 flows<br>• Handle API keys and tokens |
| [**Change Data Capture**](EIP/ChangeDataCapture) | Capture changes in data sources and propagate them | • Monitor database changes<br>• Sync data between systems |
| [**Circuit Breaker**](EIP/CircuitBreaker) | Prevents repeated failures from impacting your system | • Halts execution temporarily<br>• recover system  gracefully | 
| [**Content-Based Router**](EIP/ContentBasedRouter) | Route messages to different destinations based on message content | • Route orders based on order type<br>• Direct support requests to appropriate departments|
| [**Control Bus**](EIP/ControlBus) | Manage and monitor integration components at runtime | • Dynamically start/stop routes<br>• Monitor system health and metrics |
| [**Dead Letter Channel**](EIP/DeadLetterChannel) | Handle failed message processing | • Capture failed messages for analysis<br>• Implement retry mechanisms |
| [**Jsonata Mapper**](EIP/JsonataMapper) | Transform JSON data using JSONata expressions | • Convert JSON structures<br>• Map fields between different JSON formats |
| [**Message Filter**](EIP/MessageFilter) | Remove unwanted messages from a channel based on criteria | • Filter out invalid messages<br>• Remove duplicate messages |
| [**Ports and Adapters**](EIP/PortsAndAdapters) | Structure your application logic to be independent of external technologies | • Decouple core business logic<br>• Easily swap external dependencies (databases, APIs) |
| [**Recipient List**](EIP/RecipientList) | Send a copy of the message to one or more recipients | • Broadcasting messages to multiple services or microservices<br>• Sending alerts to multiple channels | 
| [**SAGA**](EIP/SAGA) | Manage distributed transactions across multiple services | • Coordinate multi-step business processes<br>• Handle compensation for failed transactions |
| [**Resequencer**](EIP/SplitterResequencer) | Reorder messages based on a specific key | • Ensure messages are processed in the correct order<br>• Handle out-of-order events |
| [**Request-Reply**](EIP/RequestReply) | Send a request and wait for corresponding reply | • Synchronous API calls to external services<br>• Credit checks and financial verifications<br>• Real-time data validation |
| [**XSLT Mapper**](EIP/XsltMapper) | Transform XML documents using XSLT  | • Transform XML data structures • Modify XML data |


## AI Agent Examples

VIZED also supports implementing AI Agents connecting with various LLM APIs to build smart workflow.

| Project | Use Cases Description |
|---------|-------------|
| [**Email Summariser**](AI-Agents/EmailSummariser) | Summarize long email contents for quick view of highlights
| [**Image Recognition**](AI-Agents/ImageRecognition) | Analyze images to identify objects, scenes, and text |
| [**RAGBot**](AI-Agents/RagBot) | Retrieve relevant information from documents using Retrieval-Augmented Generation (RAG) and answer user queries |
| [**Sentiment Analyser**](AI-Agents/SentimentAnalyser) | Analyze the sentiments of feedbacks and store in mongodb
| [**Ticket Agent**](AI-Agents/TicketAgent) | Automate analysing sentiments on support requests and prioritize 
| [**Weather Agent**](AI-Agents/WeatherAgent) | AI to retrieve weather information for a specified location |
