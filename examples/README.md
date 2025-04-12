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

| Pattern | Description | Example Use Cases | Implementation Steps |
|---------|-------------|-------------------|----------------------|
| **Content-Based Router** | Route messages to different destinations based on message content | • Route orders based on order type<br>• Direct support requests to appropriate departments | 1. Add source component (e.g., REST endpoint)<br>2. Add Content-Based Router component<br>3. Configure routing conditions<br>4. Connect to destination endpoints |
| **Message Filter** | Remove unwanted messages from a channel based on criteria | • Filter out invalid messages<br>• Remove duplicate messages | 1. Add source component<br>2. Connect to Filter component<br>3. Define filter conditions<br>4. Connect to destination |
| **Splitter** | Break a composite message into individual messages | • Process batch files by splitting into records<br>• Break down complex XML documents | 1. Add source component<br>2. Add Splitter component<br>3. Configure splitting expression<br>4. Connect to downstream processors |
| **Aggregator** | Combine multiple related messages into a single message | • Combine order items into complete order<br>• Merge partial results from multiple systems | 1. Start with multiple inputs or a Splitter<br>2. Add Aggregator component<br>3. Configure correlation and completion<br>4. Connect to output destination |
| **Message Transformer** | Change format, structure, or content of messages | • Convert between XML, JSON, CSV<br>• Map fields between data models | 1. Add Transformer component<br>2. Use visual mapping tool<br>3. Configure data type conversions<br>4. Connect to destination |
| **API Gateway** | Create a single entry point for multiple microservices | • Unify access to backend services<br>• Implement authentication | 1. Create API Gateway project<br>2. Define REST resources/methods<br>3. Configure backend routing<br>4. Add security policies<br>5. Deploy gateway |
| **Dead Letter Channel** | Handle failed message processing | • Capture failed messages for analysis<br>• Implement retry mechanisms | 1. Add Error Handler component<br>2. Configure Dead Letter Channel<br>3. Connect to error destination<br>4. Add retry policies |
| **File Transfer** | Reliable file transfer between systems | • Secure file transfers<br>• ETL processes | 1. Add File/FTP source<br>2. Configure polling and filters<br>3. Add validation/transformation<br>4. Configure destination<br>5. Add error handling |
| **Request-Reply** | Send a request and wait for corresponding reply | • Synchronous API calls<br>• Database query operations | 1. Configure requestor endpoint<br>2. Set up correlation ID<br>3. Configure timeout handling<br>4. Connect to reply processors |
| **Publish-Subscribe** | Broadcast messages to multiple recipients | • Notify systems about events<br>• Implement event-driven architectures | 1. Add message source<br>2. Connect to Pub-Sub component<br>3. Define subscriber routes<br>4. Configure topics/selectors |



