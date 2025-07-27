# Image Recognition with VIZED & Apache Camel

## What is Image Recognition?

Image Recognition is an AI-powered technology that analyzes and interprets visual content, identifying objects, scenes, people, text, and other elements within images. This capability enables applications to understand and extract meaningful information from visual data, transforming raw pixels into structured, actionable insights.

## Overview

This tutorial demonstrates how to implement an **Image Recognition API** using **VIZED** and **Apache Camel**. You'll learn how to create a system that accepts image uploads via HTTP, processes them using AI vision models, and returns detailed analysis of the image content.

## Key Features

- **AI-Powered Image Analysis**: Identify objects, scenes, people, and text in images
- **Multipart File Upload**: Process images via HTTP POST requests
- **LLM Integration**: Leverage Google's advanced vision models
- **Structured Analysis**: Receive detailed, organized descriptions of image content
- **Web Interface**: User-friendly UI for image uploads and viewing results
- **Error Handling**: Robust error management for AI service failures

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **HTTP Endpoint**: Accepts image uploads via multipart/form-data
- **Apache Camel**: Integration and routing engine
- **LLM**: AI-powered image recognition and analysis
- **Web Interface**: HTML/CSS/JS frontend for user interaction

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Web Client    │───▶│ HTTP Endpoint   │───▶│ Image Processing│
│   (Browser)     │    │ (File Upload)   │    │ (Camel Route)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                       │
                                                       ▼
                                             ┌─────────────────┐    ┌─────────────────┐
                                             │    AI  MODEL    │───▶│ JSON Response   │
                                             │                 │    │ to Client       │
                                             └─────────────────┘    └─────────────────┘
```

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your image recognition solution.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer.
2. Search for the Platform HTTP Component in the Component tab.
3. Configure it to accept POST requests on the `/upload` endpoint with multipart/form-data content type.

https://github.com/user-attachments/assets/bb5e27d7-e9d6-48cb-9834-da26ed9aded5

### 3. Implement Image Processing

Process the uploaded image for AI analysis:

1. Add a Groovy script to handle the file upload and extract the image bytes.
2. Convert the image to the appropriate format for the AI model.
3. Create a LangChain4j UserMessage with image content.

https://github.com/user-attachments/assets/ee3caaa2-e973-46ee-8155-69f7ca14c48f

### 4. Configure AI Integration

Set up the connection to the Google Gemini AI model:

1. Configure the Google Gemini model as a bean in your Camel context.
2. Set appropriate parameters for the AI model (temperature, token limits).
3. Implement error handling for AI service failures.

https://github.com/user-attachments/assets/ac99533f-10da-4863-942f-219213322273

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Open your browser to `http://localhost:8080` to access the web interface.
4. Upload an image and receive AI-powered analysis of its content.

https://github.com/user-attachments/assets/d6bd13ee-b502-4fd1-aa9c-3cbb8ac7ca4a

## Testing the Image Recognition API

### Using cURL

```bash
curl -X POST http://localhost:8080/upload \
  -F "file=@/path/to/your/image.jpg" \
  -F "prompt=Analyze this image in detail"
```

## External Dependencies Setup

### Google Gemini API

To use the Google Gemini vision model, you need to set up API access:

1. **Create a Google Cloud Account**:
   - Register at [Google Cloud](https://cloud.google.com/) if you don't have an account.

2. **Enable the Gemini API**:
   - Go to the Google Cloud Console.
   - Navigate to "APIs & Services" > "Library".
   - Search for "Gemini API" and enable it.

3. **Generate an API Key**:
   - In the Google Cloud Console, go to "APIs & Services" > "Credentials".
   - Create a new API key.
   - Copy the key to your application.properties file.

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/) 
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min) 

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
