# Misy2

Microservice system - to demonstrate Spring Cloud stack. Can work locally or in Docker container.

## Architecture

### Services

- misy2-config-server - Spring Cloud Config, port 8888
- misy2-discovery-server - Netflix Eureka service registry, port 8761
- misy2-gateway-server - Spring Cloud Gateway, port 8080
- misy2-users-sevice - simple crud service over User entity, random port

### Build

Gradle.

###DB

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
- config-server:

`cd misy2-config-server`

`docker build -t misy2/misy2-config .`

- discovery-server:

`cd misy2-discovery-server`

`docker build -t misy2/misy2-discovery .`

- gateway-server:

`cd misy2-gateway-server`

`docker build -t misy2/misy2-gateway .`

- users-service:

`cd misy2-users-service`

`docker build -t misy2/misy2-users .`

## Run

### Local

At first, run misy2-config-server:

`cd misy2-config-server`

Linux: `./gradlew bootRun`

Windows: `gradlew.bat bootRun`

After then, run misy2-discovery-server:

`cd misy2-discovery-server`

Linux: `./gradlew bootRun`

Windows: `gradlew.bat bootRun`

Then others:

- gateway:

`cd misy2-gateway-server`

Linux: `./gradlew bootRun`

Windows: `gradlew.bat bootRun`

- users-service:

`cd misy2-users-service`

Linux: `./gradlew bootRun`

Windows: `gradlew.bat bootRun`

### Docker (Linux)

At first, run misy2-config-server:

`docker run --name config -p 8888:8888 --network misy2 -t misy2/misy2-config`

After then, run misy2-discovery-server:

`docker run --name discovery -p 8761:8761 --network misy2 -t misy2/misy2-discovery`

Then others:

- gateway:

`docker run --name gateway -p 8080:8080 --network misy2 -t misy2/misy2-gateway`

- users-service:

`docker run --name users[0, 1...n] --network misy2 -t misy2/misy2-users`

## Test

### misy2-config-server

Visit misy2-config-server\src\main\resources\configs\ to see configurations of all applications.

Visit http://localhost:8888/gateway-server/docker to see configuration for "docker" profile of "gateway-server" app.

Visit http://localhost:8888/gateway-server/dev to see configuration for "dev" profile of "gateway-server" app.

### misy2-discovery-server

Visit http://localhost:8761/ to see Eureka dashboard.

### misy2-gateway-server

Visit http://localhost:8080/get to see how Gateway redirect request to http://httpbin.org and add custom header.

Visit http://localhost:8080/users to see how Gateway redirect request to Users-service and get list of users, using
load balancing (different port for request) and discovery server (service name instead host name).

Visit http://localhost:8080/users without starting Users-service to see how Spring Cloud Circuit breaker in Gateway
(Resilience4J) redirect request to http://localhost:8080/get.

Visit http://localhost:8080/headers to see how how Gateway redirect request to Users-service, Spring Cloud Sleuth
adds headers (x-b3-traceid, x-b3-spanid, x-b3-parentspanid, x-b3-sampled). Look at users-service output
(console log) to see headers values :

`...c.s.m.u.controller.UserRestController    : header [x-b3-traceid]: 4d384d32cd2e7ec3`

Then Users-service makes request to http://httpbin.org - you see response for this request at
http://localhost:8080/headers. We have the same "x-b3-traceid" header and different "x-b3-parentspanid", "x-b3-spanid".

### misy2-users-service

Visit http://localhost:[randomPort]/api/users to see list of users.

To know an actual value of randomPort see output of users-service (console log) during startup of a sevice:

`...main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 64110 (http) with context path ''`

Visit http://localhost:[randomPort]/api/instances/users-service to see list of registered instances of "users-service"
 services. The misy2-users-service will take about a minute to register itself in the registry and to refresh its own
  list of registered instances from the registry.