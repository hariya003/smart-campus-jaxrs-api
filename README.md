# Smart Campus API

**Module:** 5COSC022W – Client-Server Architectures  
**Coursework:** Smart Campus Sensor & Room Management API  
**Student Name:** Harinitha Wasathhara  
**Student ID:** w2120423  
**IIT ID:** 20232501 
**Email:** w2120423@westminster.ac.uk  
**GitHub Repository:** https://github.com/hariya003/smart-campus-jaxrs-api

---

## Declaration

I hereby declare that this coursework report and the Smart Campus API project submitted for the Client-Server Architectures module are entirely my own work. All implementation testing and written answers included in this submission were completed by me according to the coursework specification. I confirm that this work has not been submitted before for any other assessment module or qualification.

---

## Abstract

This report describes the design and implementation of the Smart Campus API which is a RESTful web service developed using JAX-RS for the Client-Server Architectures coursework. The system is designed to manage three main resources: Rooms Sensors and Sensor Readings. It provides room creation room retrieval room deletion with safety checks sensor registration filtering sensors by type and a historical log of readings for each sensor.

The API applies important RESTful principles such as resource-oriented design stateless communication query-based filtering and nested sub-resources. It also includes structured JSON error handling through custom exception mapping and request and response logging through JAX-RS filters. The project uses in-memory Java collections such as HashMap and ArrayList instead of a database in line with the coursework requirements.

Overall the Smart Campus API demonstrates both the practical construction of a RESTful service in Java and the conceptual understanding of JAX-RS resource organisation error handling and API observability required by the module.

---

## Keywords

Smart Campus API, RESTful API, JAX-RS, Jersey, Client-Server Architecture, Rooms, Sensors, Sensor Readings, Exception Mapping, Logging and Resource-Oriented Design

---

## Acknowledgment

I would like to express my sincere thanks to the module leader and lecturers of Client-Server Architectures for their support and guidance throughout this coursework. The lectures tutorials and coursework brief helped me develop a clearer understanding of RESTful API design JAX-RS development exception mapping filtering and sub-resource handling. This coursework gave me a valuable chance to apply the concepts learned in class to a practical Smart Campus API implementation.

---

## 1. API Overview

This project presents a RESTful Smart Campus API built using **JAX-RS**. The API manages three main resources:

- **Room**
- **Sensor**
- **SensorReading**

The system supports room creation room retrieval room deletion with safety validation sensor registration filtering sensors by type nested sensor readings custom exception mapping and request and response logging.

The project uses only **in-memory data structures** such as `HashMap` and `ArrayList` and does **not** use any database technology. For that reason all stored data is cleared whenever the server is restarted or redeployed.

---

## 2. Technology Stack

- Java
- Maven
- JAX-RS (Jersey)
- Apache Tomcat
- NetBeans
- Postman

---

## 3. How to Build and Run the Project

### Run in NetBeans
1. Open the project in NetBeans.
2. Check that Apache Tomcat is configured in NetBeans.
3. Right-click the project and select **Clean and Build**.
4. Right-click the project and select **Run**.
5. Access the API using a browser or Postman.

### Build with Maven
From the project folder run:

```bash
mvn clean install
````

Then deploy the generated WAR file to Tomcat.

### Base URL

```text
http://localhost:8080/SmartCampusAPI/api/v1
```

The coursework requires the API entry point to use `@ApplicationPath("/api/v1")`.

---

## 4. API Endpoints

### Discovery

* `GET /api/v1`

### Rooms

* `GET /api/v1/rooms`
* `POST /api/v1/rooms`
* `GET /api/v1/rooms/{roomId}`
* `DELETE /api/v1/rooms/{roomId}`

### Sensors

* `GET /api/v1/sensors`
* `GET /api/v1/sensors?type=Temperature`
* `GET /api/v1/sensors/{sensorId}`
* `POST /api/v1/sensors`

### Sensor Readings

* `GET /api/v1/sensors/{sensorId}/readings`
* `POST /api/v1/sensors/{sensorId}/readings`

### Debug

* `GET /api/v1/debug/error`

These endpoints follow the coursework requirements for discovery room management sensor operations filtered retrieval and nested reading resources.

---

## 5. Sample curl Commands

### 1. Discovery endpoint

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1"
```

