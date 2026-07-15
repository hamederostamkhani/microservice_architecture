# Microservices Architecture with Docker

A sample microservices project demonstrating service discovery, API gateway routing, asynchronous communication, centralized logging, and containerized deployment using Docker.

---

## Architecture Overview

This project consists of four business services:

* **getSellers**
* **getProducts**
* **addProductToSeller**
* **getCorrelatedLogs**

Each service is implemented as one or more Spring Boot microservices and runs independently inside Docker containers.

The overall architecture is shown below:

![Microservices Architecture](https://github.com/gymofjava/microservice_architecture/blob/main/microservice_demo.png?raw=true)

## Demo

Watch the project demo:

[Demo Video](https://github.com/gymofjava/microservice_architecture/blob/main/demo_video.mp4)

---

# Components

## Service Discovery (Consul)

Each microservice automatically registers itself with **HashiCorp Consul** when it starts.

Example configuration:

```yaml
management:
  contextPath: /actuator

spring:
  application:
    name: gateway

  cloud:
    consul:
      host: consul_server
      port: 8500
      discovery:
        hostname: gateway
        health-check-path: /actuator/health
        health-check-interval: 10s
        instance-id: ${spring.application.name}:${random.value}
```

Consul UI:

```
http://localhost:8500/ui/
```

![Consul](https://github.com/gymofjava/microservice_architecture/blob/main/consul.png?raw=true)

---

## API Gateway

The application uses **Spring Cloud Gateway** as the single entry point for all client requests.

Responsibilities include:

* Request routing
* Reverse proxy
* Service abstraction
* Load balancing through service discovery

The Gateway service runs on:

```
http://localhost:9090
```

---

## Centralized Logging

Application logs are collected and visualized using **Splunk Enterprise**.

Logs are forwarded by **Splunk Universal Forwarder**, allowing centralized monitoring and correlation across all services.

Splunk UI:

```
http://localhost:7071
```

![Splunk Dashboard](https://github.com/gymofjava/microservice_architecture/blob/main/splunk.png?raw=true)

---

## Asynchronous Communication

Microservices communicate asynchronously using **RabbitMQ**.

RabbitMQ provides:

* Event-driven communication
* Decoupled services
* Reliable message delivery

RabbitMQ Management Console:

```
http://localhost:15672
```

---

# Technologies

| Technology                 | Purpose                            |
| -------------------------- | ---------------------------------- |
| Spring Boot                | Microservice framework             |
| Spring Cloud Gateway       | API Gateway                        |
| HashiCorp Consul           | Service discovery and registration |
| RabbitMQ                   | Asynchronous messaging             |
| Docker                     | Containerization                   |
| Splunk Enterprise          | Log management and visualization   |
| Splunk Universal Forwarder | Log forwarding                     |
| Angular                    | Frontend application               |

---

# Development Tools

| Tool   | Purpose                                |
| ------ | -------------------------------------- |
| Java   | Programming language                   |
| Maven  | Build automation                       |
| Git    | Version control                        |
| Docker | Deployment and container orchestration |

---

# Project Highlights

* Docker-based microservices architecture
* API Gateway with Spring Cloud Gateway
* Service discovery using Consul
* Event-driven communication with RabbitMQ
* Centralized logging using Splunk
* Independent deployment of services
* Health monitoring with Spring Boot Actuator

---

# Access URLs

| Component   | URL                       |
| ----------- | ------------------------- |
| API Gateway | http://localhost:9090     |
| Consul      | http://localhost:8500/ui/ |
| RabbitMQ    | http://localhost:15672    |
| Splunk      | http://localhost:7071     |

---

# Future Improvements

* Add Docker Compose setup instructions
* Include Kubernetes deployment manifests
* Add authentication (JWT / OAuth2)
* Add distributed tracing (Zipkin or Jaeger)
* Add Prometheus and Grafana monitoring
* Include API documentation with Swagger/OpenAPI
