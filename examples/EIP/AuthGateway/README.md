# AuthGateway - Scope-Based Authorization Example

This example demonstrates how to implement an API Gateway with **scope-based authorization** using Apache Camel YAML DSL. It showcases three different authentication methods combined with fine-grained authorization based on OAuth2 scopes and JWT claims:

1. **API Key Authentication** - Simple header-based authentication with full access
2. **OAuth2 Token Introspection** - Token validation with scope extraction and role mapping
3. **OIDC JWT Token Validation** - JWT token validation with scope-based routing

## 🆕 What's New: Scope-Based Authorization

This enhanced version adds:
- **Scope Extraction**: Automatically extracts scopes from OAuth2 tokens and JWT claims
- **Role Mapping**: Maps OAuth2 scopes to internal application roles
- **Endpoint Protection**: Different endpoints require different scopes
- **Fine-grained Access Control**: Users can only access resources they have scopes for
- **Interactive Testing Interface**: Web interface for testing authentication and authorization

## 🏗️ Architecture Overview

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

## 🎯 Scope-Based Authorization Flow

1. **Authentication**: Client provides API key, OAuth2 token, or JWT token
2. **Scope Extraction**: Gateway extracts scopes from the token
3. **Role Mapping**: Scopes are mapped to internal application roles
4. **Endpoint Authorization**: Gateway checks if user has required scopes for the endpoint
5. **Routing**: Request is routed to appropriate backend service or denied with 403

## 🔄 OAuth2 Authorization Code Flow

The gateway now supports the complete OAuth2 authorization code flow:

1. **Authorization Request**: User initiates OAuth2 flow via web interface
2. **User Consent**: User authenticates and authorizes the application
3. **Authorization Code**: OAuth2 provider returns authorization code
4. **Token Exchange**: Gateway exchanges code for access and refresh tokens
5. **Token Management**: Tokens are stored and can be refreshed automatically
6. **API Access**: Access token is used for API requests

## 🚀 Quick Start

### 1. Start Keycloak (OAuth2/OIDC Provider)

```bash
# Start Keycloak with pre-configured realm
docker-compose up -d keycloak

# Wait for Keycloak to be ready (check health)
curl -f http://localhost:8081/health/ready
```

### 2. Run the Auth Gateway

```bash
# Run the Camel application
jbang camel@apache/camel run AuthGateway.camel.yaml

# Or with specific properties
jbang camel@apache/camel run AuthGateway.camel.yaml --property-file=application.properties
```

### 3. Test the Gateway

The gateway will be available at `http://localhost:8080`

**Web Interface**: Open `http://localhost:8080` in your browser for an interactive testing interface.

### 4. Test OAuth2 Authorization Flow

1. **Select OAuth2 Authorization Flow** from the authentication dropdown
2. **Click "Start OAuth2 Flow"** to begin the authorization process
3. **Complete authentication** in the popup window (login with Keycloak)
4. **Authorize the application** when prompted
5. **View obtained tokens** in the token management section
6. **Test API endpoints** using the obtained access token

**API Endpoints**:
- `/api/profile` - Requires `profile` scope
- `/api/admin` - Requires `admin` scope
- `/api/*` - Default endpoints (requires `user` scope)
- `/health` - Health check endpoint (no auth required)

## 🔐 Authentication & Authorization Methods

### 1. API Key Authentication

The simplest authentication method using a custom header. API keys grant **full access** to all endpoints.

**Configuration:**
- Header: `x-api-key`
- Valid keys: `12345`, `secret-api-key`, `demo-key`, `vized-key`

**Example:**
```bash
# Successful request - can access any endpoint
curl -H "x-api-key: your-api-key" http://localhost:8080/api/profile
curl -H "x-api-key: your-api-key" http://localhost:8080/api/admin

# Failed request
curl -H "x-api-key: invalid" http://localhost:8080/api/profile
```