### 2. Create a room

```bash
curl -X POST "http://localhost:8080/SmartCampusAPI/api/v1/rooms" \
-H "Content-Type: application/json" \
-d '{
  "id": "LIB-301",
  "name": "Library Quiet Study",
  "capacity": 40
}'
```

### 3. Get all rooms

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/rooms"
```

### 4. Get a room by ID

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/rooms/LIB-301"
```

### 5. Create a sensor

```bash
curl -X POST "http://localhost:8080/SmartCampusAPI/api/v1/sensors" \
-H "Content-Type: application/json" \
-d '{
  "id": "TEMP-001",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 0,
  "roomId": "LIB-301"
}'
```

### 6. Get all sensors

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/sensors"
```

### 7. Filter sensors by type

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=Temperature"
```

### 8. Add a sensor reading

```bash
curl -X POST "http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings" \
-H "Content-Type: application/json" \
-d '{
  "timestamp": 1713270000000,
  "value": 22.5
}'
```

### 9. Get sensor readings

```bash
curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings"
```

### 10. Error test: invalid room reference

```bash
curl -X POST "http://localhost:8080/SmartCampusAPI/api/v1/sensors" \
-H "Content-Type: application/json" \
-d '{
  "id": "CO2-001",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 0,
  "roomId": "BAD-ROOM"
}'
```

---

## 6. Project Structure

```text
src/main/java/com/harinitha/smartcampusapi
│
├── JAXRSConfiguration.java
│
├── model
│   ├── Room.java
│   ├── Sensor.java
│   ├── SensorReading.java
│   └── ErrorMessage.java
│
├── store
│   └── DataStore.java
│
├── resources
│   ├── DiscoveryResource.java
│   ├── RoomResource.java
│   ├── SensorResource.java
│   └── SensorReadingResource.java
│
├── exception
│   ├── RoomNotEmptyException.java
│   ├── LinkedResourceNotFoundException.java
│   ├── SensorUnavailableException.java
│   ├── RoomNotEmptyExceptionMapper.java
│   ├── LinkedResourceNotFoundExceptionMapper.java
│   ├── SensorUnavailableExceptionMapper.java
│   └── GenericExceptionMapper.java
│
└── filter
    └── ApiLoggingFilter.java
```

---

## 7. Conceptual Report Answers

### Part 1.1 – Default Lifecycle of a JAX-RS Resource Class

By default a JAX-RS resource class normally follows a request-scoped lifecycle which means a separate instance is created for each incoming HTTP request. This is useful because request-specific state is not shared between clients. However in this coursework the actual application data is kept in shared in-memory collections such as `HashMap` and `ArrayList`. Because those collections are shared across requests they must be handled carefully to avoid race conditions inconsistent updates and accidental data loss when multiple requests access them at the same time.

### Part 1.2 – Why Hypermedia / HATEOAS is Useful

Hypermedia is useful because it enables clients to discover the API through links returned in responses instead of depending only on fixed external documentation. In this project the discovery endpoint returns links to important collections such as `/api/v1/rooms` and `/api/v1/sensors`. This helps client developers navigate the API dynamically reduce hard-coded assumptions and adapt more easily if the API is extended later.

### Part 2.1 – Returning IDs Only vs Full Room Objects

Returning only room IDs reduces the response size and saves bandwidth which can be helpful when a system contains many rooms. However it places more work on the client because additional requests are needed to fetch complete details. Returning full room objects increases the size of the payload but it is more convenient because the client receives the useful data immediately. In this implementation returning full room objects was more practical because it simplified testing and reduced extra client-side processing.

