# Content-Based Routing in Apache Camel (VIZED)

## Overview

This project demonstrates how to implement **Content-Based Routing**, one of the **Enterprise Integration Patterns (EIP)**, using **VIZED** and **Apache Camel**. The routing mechanism inspects the content of incoming messages and dynamically directs them to appropriate destinations based on predefined criteria.

<!-- This route will be developed using **Vized**, a no-code platform that simplifies the creation of Apache Camel routes. -->

## Features

- **Content-Based Routing**: Messages containing the keyword `Error` are redirected to the `ErrorQueue`, while all others go to the `NormalQueue`.
- **Logging**: Logs messages at different stages of processing.
- **File-based Trigger**: The route initiates processing using a `file` component.

## Prerequisites

Before you begin, you'll need to install three important piece of software:

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

## Creating the Route Using Vized
1. Navigate to the Workspace and create a new Integration Project.

    ![Vized - Create Project](/EIP/ContentBasedRouter/assets/CreateIntegrationProject.gif)

2. Click on the "Add Route" button within the visual designer. Search for the File Component and add it from the Component tab.

    ![Vized - Create Project](/EIP/ContentBasedRouter/assets/AddFileComponent.gif)

4. Add the Choice processor and configure multiple routing conditions. For each condition, add a Direct component to define different message destinations.

    ![Vized - Create Project](/EIP/ContentBasedRouter/assets/ChoiceComponent.gif)

6. Create three new routes:

    - ErrorQueue
    - SuccessQueue
    - OtherQueue

    ![Vized - Create Project](/EIP/ContentBasedRouter/assets/AddDirectComponent.gif)

7. Add Log Processor to the each of the destination routes to record message details.

    ![alt text](/EIP/ContentBasedRouter/assets/image.png)


## Running the Project With Monitoring in Vized

Monitor your integration visually using Vized’s built-in Topology View for real-time execution tracking.

- Select your integration project in Vized, Right-click on Camel file, open context menu, and select "Run".

    ![alt text](/EIP/ContentBasedRouter/assets/run.png)

- Once running, Click View Monitoring. Each component (node) in the topology view will show real-time **execution counts** next to it.
    
    ![alt text](/EIP/ContentBasedRouter/assets/RunWithMonitoring.gif)


## 🐞 Running the Project in Debug Mode

Debug your routes step-by-step using Vized’s integrated debugging tools.

- Right-click the Camel file in your project and choose **Debug** from the context menu.

    ![alt text](/EIP/ContentBasedRouter/assets/debug.png)

- The terminal will open, Switch to the **Debug** tab to step through the route, inspect values, and troubleshoot logic in real time.
    
    ![alt text](/EIP/ContentBasedRouter/assets/RunWithDebug.gif)




<hr/>
<hr/>


## 🚀 Running the Project with Monitoring in Vized

Leverage Vized's built-in **Topology View** to monitor your Apache Camel routes in real time.

### Steps to Execute with Monitoring:

1. **Run the Integration Project**
   - In Vized, right-click on the Camel file of your integration project.
   - Select **Run** from the context menu.
   
   ![Run Project](/EIP/ContentBasedRouter/assets/run.png)

2. **Open Monitoring View**
   - Once execution begins, click on **View Monitoring**.
   - The Topology View will display live **execution counts** next to each route component, helping you trace message flow visually.
   
   ![Monitoring View](/EIP/ContentBasedRouter/assets/RunWithMonitoring.gif)


## 🐞 Running the Project in Debug Mode

Debug your routes step-by-step using Vized’s integrated debugging tools.

### Steps to Debug:

1. **Start in Debug Mode**
   - Right-click the Camel file in your project and choose **Debug** from the context menu.
   
   ![Debug Option](/EIP/ContentBasedRouter/assets/debug.png)

2. **Inspect and Trace**
   - The terminal will open for runtime logs.
   - Switch to the **Debug** tab to step through the route, inspect values, and troubleshoot logic in real time.
   
   ![Debugging View](/EIP/ContentBasedRouter/assets/RunWithDebug.gif)








<!-- ## Customization

- Modify the `setBody` expression to change message content dynamically.
- Add more conditions under `choice` for additional routing logic.
- Integrate with external queues like **Kafka**, **RabbitMQ**, or **ActiveMQ**. -->

## Reporting Bugs

If you find a bug or have a feature request, please open an issue on Vized
<!-- (https://github.com/YOUR_GITHUB_REPO/issues). -->

## Buy Me a Coffee

If this project helped you, consider supporting me! ☕


## License

This project is licensed under the **License**. 
<!-- See the [LICENSE](LICENSE) file for details. -->

<!-- ## Contributing

1. Fork the repository.
2. Create a new branch (`feature-branch-name`).
3. Commit your changes.
4. Push to your fork.
5. Create a Pull Request. -->

<!-- 
### 📌 Follow me for more Apache Camel content! -->