**Response:**
```json
{
  "service": "Profile API",
  "message": "Profile data retrieved successfully",
  "authenticated_via": "API_KEY",
  "timestamp": "2024-01-15 10:30:45",
  "request_path": "/api/profile",
  "profile": {
    "user_id": "user123",
    "username": "john.doe",
    "email": "john.doe@example.com",
    "roles": "ADMIN,USER,PROFILE_ACCESS"
  }
}
```

## 🎯 Scope-Based Authorization

### Available Scopes

| Scope | Description | Grants Access To |
|-------|-------------|------------------|
| `admin` | Full administrative access | All endpoints |
| `user` | Basic user access | Default endpoints |
| `profile` | Profile access | GET/POST/PUT `/api/profile` |

### Authorization Examples

#### 1. Admin User (Full Access)
**Scopes**: `admin user profile`
```bash
# Get token from your OAuth2 provider
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET" \
  "https://your-auth-server.com/oauth2/token"

# Use the token
curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" http://localhost:8080/api/admin
```

#### 2. Profile User (Profile Access)
**Scopes**: `user profile`
```bash
# ✅ SUCCESS - Can access profile
curl -H "Authorization: Bearer YOUR_PROFILE_TOKEN" http://localhost:8080/api/profile

# ❌ FORBIDDEN - Cannot access admin
curl -H "Authorization: Bearer YOUR_PROFILE_TOKEN" http://localhost:8080/api/admin
```

### Authorization Responses

#### Success Response (200)
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

#### Insufficient Scope Response (403)
```json
{
  "error": "insufficient_scope",
  "message": "Insufficient scope for path: /api/admin",
  "required_scopes": "admin"
}
```

### 2. OAuth2 Token Introspection

Uses OAuth2 token introspection to validate access tokens and extract scopes.

**Flow:**
1. Client obtains access token from OAuth2 provider
2. Client sends token in Authorization header
3. Gateway validates token via introspection endpoint and extracts scopes
4. Scopes are mapped to internal roles
5. If valid and authorized, request is forwarded to backend

**Get Access Token:**
```bash
# Client Credentials Grant
```

### 3. OAuth2 Authorization Code Flow

The gateway now supports the complete OAuth2 authorization code flow with a web interface.

**Features:**
- **Interactive OAuth2 Flow**: Web-based authorization flow with popup window
- **Token Management**: Automatic storage and refresh of access/refresh tokens
- **Scope Display**: Visual display of obtained scopes with badges
- **Token Refresh**: Automatic token refresh when expired
- **Persistent Storage**: Tokens are stored in localStorage for session persistence

**OAuth2 Endpoints:**
- `GET /oauth2/authorize` - Initiates OAuth2 authorization flow
- `GET /oauth2/callback` - Handles OAuth2 callback (authorization code)
- `POST /oauth2/callback` - Exchanges authorization code for tokens
- `POST /oauth2/refresh` - Refreshes access tokens using refresh token

**Flow Steps:**
1. User clicks "Start OAuth2 Flow" in the web interface
2. Popup window opens with Keycloak login page
3. User authenticates and authorizes the application
4. Authorization code is returned to the callback page
5. Callback page exchanges code for tokens and communicates with main window
6. Popup window automatically closes after successful token exchange
7. Main window receives tokens and updates the UI
8. Tokens are displayed and stored for API access
9. User can refresh tokens or clear them as needed

**Communication Flow:**
- **Popup Window** ↔ **Main Window** communication via `postMessage`
- **Automatic popup closure** after successful token exchange
- **Error handling** with proper error messages to main window
- **Fallback mechanism** using localStorage if communication fails
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET" \
  "https://your-auth-server.com/oauth2/token"
```

**Use Access Token:**
```bash
# Replace YOUR_ACCESS_TOKEN with actual token
curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" http://localhost:8080/api/profile
```

### 3. OIDC JWT Token Validation

Validates JWT tokens using signature verification, claims validation, and scope extraction.

**Flow:**
1. Client obtains JWT token from Keycloak
2. Client sends JWT in Authorization header
3. Gateway validates JWT signature, claims, and extracts scopes
4. Scopes are mapped to internal roles
5. If valid and authorized, request is forwarded to backend

**Get JWT Token:**
```bash
# Password Grant Flow
curl -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&username=YOUR_USERNAME&password=YOUR_PASSWORD&client_id=YOUR_CLIENT_ID" \
  "https://your-auth-server.com/oauth2/token"
