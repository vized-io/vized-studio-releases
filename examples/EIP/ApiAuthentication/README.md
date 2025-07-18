# API Authentication & Authorization with VIZED & Apache Camel

## What is API Authentication & Authorization?

API Authentication & Authorization is a security mechanism that verifies the identity of users (authentication) and determines what resources they can access (authorization). This example demonstrates implementing multiple authentication methods with scope-based authorization to protect API endpoints effectively.

## Overview

This tutorial demonstrates how to implement **API Authentication with scope-based authorization** using **VIZED** and **Apache Camel**. You'll learn how to create a secure API gateway that supports multiple authentication methods and provides fine-grained access control based on OAuth2 scopes and JWT claims.

## Key Features

- **Multiple Authentication Methods**: API Key, OAuth2 Token Introspection, and OIDC JWT Token Validation
- **Scope-Based Authorization**: Fine-grained access control using OAuth2 scopes
- **Role Mapping**: Automatic mapping of OAuth2 scopes to internal application roles
- **Interactive Testing Interface**: Web-based interface for testing authentication flows
- **OAuth2 Authorization Code Flow**: Complete OAuth2 flow implementation with token management


## System Architecture

The solution uses Apache Camel's integration framework with the following components:
- **HTTP Endpoint**: Accepts API requests with various authentication methods
- **Apache Camel**: Integration and routing engine with authentication logic
- **Keycloak**: OAuth2/OIDC provider for token validation and user management
- **Backend APIs**: Protected services (Profile API, Admin API, Default API)

```
┌─────────────────┐    ┌─────────────────────────────────────────┐    ┌─────────────────┐
│   API Client    │───▶│           Auth Gateway (Camel)          │───▶│  Backend APIs   │
│                 │    │                                         │    │                 │
│ • API Key       │    │ 1. Authentication (API Key/OAuth2/JWT)  │    │ • Profile API   │
│ • OAuth2 Token  │    │ 2. Scope Extraction                     │    │ • Admin API     │
│ • JWT Token     │    │ 3. Role Mapping                         │    │ • Default API   │
│                 │    │ 4. Endpoint Authorization               │    │                 │
└─────────────────┘    │ 5. Route to Appropriate Backend         │    └─────────────────┘
                       └─────────────────────────────────────────┘
                                          │
                                          ▼
                                   ┌─────────────────┐
                                   │   Keycloak      │
                                   │ (OAuth2/OIDC)   │
                                   │ Token Issuer    │
                                   └─────────────────┘
```

## Step-by-Step Implementation Guide

### 1. Create a New Integration Project

Begin by setting up your project workspace in VIZED:

1. Navigate to the Workspace view.
2. Create a new Integration Project for your API authentication solution.

### 2. Configure Your Source Component

Set up the entry point for your integration flow:

1. Click the "Add Route" button in the visual designer.
2. Search for the Platform HTTP Component in the Component tab.
3. Configure it to accept requests on multiple endpoints (`/api/*`, `/oauth2/*`, `/health`).

### 3. Implement Authentication Logic

Create authentication routes for different methods:

1. Add a `direct:api-key-auth` route for API key authentication.
2. Add a `direct:oauth2-introspect` route for OAuth2 token introspection.
3. Add a `direct:jwt-validation` route for OIDC JWT token validation.
4. Use choice processors to route requests based on authentication headers.

### 4. Add Scope-Based Authorization

Implement fine-grained access control:

1. Add scope extraction logic for OAuth2 tokens and JWT claims.
2. Create role mapping from scopes to internal application roles.
3. Implement endpoint protection based on required scopes.
4. Add authorization checks before routing to backend services.

### 5. Configure OAuth2 Authorization Flow

Set up complete OAuth2 flow support:

1. Add OAuth2 authorization endpoint (`/oauth2/authorize`).
2. Implement callback handling (`/oauth2/callback`).
3. Add token exchange and refresh functionality.
4. Create token management interface for web clients.

### 6. Set Up Keycloak (OAuth2/OIDC Provider)

Prepare your authentication provider for token validation:

1. Start Keycloak using Docker:
   ```bash
   docker-compose up -d keycloak
   ```
2. Wait for Keycloak to be ready:
   ```bash
   curl -f http://localhost:8081/health/ready
   ```
3. Import the pre-configured realm:
   - Add `Vized` realm.
   - Add new `vized-client` with appropriate scopes and users.
   - Test users with different scope assignments are created for you.

<video src="assets/keycloak_config.mp4" controls width="500"></video>

https://github.com/user-attachments/assets/0d68654a-7a62-4396-a4f8-9524f81097d8


## Running the Integration Project

1. Select your integration project in VIZED.
2. Right-click on the Camel file and select "Run" from the context menu.
3. Open `http://localhost:8080` in your browser for the interactive testing interface.
4. Test different authentication methods and authorization scenarios.

https://github.com/user-attachments/assets/bb41469a-a40e-46af-a660-37378800d16c


## External Dependencies Setup

### 1. Keycloak (OAuth2/OIDC Provider)

To validate OAuth2 tokens and JWT tokens, you need to set up Keycloak:

1. **Start Keycloak with Docker**:
  ```bash
  docker-compose up -d keycloak
  ```

