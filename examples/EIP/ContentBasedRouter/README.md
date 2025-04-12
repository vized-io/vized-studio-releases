# Content-Based Routing in Apache Camel (VIZED)

## Overview

This project demonstrates how to implement **Content-Based Routing**, one of the **Enterprise Integration Patterns (EIP)**, using **VIZED** and **Apache Camel**. The routing mechanism inspects the content of incoming messages and dynamically directs them to appropriate destinations based on predefined criteria.

<!-- This route will be developed using **Vized**, a no-code platform that simplifies the creation of Apache Camel routes. -->

## Features

- **Content-Based Routing**: Messages containing the keyword `Error` are redirected to the `ErrorQueue`, while all others go to the `NormalQueue`.
- **Logging**: Logs messages at different stages of processing.
- **File-based Trigger**: The route initiates processing using a `file` component.

## Prerequisites


## Creating the Route Using Vized
1. Navigate to the Workspace and create a new Integration Project.

    ![Vized - Create Project](assets/CreateIntegrationProject.gif)

2. Click on the "Add Route" button within the visual designer. Search for the File Component and add it from the Component tab.

    ![Vized - Create Project](assets/AddFileComponent.gif)

4. Add the Choice processor and configure multiple routing conditions. For each condition, add a Direct component to define different message destinations.

    ![Vized - Create Project](assets/ChoiceComponent.gif)

6. Create three new routes:

    - ErrorQueue
    - SuccessQueue
    - OtherQueue

    ![Vized - Create Project](assets/AddDirectComponent.gif)

7. Add Log Processor to the each of the destination routes to record message details.

    ![alt text](assets/image.png)


## Running the Project With Monitoring in Vized

Monitor your integration visually using Vized’s built-in Topology View for real-time execution tracking.

- Select your integration project in Vized, Right-click on Camel file, open context menu, and select "Run".

    ![alt text](assets/run.png)

- Once running, Click View Monitoring. Each component (node) in the topology view will show real-time **execution counts** next to it.
    
    ![alt text](assets/RunWithMonitoring.gif)


## 🐞 Running the Project in Debug Mode

Debug your routes step-by-step using Vized’s integrated debugging tools.

- Right-click the Camel file in your project and choose **Debug** from the context menu.

    ![alt text](assets/debug.png)

- The terminal will open, Switch to the **Debug** tab to step through the route, inspect values, and troubleshoot logic in real time.
    
    ![alt text](assets/RunWithDebug.gif)




<hr/>
<hr/>


## 🚀 Running the Project with Monitoring in Vized

Leverage Vized's built-in **Topology View** to monitor your Apache Camel routes in real time.

### Steps to Execute with Monitoring:

1. **Run the Integration Project**
   - In Vized, right-click on the Camel file of your integration project.
   - Select **Run** from the context menu.
   
   ![Run Project](assets/run.png)

2. **Open Monitoring View**
   - Once execution begins, click on **View Monitoring**.
   - The Topology View will display live **execution counts** next to each route component, helping you trace message flow visually.
   
   ![Monitoring View](assets/RunWithMonitoring.gif)


## 🐞 Running the Project in Debug Mode

Debug your routes step-by-step using Vized’s integrated debugging tools.

### Steps to Debug:

1. **Start in Debug Mode**
   - Right-click the Camel file in your project and choose **Debug** from the context menu.
   
   ![Debug Option](assets/debug.png)

2. **Inspect and Trace**
   - The terminal will open for runtime logs.
   - Switch to the **Debug** tab to step through the route, inspect values, and troubleshoot logic in real time.
   
   ![Debugging View](assets/RunWithDebug.gif)








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
