# GatewayKit

A lightweight, config-driven API gateway built for the Podium Software Engineer take-home assessment.

## Implemented Features

* Starts on port 8080
* GET /health endpoint
* YAML configuration loading
* Route matching
* Method filtering (405 Method Not Allowed)
* 404 responses for unknown routes
* Basic request proxying to upstream services
* Mock upstream server for testing

## Running the Gateway

Windows:

```bash
mvnw.cmd spring-boot:run
```

## Running the Mock Upstream

```bash
node mock-upstream.js
```

## Example Requests

Health Check:

```text
GET http://localhost:8080/health
```

Proxy Request:

```text
GET http://localhost:8080/api/users
```

Unknown Route:

```text
GET http://localhost:8080/api/unknown
```

Method Not Allowed:

```text
POST http://localhost:8080/api/users
```

## Architecture

* ConfigLoader loads gateway.yaml
* RouteMatcher finds matching routes
* GatewayController handles requests and proxying
* HealthController provides health status

## Future Improvements

* Request body forwarding
* Header forwarding
* Route-specific timeouts
* Rate limiting
* Retry logic
* Circuit breaker support
* Load balancing
* Integration tests
- Better upstream failure handling with 502/503 responses
