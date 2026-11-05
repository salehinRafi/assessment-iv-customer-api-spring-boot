# Customer API

Application built with Java 21, Spring Boot 4.1.2, and SQL Server.
Creates a customer with an account and card, updates contact details,
lists customers, and fetches a third-party post.

## Requirements

- Java 21
- Docker Desktop with Docker Compose
- Maven, or the included Maven wrapper
- Postman (optional)

## Run Locally

Run these commands from the project folder in PowerShell.

1. Start SQL Server:

   ```powershell
   docker compose up -d sqlserver
   ```

2. Wait for SQL Server to be ready. On a fresh installation, connect using a
   SQL client and create the database once:

   ```sql
   IF DB_ID('TESTDB') IS NULL
       CREATE DATABASE TESTDB;
   ```

   Connection: `localhost:1433`, user: `sa`. The local-only default password
   is in `docker-compose.yml`. You can override it with the `DB_PASSWORD`
   environment variable before starting Docker and the application. For an
   existing database volume, use its existing password.

3. Start the application:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   If Maven is already installed, you can use `mvn spring-boot:run` instead.

4. Check application health:

   ```text
   GET http://localhost:8080/actuator/health
   ```

The default `dev` profile listens on `127.0.0.1:8080`. Liquibase creates the
tables and inserts sample data in the existing `TESTDB` database on startup.
Hibernate validates the schema; it does not create the database.

## APIs

Base URL: `http://localhost:8080`

For POST and PATCH requests, set `Content-Type: application/json`.
All `/api/**` requests require this header:

```http
Authorization: Bearer test-token
```

The token is configured in `src/main/resources/application.properties`:

```properties
app.security.api-token=test-token
```

Change this value and restart the application to use a different token.
Update the Postman `apiToken` collection variable to match.
The token is for local training only. It grants the same access to
every caller; it is not a JWT and does not expire. Missing or incorrect tokens
return `401 Unauthorized`. `/actuator/health` remains accessible without a token.
Use HTTPS and real authentication before deploying outside local development.

| Method | Path | Purpose | Success |
| --- | --- | --- | --- |
| POST | `/api/customers` | Create customer, account, and card | 201 |
| PATCH | `/api/customers/{id}/contact` | Update customer contact details | 200 |
| GET | `/api/customers` | List customers with accounts and cards | 200 |
| GET | `/api/external/posts/{id}` | Fetch a post from JSONPlaceholder | 200 |

### Create Customer

```text
POST /api/customers
```

```json
{
  "name": "API Customer",
  "email": "customer@example.com",
  "phone": "0100000001",
  "address": "1 Example Street",
  "country": "MY",
  "identityType": "NRIC",
  "identityNumber": "000000482719",
  "accountType": "SAVINGS",
  "cardType": "DEBIT"
}
```

All fields are required. `identityType` accepts `NRIC` or `PASSPORT`;
`cardType` accepts `DEBIT` or `CREDIT`. Country must be two uppercase letters.

The response contains the customer with nested `accounts` and `cards`.
The new account starts with a zero balance, and the card starts `INACTIVE`.
CVV is not returned. All three records are created in one transaction.
Save the customer `id` for contact update requests.

### Update Contact

```text
PATCH /api/customers/1/contact
```

```json
{
  "phone": "0199999999",
  "address": "2 Example Street"
}
```

Provide at least one of `email`, `phone`, or `address`. Omitted or null fields
remain unchanged. Returns the updated customer with accounts and cards.

### List Customers

```text
GET /api/customers?page=0&size=10&sort=id,asc
```

Pages are zero-based. Defaults: page `0`, size `10`, sorted by `id` ascending.
You can change the size and sort, for example `?page=0&size=5&sort=name,desc`.

The response has a `content` array and a `page` object containing `size`,
`number`, `totalElements`, and `totalPages`.

### Fetch External Post

```text
GET /api/external/posts/1
```

Calls `https://jsonplaceholder.typicode.com/posts/1` and returns `userId`,
`id`, `title`, and `body`. Internet access is required for this endpoint.
The HTTP client has a 3-second connection timeout and a 5-second read timeout.

## Error Responses

The application provides custom `ProblemDetail` responses for:

- `404 Not Found`: customer or external post not found.
- `502 Bad Gateway`: external service failure or empty external response.

Authentication failures return `401` with a `WWW-Authenticate: Bearer` header.
They are handled by Spring Security, not the custom exception handler.

Other errors use Spring Boot's default handling. Invalid request bodies
normally return `400`; database conflicts and invalid sorting do not have
custom handlers.

## Postman

1. Import `postman/customer-api.postman_collection.json`.
2. Keep `baseUrl` as `http://localhost:8080`.
3. In the collection's **Variables** tab, set `apiToken` to the value of
   `app.security.api-token` in `application.properties`. The current demo value
   is `test-token`. Enter only the token, without the `Bearer` prefix.
4. The collection's **Authorization** tab uses **Bearer Token** with
   `{{apiToken}}`. Leave each request set to **Inherit auth from parent**;
   Postman adds the `Authorization` header automatically.
5. Run **Create customer with account and card**.
6. Copy the customer `id` from the response into the `customerId` collection variable.
7. Run the contact update, customer listing, and external post requests.

If you already imported an older collection, re-import the updated file.
If you change the application token, restart the application and update
`apiToken` in Postman. The token is validated locally; no login endpoint or
external authorization server is needed.

The collection has four requests and no test scripts. Its creation pre-request
script generates sample email and identity values automatically.

## Logs

HTTP logs are written to `logs/customer-api.log`. They include the request
method, path, response status, and duration. Request/response bodies, headers,
and query strings are not logged.
