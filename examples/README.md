# VIZED Examples

Welcome to VIZED, the visual integration development environment designed to simplify complex integration workflows. Here we have provided some examples of common integration patterns you can implement using VIZED.

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
| **Message Filter** | Remove unwanted messages from a channel based on criteria | • Filter out invalid messages<br>• Remove duplicate messages | 
| **Splitter** | Break a composite message into individual messages | • Process batch files by splitting into records<br>• Break down complex XML documents | 
| **Aggregator** | Combine multiple related messages into a single message | • Combine order items into complete order<br>• Merge partial results from multiple systems | 
| **Message Transformer** | Change format, structure, or content of messages | • Convert between XML, JSON, CSV<br>• Map fields between data models | 
| **API Gateway** | Create a single entry point for multiple microservices | • Unify access to backend services<br>• Implement authentication | 
| **Dead Letter Channel** | Handle failed message processing | • Capture failed messages for analysis<br>• Implement retry mechanisms | 
| **File Transfer** | Reliable file transfer between systems | • Secure file transfers<br>• ETL processes |
| **Request-Reply** | Send a request and wait for corresponding reply | • Synchronous API calls<br>• Database query operations | 
| **Publish-Subscribe** | Broadcast messages to multiple recipients | • Notify systems about events<br>• Implement event-driven architectures | 



