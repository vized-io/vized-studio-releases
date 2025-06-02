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
| [**Content-Based Router**](EIP/ContentBasedRouter/README.md) | Route messages to different destinations based on message content | • Route orders based on order type<br>• Direct support requests to appropriate departments|
| [**Recipient List**](EIP/RecipientList/README.md) | Send a copy of the message to one or more recipients | • Broadcasting messages to multiple services or microservices<br>• Sending alerts to multiple channels | 
| [**Message Filter**](EIP/MessageFilter/README.md) | Remove unwanted messages from a channel based on criteria | • Filter out invalid messages<br>• Remove duplicate messages |
| [**CircuitBreaker**](EIP/CircuitBreaker/README.md) | Prevents repeated failures from impacting your system | • Halts execution temporarily<br>• recover system  gracefully | 
| [**Aggregator**](EIP/Aggregation/README.md) | Combine multiple related messages into a single message | • Combine order items into complete order<br>• Merge partial results from multiple systems | 
| [**Ports and Adapters**](EIP/PortsAndAdapters/README.md) | Structure your application logic to be independent of external technologies | • Decouple core business logic<br>• Easily swap external dependencies (databases, APIs) |


## AI Integration Examples

| Pattern | Description | Example Use Cases |
|---------|-------------|-------------------|
| [**AI Ticket Agent**](Ai/TicketAgent/README.md) | Automate ticket resolution | • Resolve customer support tickets<br>• Handle technical issues |
| [**AI Email Summarizer**](Ai/EmailSummarizer/README.md) | Summarize emails for quick review | • Summarize long emails<br>• Extract key points from emails |
| [**AI Sentiment Analysis**](Ai/SentimentAnalysis/README.md) | Analyze sentiment of text | • Analyze customer feedback<br>• Monitor social media sentiment |

<!-- | [**AI Document Summarizer**](Ai/DocumentSummarizer/README.md) | Summarize documents for quick review | • Summarize long documents<br>• Extract key points from documents |
| [**AI Image Captioning**](Ai/ImageCaptioning/README.md) | Generate captions for images | • Generate captions for product images<br>• Add captions to images for accessibility | -->
<!-- | **Message Transformer** | Change format, structure, or content of messages | • Convert between XML, JSON, CSV<br>• Map fields between data models | -->

<!-- | **API Gateway** | Create a single entry point for multiple microservices | • Unify access to backend services<br>• Implement authentication |  -->
<!-- | **Dead Letter Channel** | Handle failed message processing | • Capture failed messages for analysis<br>• Implement retry mechanisms | 
| **File Transfer** | Reliable file transfer between systems | • Secure file transfers<br>• ETL processes |
| **Request-Reply** | Send a request and wait for corresponding reply | • Synchronous API calls<br>• Database query operations | 
| **Publish-Subscribe** | Broadcast messages to multiple recipients | • Notify systems about events<br>• Implement event-driven architectures | 

 -->

