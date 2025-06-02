# Sentiment Analyser with VIZED & Apache Camel

## What is Sentiment Analysis?

Sentiment Analysis is the process of determining the emotional tone behind a series of words. It helps in understanding the sentiment expressed in feedback, reviews, or other text data, categorizing it as positive, negative, or neutral.

## Overview

This tutorial demonstrates how to implement **Sentiment Analysis** using **VIZED** and **Apache Camel**. You'll learn how to create a system that processes feedback, analyzes its sentiment using an AI, and stores the results in a MongoDB database.

## Key Features

- **Feedback Integration**: Accept feedback via HTTP POST requests.
- **AI-Powered Sentiment Analysis**: Use AI to classify feedback as positive, negative, or neutral.
- **MongoDB Integration**: Store the analyzed sentiment data in a database.
- **Logging**: Log the feedback and sentiment results for monitoring.

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **HTTP Endpoint**: Accepts feedback via HTTP POST requests.
- **Apache Camel**: Integration and routing engine.
- **OpenRouter/OpenAI API**: Natural language processing for sentiment analysis.
- **MongoDB**: Stores the sentiment analysis results.

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Add an HTTP endpoint to accept feedback via POST requests.
2. Configure the endpoint to listen on `/feedback`.

![source](./assets/source.gif)
<!-- ![Source](https://raw.githubusercontent.com/vized-io/vized-studio-releases/refs/heads/examples/examples/AI-Agents/SentimentAnalyser/assets/analysis.mp4) -->
<!-- <video src="./assets/source.mp4" controls></video> -->


### 3. Implement Sentiment Analysis

Use an AI to analyze the feedback and determine its sentiment:

1. Add a `direct:call-ml` route to call the AI service.
2. Configure the AI to classify the feedback as positive, negative, or neutral.

![Analysis](./assets/analysis.gif)
<!-- <video src="./assets/analysis.mp4" controls></video> -->

### 4. Store Sentiment Results

Store the analyzed sentiment data in MongoDB:

1. Add a `direct:store-sentiment` route to handle database operations.
2. Use a choice processor to store feedback in different collections based on sentiment.

![store](./assets/store.gif)
<!-- <video src="./assets/store.mp4" controls></video> -->

## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Monitor the logs to see feedback being processed and sentiment results being stored in MongoDB.

![executing](./assets/executing.gif)
<!-- <video src="./assets/executing.mp4" controls></video> -->

## External Dependencies Setup

### 1. MongoDB

To store sentiment analysis results, you need to set up MongoDB:

1. Install MongoDB on your system or use a cloud-hosted MongoDB service.
2. Configure the connection details in the Camel route (e.g., host, database, collection).

### 2. LLM API (or Alternative LLM Service)

For sentiment analysis, you need to set up an LLM API:

1. **Create an OpenAI Account** or **OpenRouter Account**:
   - Register at [OpenAI Platform](https://platform.openai.com/signup) or [OpenRouter](https://openrouter.ai/signup).

2. **Generate API Key**:
   - Navigate to API Keys in your OpenAI or OpenRouter dashboard.
   - Create a new secret key.

3. **Select a Model**:
   - Recommended: gpt-4 or gpt-3.5-turbo (OpenAI), qwen/qwen3-1.7b:free (OpenRouter).
   - The prompt is designed to work with ChatGPT-style models.

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