```

**Use JWT Token:**
```bash
# Use the JWT token from your OIDC provider
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" http://localhost:8080/api/profile
```

## 🔧 Configuration

### Application Properties

Key configuration options in `application.properties`:

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

# Endpoint Access Control
endpoint.access.profile=profile
endpoint.access.admin=admin
```

### Keycloak Configuration

The included `keycloak-realm.json` sets up:
- **Realm**: `Vized`
- **Client**: `vized-client` with secret `rtyJVIiCTlVYgbO3UuStMTavFOW3OppG`
- **Users**: Pre-configured users with different scope assignments
- **Scopes**: OAuth2 scopes for fine-grained access control

## 🧪 Testing Scenarios

### Basic Authorization Testing

```bash
# 1. API Key Authentication (Full Access)
curl -H "x-api-key: your-api-key" http://localhost:8080/api/profile   # ✅ SUCCESS
curl -H "x-api-key: your-api-key" http://localhost:8080/api/admin     # ✅ SUCCESS

# 2. JWT Token with Admin Scopes (Full Access)
curl -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" http://localhost:8080/api/profile   # ✅ SUCCESS
curl -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" http://localhost:8080/api/admin     # ✅ SUCCESS

# 3. JWT Token with Profile Scopes (Limited Access)
curl -H "Authorization: Bearer YOUR_PROFILE_JWT_TOKEN" http://localhost:8080/api/profile   # ✅ SUCCESS
curl -H "Authorization: Bearer YOUR_PROFILE_JWT_TOKEN" http://localhost:8080/api/admin     # ❌ 403 FORBIDDEN

# 4. JWT Token with User Scopes (Very Limited Access)
curl -H "Authorization: Bearer YOUR_USER_JWT_TOKEN" http://localhost:8080/api/profile      # ❌ 403 FORBIDDEN
curl -H "Authorization: Bearer YOUR_USER_JWT_TOKEN" http://localhost:8080/api/admin        # ❌ 403 FORBIDDEN
```

### Test Error Scenarios

```bash
# No authentication
curl http://localhost:8080/api/profile                            # ❌ 401 UNAUTHORIZED

# Invalid API key
curl -H "x-api-key: invalid" http://localhost:8080/api/profile    # ❌ 401 UNAUTHORIZED

# Invalid Bearer token
curl -H "Authorization: Bearer invalid-token" http://localhost:8080/api/profile # ❌ 401/403

# Malformed JWT token
curl -H "Authorization: Bearer not.a.jwt" http://localhost:8080/api/profile # ❌ 401/403
```

### Interactive Testing

**Web Interface**: Open `http://localhost:8080` in your browser for an interactive testing interface.

**Postman Collection**: Import `AuthGateway.postman_collection.json` for comprehensive API testing.

### Health Check

```bash
# Check gateway health
curl http://localhost:8080/health
```

## 📊 Monitoring & Logging

### Log Output

The application provides detailed logging for each authentication flow:

```
2024-01-15 10:30:45 INFO  [api-gateway] - Incoming request: GET /api/test
2024-01-15 10:30:45 INFO  [api-key-detected] - API Key authentication detected
2024-01-15 10:30:45 INFO  [api-key-validation] - Validating API key: 12345
2024-01-15 10:30:45 INFO  [api-key-valid] - API key validation successful
2024-01-15 10:30:45 INFO  [backend-access] - Request authorized via API_KEY - forwarding to backend service
```

### Metrics

The gateway tracks:
- Authentication success/failure rates by method
- Request processing times
- Token validation times
- Error rates by authentication method

## 🔒 Security Considerations

### Production Deployment

