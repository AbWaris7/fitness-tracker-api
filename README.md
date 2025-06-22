# 🏃‍♂️ Fitness Tracker API

A Spring Boot REST API for collecting and retrieving fitness tracker data from client applications. Includes developer and app registration, API key-based authentication, and rate limiting.

---

## 📚 Overview

The Fitness Tracker API supports:

- Uploading and retrieving fitness data.
- Developer sign-up and profile access.
- Registering applications to get API keys.
- API key authentication for secure access.
- Rate limiting for basic-tier applications.

---

## ⚙️ Setup & Configuration

### 🔧 Prerequisites

- Java 17+
- Gradle
- Spring Boot

### 💾 Database Configuration (H2 on disk)

Add the following lines to `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:../fitness_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false
```

---

## 🔐 Security Notes

- `/api/developers` and `/api/applications` use **Basic Auth**.
- `/api/tracker` endpoints require **X-API-Key** header.
- **Stateless** security (no sessions).
- CSRF and frame protection are **disabled** for development tools like Postman and H2 console.

```java
.csrf(csrf -> csrf.disable())
.headers(headers -> headers.frameOptions().disable())
.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```

---

## 🧑‍💻 Developer API

### `POST /api/developers/signup`

Registers a new developer.

**Request:**
```json
{
  "email": "johndoe@gmail.com",
  "password": "qwerty"
}
```

**Responses:**

- `201 CREATED`
- `Location: /api/developers/{id}`  
- `400 BAD REQUEST` for invalid/duplicate emails

---

### `GET /api/developers/{id}`

Returns developer profile. Requires matching credentials via Basic Auth.

**Response:**
```json
{
  "id": 9062,
  "email": "johndoe@gmail.com",
  "applications": [
    {
      "id": 4624,
      "name": "Fitness App",
      "description": "demo application",
      "apikey": "21da3cc802...",
      "category": "basic"
    }
  ]
}
```

**Access Control:**
- `200 OK` – valid auth & ID match
- `401 UNAUTHORIZED` – no auth
- `403 FORBIDDEN` – ID mismatch

---

## 📱 Application API

### `POST /api/applications/register`

Registers a new app under an authenticated developer.

**Auth:** Basic Auth  
**Request:**
```json
{
  "name": "Fitness App",
  "description": "demo application",
  "category": "basic"  // or "premium"
}
```

**Response:**
```json
{
  "name": "Fitness App",
  "apikey": "21da3cc802...",
  "category": "basic"
}
```

**Errors:**
- `400 BAD REQUEST` for duplicate/invalid fields
- `401 UNAUTHORIZED` for missing credentials

---

## 📊 Tracker API

> Requires API Key in the header: `X-API-Key`

---

### `POST /api/tracker`

Uploads fitness data.

**Headers:**
```
X-API-Key: your-api-key
```

**Request:**
```json
{
  "username": "user-12",
  "activity": "swimming",
  "duration": 950,
  "calories": 320
}
```

**Response:** `201 CREATED`

---

### `GET /api/tracker`

Retrieves all tracker data (newest first).

**Headers:**
```
X-API-Key: your-api-key
```

**Response:**
```json
[
  {
    "id": 2,
    "username": "user-12",
    "activity": "hiking",
    "duration": 4800,
    "calories": 740,
    "application": "Fitness App"
  },
  {
    "id": 1,
    "username": "user-12",
    "activity": "swimming",
    "duration": 950,
    "calories": 320,
    "application": "Fitness App"
  }
]
```

---

## 🚦 Rate Limiting

**Basic** apps: limited to **1 request/second**  
**Premium** apps: **no limit**

If limit exceeded, response is:
```http
429 TOO MANY REQUESTS
```

Applies globally to:
- `GET /api/tracker`
- `POST /api/tracker`

---

## 🧪 API Usage Examples (cURL)

### Register Developer

```bash
curl -X POST http://localhost:8080/api/developers/signup   -H "Content-Type: application/json"   -d '{"email":"johndoe@gmail.com","password":"qwerty"}'
```

### Register Application

```bash
curl -X POST http://localhost:8080/api/applications/register   -u johndoe@gmail.com:qwerty   -H "Content-Type: application/json"   -d '{"name":"Fitness App","description":"demo application","category":"basic"}'
```

### Upload Fitness Data

```bash
curl -X POST http://localhost:8080/api/tracker   -H "X-API-Key: your-api-key"   -H "Content-Type: application/json"   -d '{"username":"user-12","activity":"running","duration":3600,"calories":450}'
```

### Get Fitness Data

```bash
curl -X GET http://localhost:8080/api/tracker   -H "X-API-Key: your-api-key"
```

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security
- H2 Database (file-based)
- JPA & Hibernate
- Gradle

---

## 📄 License

This project is licensed under the MIT License.

---

## 🙋 Support

Please open an issue for questions, bugs, or feature requests.