### Part 2.2 – Is DELETE Idempotent?

Yes DELETE is idempotent because repeating the same DELETE request does not create extra side effects after the first successful removal. In this implementation the first DELETE removes the room if the business rules permit it. If the same request is sent again later the room is already absent so the server may return “Room not found” but the final state of the system still remains unchanged after the first deletion.

### Part 3.1 – Effect of `@Consumes(MediaType.APPLICATION_JSON)`

The `@Consumes(MediaType.APPLICATION_JSON)` annotation indicates that the method is intended to accept only JSON request bodies. If a client sends another content type such as `text/plain` or `application/xml` the JAX-RS runtime will not match that request correctly to the method and should reject it as an unsupported media type. This makes the API safer and more precise because it accepts only the format the method was designed to process.

### Part 3.2 – Why `@QueryParam` Is Better for Filtering

Using `@QueryParam` such as `/sensors?type=CO2` is more suitable for filtering because the client is still requesting the same collection resource with an optional condition applied. A path such as `/sensors/type/CO2` makes the filter appear like a different resource. Query parameters are therefore more flexible and more RESTful for searching and filtering collection results.

### Part 4.1 – Benefits of the Sub-Resource Locator Pattern

The Sub Resource Locator pattern improves structure and maintainability by passing nested logic to a separate class. Instead of putting all sensor and reading operations into one large resource class `SensorResource` delegates reading-related work to `SensorReadingResource`. This keeps responsibilities separate makes the code easier to understand reduces clutter and makes future extension simpler.

### Part 5.2 – Why 422 Is More Accurate Than 404

`422 Unprocessable Entity` is more accurate than `404 Not Found` because the endpoint `/sensors` does exist and the JSON body itself may be syntactically valid. The problem is semantic because the `roomId` inside the payload refers to a room that does not exist. A `404` usually describes a missing target resource in the request URL whereas `422` better represents a valid request body that cannot be processed correctly because of invalid linked data.

### Part 5.4 – Why Internal Stack Traces Should Not Be Exposed

Internal Java stack traces should not be exposed because they can reveal sensitive implementation details such as package names class names method names library versions file paths and internal control flow. An attacker could use this information to understand the internal structure of the system identify weak points and target known vulnerabilities in specific frameworks or libraries. Returning a generic `500 Internal Server Error` response is therefore much safer.

### Part 5.5 – Why Filters Are Better for Logging

Filters are better for logging because logging is a cross-cutting concern that applies to many endpoints. By using JAX-RS filters the request and response logging logic is kept in one central place instead of being repeated inside every resource method. This reduces duplication keeps resource classes cleaner and makes the logging behaviour more consistent and easier to maintain.

---

## 8. Exception Handling Implemented

This API uses custom exception classes and exception mappers for the required error scenarios:

* `RoomNotEmptyException` → `409 Conflict`
* `LinkedResourceNotFoundException` → `422 Unprocessable Entity`
* `SensorUnavailableException` → `403 Forbidden`
* `GenericExceptionMapper` → `500 Internal Server Error`

This ensures the API is leak-proof and does not expose raw Java stack traces or default Tomcat error pages to clients.

---

## 9. Logging Implemented

The project includes `ApiLoggingFilter` which implements:

* `ContainerRequestFilter`
* `ContainerResponseFilter`

It records:

* incoming HTTP method and URI
* outgoing HTTP response status code

This improves observability and supports debugging and monitoring.

---

## 10. Important Notes

* This project uses **JAX-RS only**.
* No Spring Boot or other framework has been used.
* No database has been used.
* Data is stored using in-memory collections only.
* Restarting the server clears the stored data.

---

## 11. Video Demonstration

The API was tested using Postman. The demonstration includes:

* discovery endpoint
* room creation and lookup
* sensor creation and filtering
* nested sensor readings
* error handling scenarios
* logging output in NetBeans