2. **Wait for Keycloak to be ready**:
  ```bash
  curl -f http://localhost:8081/health/ready
  ```

3. **Import Pre-configured Realm**:
  - The included `keycloak-realm.json` sets up the `Vized` realm
  - Configures `vized-client` with appropriate scopes and users
  - Creates test users with different scope assignments

### 2. Application Configuration

Configure the authentication settings in `application.properties`:

```properties
# API Key Configuration
api.keys=12345,secret-api-key,demo-key,vized-key

# OAuth2 Configuration
oauth2.client.id=vized-client
oauth2.client.secret=rtyJVIiCTlVYgbO3UuStMTavFOW3OppG
oauth2.introspect.url=http://localhost:8081/realms/Vized/protocol/openid-connect/token/introspect

# OIDC Configuration
oidc.issuer=http://localhost:8081/realms/Vized
oidc.jwks.url=http://localhost:8081/realms/Vized/protocol/openid-connect/certs
jwt.audience=camel-gateway

# Scope to Role Mappings
scope.role.mappings.admin=ADMIN
scope.role.mappings.user=USER
scope.role.mappings.profile=PROFILE_ACCESS
```

## Testing the API Authentication

### Sample Requests

**1. API Key Authentication (Full Access)**
```bash
curl -H "x-api-key: your-api-key" http://localhost:8080/api/profile
curl -H "x-api-key: your-api-key" http://localhost:8080/api/admin
```

**2. OAuth2 Token with Admin Scopes**
```bash
# Get token from OAuth2 provider
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&client_id=vized-client&client_secret=rtyJVIiCTlVYgbO3UuStMTavFOW3OppG" \
  "http://localhost:8081/realms/Vized/protocol/openid-connect/token"

# Use the token
curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" http://localhost:8080/api/admin
```

**3. JWT Token with Profile Scopes**
```bash
curl -H "Authorization: Bearer YOUR_PROFILE_JWT_TOKEN" http://localhost:8080/api/profile   # ✅ SUCCESS
curl -H "Authorization: Bearer YOUR_PROFILE_JWT_TOKEN" http://localhost:8080/api/admin     # ❌ 403 FORBIDDEN
```

**4. OAuth2 Authorization Code Flow**
1. Open `http://localhost:8080` in your browser
2. Select "OAuth2 Authorization Flow" from the dropdown
3. Click "Start OAuth2 Flow" to begin authorization
4. Complete authentication in the popup window
5. Use obtained tokens for API access

### Expected Responses

**Success Response (200)**
```json
{
  "service": "Profile API",
  "message": "Profile data retrieved successfully",
  "user_roles": "PROFILE_ACCESS,USER",
  "scopes": "user profile",
  "authenticated_via": "OIDC_JWT",
  "timestamp": "2024-12-26 12:00:00",
  "request_path": "/api/profile",
  "profile": {
    "user_id": "user123",
    "username": "john.doe",
    "email": "john.doe@example.com",
    "roles": "PROFILE_ACCESS,USER"
  }
}
```

**Insufficient Scope Response (403)**
```json
{
  "error": "insufficient_scope",
  "message": "Insufficient scope for path: /api/admin",
  "required_scopes": "admin"
}
```

**Unauthorized Response (401)**
```json
{
  "error": "unauthorized",
  "message": "Invalid or missing authentication credentials"
}
```

## Available Scopes & Endpoints

| Scope | Description | Grants Access To |
|-------|-------------|------------------|
| `admin` | Full administrative access | All endpoints |
| `user` | Basic user access | Default endpoints |
| `profile` | Profile access | GET/POST/PUT `/api/profile` |

**Protected Endpoints:**
- `/api/profile` - Requires `profile` scope
- `/api/admin` - Requires `admin` scope  
- `/api/*` - Default endpoints (requires `user` scope)
- `/health` - Health check endpoint (no auth required)

## Need Help?

We're here to assist you with any questions or issues you may face. Whether you're stuck, confused, or simply need some guidance, we're just a click away!

[![Report a Problem](https://img.shields.io/badge/Report%20a%20Problem-darkred?logo=openbugbounty)](https://github.com/vized-io/artifacts/issues/new/choose)

> **Oops! Bugs happen.** Let us know so we can resolve them quickly. Your feedback is invaluable in helping us improve.

For more examples >> [click here](/examples/README.md)

## Contact us

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/company/vized-io/)
[![Book Meeting](https://img.shields.io/badge/Book%20a%20Meeting-purple?logo=calendar)](https://calendly.com/vidhyasagar-jeevendran/30min)

[<img src="https://github.com/user-attachments/assets/806d0fc0-0a00-4d63-81a3-8f2df15d5528" alt="BuyMeCoffee" width="150"/>](https://buymeacoffee.com/vidhyasagarj)


<!-- ## 📚 References

- [Apache Camel Documentation](https://camel.apache.org/)
- [Camel Netty HTTP Component](https://camel.apache.org/components/latest/netty-http-component.html)
- [OAuth 2.0 Specification](https://oauth.net/2/)
- [OpenID Connect Specification](https://openid.net/connect/)
- [JWT Specification](https://jwt.io/)
- [Keycloak Documentation](https://www.keycloak.org/documentation) -->


