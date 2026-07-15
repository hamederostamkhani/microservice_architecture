# Microservices Architecture & Docker Containers


### Use case & Architecture

The sample application has four services namely getSellers, getProducts, addProductToSeller and getCorrelatedLogs. Each of the service created with multiple microservices. You can see the top level architecture of this demo in the image below.

![alt tag](https://github.com/gymofjava/microservice_architecture/blob/main/microservice_demo.png?raw=true)

You can see demo video:
[![Watch the video]](https://github.com/gymofjava/microservice_architecture/blob/main/demo_video.mp4)


### Components Integrated & Tools Usage   
##### Service registration and discovery

During the initialization of a service, it would get registered to the discovery and registration server (which in our demo is Hashicorp's Consul).
Configuration done in micro services to register to Consul:   
```
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
        instance-id: ${spring.application.name}:${random.value}
        health-check-interval: 10s
```
Consul management console can be accessed at http://localhost:8500/ui/ 
![alt tag](https://github.com/gymofjava/microservice_architecture/blob/main/consul.png?raw=true)

##### API Gateway
   
Spring Cloud Gateway is a the reverse proxy server which acts as the API Gateway for accessing the micro services behind the gateway which routes the request to the respective service. Microservice’s stay behind reverse proxy server and needs to be consumed via api gateway. The api-gateway micro service with docker profile runs on port 9090.

 
##### Monitoring and vizualization

Monitoring, visualisation & management of the logs done by splunk enterprise.   

splunk enterprise ui can be accessed at http://localhost:7071/   

![alt tag](https://github.com/gymofjava/microservice_architecture/blob/main/splunk.png?raw=true)

##### Asynchronous microservices communication  

Intercommunication between microservices happens asynchronously with the help of RabbitMQ.

RabbitMQ console can be accessed at http://localhost:15672/

### Technology

Microservices sample project uses a number of open source projects to work properly:

* [SpringBoot] - Application framework
* [SpringCloudGateway] - API Gateway 
* [Consul] - Service Registration and Discovery
* [Docker] - Containerization Platform
* [RabbitMQ] - Asynchronous microservices messaging
* [SplunkUniversalForwarder] - Log forwarder
* [SplunkEnterprise] - Log Management (time-series db)
* [Angular] - Web App

### Tools

* [Java] - Programming
* [Maven] - Build
* [Git] - Version control
* [Docker] - Deployment