1. **API Keys**: Store in secure vault (HashiCorp Vault, AWS Secrets Manager)
2. **OAuth2 Secrets**: Use environment variables or secure configuration
3. **JWT Validation**: Implement proper JWKS-based signature verification
4. **HTTPS**: Always use HTTPS in production
5. **Rate Limiting**: Implement rate limiting per authentication method
6. **Audit Logging**: Enable comprehensive audit logging

### JWT Verification

The current JWT verification is simplified for demonstration. In production:

```java
// Use proper JWT libraries
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'

// Implement JWKS-based verification
// Verify signature against public keys from JWKS endpoint
// Validate all standard claims (iss, aud, exp, nbf, iat)
```

## 🐳 Docker Deployment

### Build Docker Image

```bash
# Create Dockerfile
cat > Dockerfile << EOF
FROM openjdk:17-jre-slim
COPY AuthGateway.camel.yaml /app/
COPY application.properties /app/
COPY JwtVerifierBean.java /app/
WORKDIR /app
RUN apt-get update && apt-get install -y curl
RUN curl -Ls https://sh.jbang.dev | bash -s - app setup
ENV PATH="/root/.jbang/bin:$PATH"
EXPOSE 8080
CMD ["jbang", "camel@apache/camel", "run", "AuthGateway.camel.yaml"]
EOF

# Build image
docker build -t authgateway:latest .
```

### Run with Docker Compose

```bash
# Use production profile for PostgreSQL backend
docker-compose --profile production up -d

# Or use default H2 database
docker-compose up -d
```

## 🔄 Extending the Example

### Add New Authentication Method

1. **Create new route** in `AuthGateway.camel.yaml`:
```yaml
- route:
    id: auth-custom
    from: "direct:auth-custom"
    steps:
      - log:
          message: "Custom authentication flow"
      # Add your custom authentication logic
```

2. **Update choice condition** in main gateway route:
```yaml
- simple: "${header.CustomAuth} != null"
  steps:
    - to: "direct:auth-custom"
```

### Add Rate Limiting

```yaml
# Add to route
- throttle:
    expression:
      constant: 100
    timePeriodMillis: 60000
```

### Add Circuit Breaker

```yaml
# Add around OAuth2 introspection
- circuitBreaker:
    configuration:
      failureThreshold: 5
      delay: 10000
    steps:
      - to: "{{oauth2.introspect.url}}?bridgeEndpoint=true"
    onFallback:
      - setBody:
          constant: '{"error":"Service temporarily unavailable"}'
```

## 🐛 Troubleshooting

### Common Issues

1. **Keycloak not accessible**:
   ```bash
   # Check if Keycloak is running
   docker-compose ps
   curl http://localhost:8081/health/ready
   ```

2. **Token validation fails**:
   ```bash
   # Check token format
   echo "YOUR_JWT_TOKEN" | cut -d'.' -f2 | base64 -d | jq
   
   # Check token expiration
   echo "YOUR_JWT_TOKEN" | cut -d'.' -f2 | base64 -d | jq '.exp'
   ```

3. **API Key not working**:
   - Verify header name is `x-api-key`
   - Check if key is in `api.keys` property
   - Ensure no extra spaces in key value

### Debug Mode

```bash
# Run with debug logging
jbang camel@apache/camel run AuthGateway.camel.yaml --logging-level=DEBUG

# Or set in application.properties
logging.level.org.apache.camel=DEBUG
```

## 📚 References

- [Apache Camel Documentation](https://camel.apache.org/)
- [Camel Netty HTTP Component](https://camel.apache.org/components/latest/netty-http-component.html)
- [OAuth 2.0 Specification](https://oauth.net/2/)
- [OpenID Connect Specification](https://openid.net/connect/)
- [JWT Specification](https://jwt.io/)
- [Keycloak Documentation](https://www.keycloak.org/documentation)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This example is part of the Vized Studio examples collection and is provided under the Apache License 2.0.

---

**Note**: This example is for demonstration purposes. For production use, implement proper security measures, error handling, and monitoring as outlined in the security considerations section. 