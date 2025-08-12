# RAG with VIZED & Apache Camel

## What is RAG?

Retrieval-Augmented Generation (RAG) is an AI technique that enhances Large Language Models (LLMs) by grounding them in external knowledge. Instead of relying solely on its training data, a RAG system first retrieves relevant information from a specified knowledge base and then uses that context to generate a more accurate and informed response.

## Overview

This example demonstrates how to build a powerful, yet simple, RAG-based question-answering bot using **VIZED** and **Apache Camel**. The integration accepts questions via HTTP or a console, generates text embeddings using **Google Gemini**, searches a **Chroma** vector database for relevant context, and then calls an LLM to generate a final, context-aware answer.

## Key Features

- **Dual Input Channels**: Accepts questions via an HTTP endpoint (`POST /ragbot`) or an interactive console.
- **AI-Powered Embeddings**: Utilizes Google Gemini for high-quality text embeddings.
- **Vector Search**: Queries a Chroma vector database to find the most relevant context for a given question.
- **Grounded Responses**: Generates answers with an LLM that are grounded in the retrieved context, reducing hallucinations.
- **Flexible Configuration**: Easily configure models, API keys, and database settings.

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **Platform HTTP**: Receives user questions via a RESTful endpoint.
- **Stream (Console)**: Provides an interactive command-line interface for testing.
- **Google Gemini**: Used for both `embedContent` (generating embeddings) and `generateContent` (generating answers).
- **Chroma**: A vector database for efficient similarity searches.
- **Apache Camel**: The core engine for routing, transformations, and logging.

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1.  Navigate to the Workspace view.
2.  Create a new Integration Project.

<!-- *(Placeholder for GIF: Creating a new project)* -->

### 2. Configure Source Inputs

Set up the entry points for your integration flow:

1.  Add a **Platform HTTP** component to listen for `POST` requests at `/ragbot`.
2.  Add a **Stream** component (`stream:in`) to accept console input with a prompt.

<!-- *(Placeholder for GIF: Configuring HTTP and Stream components)* -->

### 3. Generate Embeddings with Gemini

Create a route to convert the user's question into a vector embedding:

1.  Define a `direct:geminiEmbeddings` route.
2.  Construct a JSON request and call the Gemini `embedContent` API.
3.  Store the resulting `questionEmbedding` in a property.

<!-- *(Placeholder for GIF: Generating embeddings)* -->

### 4. Search the Vector Database

Create a route to find relevant context in Chroma:

1.  Define a `direct:searchChroma` route.
2.  Use the `questionEmbedding` to query the Chroma collection.
3.  Aggregate the retrieved documents into a single `contextString`.

<!-- *(Placeholder for GIF: Searching Chroma)* -->

### 5. Generate the Final Answer

Create a route to generate a grounded response from the LLM:

1.  Define a `direct:generate-response` route.
2.  Create a prompt that includes both the `contextString` and the `originalQuestion`.
3.  Call the Gemini `generateContent` API and extract the final answer.

<!-- *(Placeholder for GIF: Generating the final answer)* -->

## Running the Integration Project

1.  Ensure your external dependencies (Chroma and Gemini API) are set up correctly (see below).
2.  In VIZED, right-click the `RagBot.camel.yaml` file and select **Run**.
3.  The console will become active, and the HTTP endpoint will be available.



https://github.com/user-attachments/assets/f70fc0d5-ccc3-44ae-a25a-27ace98e4e4d


<!-- *(Placeholder for GIF: Running the project)* -->

## Testing the RAG Bot

### Test via HTTP

Send a `POST` request to `http://localhost:8080/ragbot`:

```bash
curl -X POST http://localhost:8080/ragbot \
  -H "Content-Type: application/json" \
  -d '{"question": "What is RAG and why is it useful?"}'
```

You can also send the question as plain text:

```bash
curl -X POST http://localhost:8080/ragbot \
  -H "Content-Type: text/plain" \
  -d "What is RAG and why is it useful?"
```

### Test via Console

Once the integration is running, type your question directly into the `RAGBot>` prompt in the terminal and press Enter.

## Configuration

Configure the integration by setting the following properties in your environment or in an `application.properties` file:

```properties
# Gemini embeddings model
ragbot.embedding.model=embedding-001
ragbot.embedding.api_key=YOUR_GEMINI_API_KEY

# Chroma vector database
ragbot.chroma.tenant=default_tenant
ragbot.chroma.database=default_db
ragbot.chroma.collection=your_collection

# LLM for response generation
ragbot.llm.model=gemini-1.5-flash
ragbot.api.key=YOUR_GEMINI_API_KEY
```

## External Dependencies Setup

### 1. Chroma

- **Install and Run**: It is recommended to run Chroma using Docker.
- **Create Collection**: Ensure you have a tenant, database, and collection that match your configuration.
- **Ingest Data**: Populate your collection with documents that have been processed using the same embedding model (`embedding-001`).

### 2. Google Gemini API

- **Google Cloud Account**: Create a Google Cloud account if you don't have one.
- **Enable API**: Enable the Gemini API in your Google Cloud project.
- **Generate API Key**: Create an API key and provide it for both the embedding and LLM configurations.

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)
