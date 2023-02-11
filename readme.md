# Misy2

Microservice system - to demonstrate Spring Cloud stack. Can work locally or in Docker containers.

## Architecture

### Services

- misy2-config-server - Spring Cloud Config, port 8888
- misy2-discovery-server - Netflix Eureka service registry, port 8761
- misy2-gateway-server - Spring Cloud Gateway, port 8080
- misy2-greeting-sevice - listener of kafka, random port
- misy2-users-sevice - simple crud service over User entity, random port
- kafka - Apache Kafka instance, port 9092 (port 29092 in container network) 
- zookeeper - Apache ZooKeeper instance for kafka

### Build

Gradle.

### DB

H2 in-memory.

### Prerequisites

- Java SDK 11
- Docker (if run in container)
- Docker network (if run in container): `docker network create misy2`

## Build

### Local

`cd misy2`

Linux: `./gradlew build`

Windows: `gradlew.bat build`

### Docker (Linux)

`cd misy2`

Build apps:

`./gradlew build`

Build Docker images:

`docker-compose build`

## Run

### Local

`cd misy2`

At first, run misy2-config-server:

Linux: `./gradlew :misy2-config-server:bootRun`

Windows: `gradlew.bat :misy2-config-server:bootRun`

After then, run misy2-discovery-server:

Linux: `./gradlew :misy2-discovery-server:bootRun`

Windows: `gradlew.bat  :misy2-discovery-server:bootRun`

Then others:

- gateway:

Linux: `./gradlew :misy2-gateway-server:bootRun`

Windows: `gradlew.bat :misy2-gateway-server:bootRun`

- users-service:

Linux: `./gradlew :misy2-users-service:bootRun`

Windows: `gradlew.bat :misy2-users-service:bootRun`

### Docker compose (Linux)

`cd misy2`

`sudo docker-compose up`

## Test

### misy2-config-server

Visit misy2-config-server\src\main\resources\configs\ to see configurations of all applications.

Visit http://localhost:8888/gateway-server/docker to see configuration for "docker" profile of "gateway-server" app.

Visit http://localhost:8888/gateway-server/default to see configuration for "default" profile of "gateway-server" app.

### misy2-discovery-server

Visit http://localhost:8761/ to see Eureka dashboard.

### misy2-gateway-server

Visit http://localhost:8085/get to see how Gateway redirect request to http://httpbin.org and add custom header.

Visit http://localhost:8085/users to see how Gateway redirect request to Users-service and get list of users, using
load balancing (different port for request) and discovery server (service name instead host name).

Perform POST request to http://localhost:8085/users to see how Gateway redirect request to Users-service and a new
message in the "users"-topic in kafka will be created:

`curl -X POST -H "Content-Type: application/json" -d '{"name":"Andy"}' http://localhost:8085/users`

Visit http://localhost:8085/users without starting Users-service to see how Spring Cloud Circuit breaker in Gateway
(Resilience4J) redirect request to http://localhost:8085/get.

Visit http://localhost:8085/headers to see how how Gateway redirect request to Users-service, Spring Cloud Sleuth
adds headers (x-b3-traceid, x-b3-spanid, x-b3-parentspanid, x-b3-sampled). Look at users-service output
(console log) to see headers values :

`...c.s.m.u.controller.UserRestController    : header [x-b3-traceid]: 4d384d32cd2e7ec3`

Then Users-service makes request to http://httpbin.org - you see response for this request at
http://localhost:8085/headers. We have the same "x-b3-traceid" header and different "x-b3-parentspanid", "x-b3-spanid".

### misy2-users-service

Visit http://localhost:[randomPort]/api/users to see list of users.

To know an actual value of randomPort see output of users-service (console log) during startup of a sevice:

`...main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 64110 (http) with context path ''`

Visit http://localhost:[randomPort]/api/instances/users-service to see list of registered instances of "users-service"
 services. The misy2-users-service will take about a minute to register itself in the registry and to refresh its own
  list of registered instances from the registry.

### misy2-greeting-service

This service is listening to the kafka's "users"-topic. And after receiving of a new message prints a message to the 
console:

`... Listener: User is created: User{id=6, name='Andy'}`

### kafka

misy2-users-service creates a new kafka topic with a name "users" after launch. You can see it by attaching to the
kafka container:

`docker exec -it kafka /bin/sh`

and running a command:

`kafka-topics --list --bootstrap-server localhost:9092`

To see all new messages in a "users"-topic interactively you should run consumer console:

`kafka-console-consumer --topic users --from-beginning --bootstrap-server localhost:9092`