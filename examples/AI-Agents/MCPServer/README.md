# MCP Server with VIZED & Apache Camel

## What is Model Context Protocol (MCP)?

Model Context Protocol (MCP) is a standardized protocol that enables AI models to interact with external systems, databases, and resources through a well-defined interface. It provides a way for AI assistants to access tools, read files, query databases, and perform various operations in a secure and controlled manner.

## Overview

This tutorial demonstrates how to implement a **Model Context Protocol (MCP) Server** using **VIZED** and **Apache Camel**. You'll learn how to create a comprehensive MCP server that provides AI models with access to databases, file systems, tools, and resources through JSON-RPC 2.0 communication.

## Key Features

- **JSON-RPC 2.0 Compliant**: Full implementation of the MCP specification
- **Database Integration**: Access to MySQL and MongoDB databases
- **Comprehensive Tools**: Calculator, file operations, system info, and more
- **Resource Management**: File system, database, and system resource access
- **Prompt Templates**: AI-powered prompt generation for various use cases
- **Web Interface**: Interactive test client for server exploration

## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **HTTP Endpoint**: Accepts JSON-RPC requests via HTTP
- **Apache Camel**: Integration and routing engine with MCP protocol handling
- **Database Connectors**: MySQL and MongoDB integration
- **Tool Registry**: Comprehensive set of utility tools for AI models

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   MCP Client    │    │   JSON-RPC 2.0   │    │   Apache Camel  │
│   (AI Model)    │───▶│   HTTP Transport  │───▶│   YAML DSL      │
│                 │    │                  │    │   Routes        │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                                        │
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Resources     │    │   Tools          │    │   Prompts       │
│   • Files       │    │   • Calculator   │    │   • Code Gen    │
│   • MySQL       │◀───│   • File Ops     │◀───│   • Docs        │
│   • MongoDB     │    │   • Database     │    │   • Query Help  │
│   • System      │    │   • Text Proc    │    │                 │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view
2. Create a new Integration Project for your MCP server

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer
2. Search for the Platform HTTP Component in the Component tab
3. Configure it to accept POST requests on the `/mcp` endpoint

### 3. Implement MCP Protocol Handler

Add the core MCP protocol handling logic:

1. Add a `choice` component to route different MCP methods
2. Configure routes for `initialize`, `resources/list`, `resources/read`, `tools/call`, and `prompts/get`
3. Implement proper JSON-RPC 2.0 response formatting

### 4. Create Resource Management Routes

Set up resource handling capabilities:

1. Create routes for listing available resources
2. Implement resource reading functionality for files, databases, and system info
3. Add resource subscription capabilities

### 5. Implement Tool Registry

Add comprehensive tool support:

1. Create individual routes for each tool (calculator, file operations, database queries, etc.)
2. Configure tool parameter validation and error handling
3. Implement tool result formatting

### 6. Add Prompt Template System

Set up AI prompt generation:

1. Create prompt template routes for code generation, documentation, and query assistance
2. Implement dynamic prompt parameter substitution
3. Configure prompt result formatting

## Running the Integration Project

1. Select your integration project in VIZED
2. Right-click on the Camel file and select "Run" from the context menu
3. Access the web interface at `http://localhost:8080` to test the MCP server

## Testing the MCP Server

### Using the Web Interface

1. Open `http://localhost:8080` in your browser
2. Use the interactive test client to:
   - Initialize the server
   - List and read resources
   - Execute tools
   - Generate prompts

### Using curl

```bash
# Initialize server
curl -X POST http://localhost:8080/mcp \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": 1,
    "method": "initialize",
    "params": {
      "protocolVersion": "2025-03-26",
      "capabilities": {},
      "clientInfo": {"name": "Test Client", "version": "1.0.0"}
    }
  }'

# List resources
curl -X POST http://localhost:8080/mcp \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": 2,
    "method": "resources/list"
  }'

# Use calculator tool
curl -X POST http://localhost:8080/mcp \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": 3,
    "method": "tools/call",
    "params": {
      "name": "calculator",
      "arguments": {"operation": "add", "a": 10, "b": 5}
    }
  }'
```

## External Dependencies Setup

### 1. Database Configuration

Configure your databases in `application.properties`:

```properties
# MySQL Database
mcp.database.mysql.host=localhost
mcp.database.mysql.port=3306
mcp.database.mysql.database=mcp_db
mcp.database.mysql.username=root
mcp.database.mysql.password=password

# MongoDB Database
mcp.database.mongodb.host=localhost
mcp.database.mongodb.port=27017
mcp.database.mongodb.database=mcp_db
```

### 2. Server Configuration

```properties
# Server Configuration
camel.server.enabled=true
camel.server.port=8080
mcp.server.name=Vized MCP Server
mcp.server.version=1.0.0
```

## Available Tools

The MCP server provides 8 comprehensive tools:

1. **Calculator**: Mathematical operations (add, subtract, multiply, divide)
2. **File Operations**: Read, write, list, and delete files
3. **Database Query**: Execute queries on MySQL and MongoDB
4. **System Information**: Get OS, memory, CPU, and system status
5. **Text Processor**: Text manipulation (uppercase, lowercase, reverse)
6. **Date/Time Operations**: Date and time manipulation
7. **UUID Generator**: Generate UUIDs (v1 or v4)
8. **Base64 Encoder**: Base64 encoding and decoding

## Available Resources

The server provides access to various resources:

- **Server Config**: `file://config/server.properties`
- **Server Logs**: `file://logs/server.log`
- **MySQL Database**: `mysql://localhost:3306/mcp_db`
- **MongoDB Database**: `mongodb://localhost:27017/mcp_db`
- **System Info**: `system://info`
- **API Docs**: `docs://api`

## Available Prompts

The server provides 3 intelligent prompt templates:

1. **Code Generator**: Generate code based on specifications
2. **Documentation**: Generate documentation templates
3. **Query Helper**: Database query assistance

## Monitoring and Health Check

### Health Check
```bash
curl http://localhost:8080/health
```

### Logging
The server provides comprehensive logging with configurable levels:
- `INFO`: General server operations
- `DEBUG`: Detailed request/response logging
- `ERROR`: Error conditions and stack traces

## Integration with AI Models

This MCP server can be integrated with various AI models and platforms:

- **Claude Desktop**: Configure in `claude_desktop_config.json`
- **VS Code**: Add to MCP server configuration
- **Custom AI Applications**: Use any JSON-RPC 2.0 client
- **LangChain**: Use MCP tools in LangChain applications

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose) 