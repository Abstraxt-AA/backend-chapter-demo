# Backend Chapter Demo Project

This project serves as a training playground for the following concepts:

1) Unit and Integration Tests
2) MapStruct
3) Retrofit
4) OpenFeign
5) Swagger
6) CSV parsing, import/export
7) JPA Specifications
8) Spring Cloud Config Server & Client
9) Spring Kafka

The project is run after issuing `docker-compose` in the top directory (one image that needs to be built is the image
for the Spring Cloud Server, the properties file for that is provided along with the `docker-compose.yml` file in the
top directory). The `docker-compose.yml` file launches four containers:

1) A container for Apache Zookeeper
2) A container for Apache Kafka
3) A container for Gitlab EE edition (used as the git repository for Spring Cloud Config Server)
4) A container for Spring Cloud Config Server

Once all containers are up and running, the project can be run either by:

1) Using the maven wrapper to build a `.jar` file and run it.
2) Using an IDE such as Eclipse or Intellij.

As for the Spring Cloud Config Server image, the following docs can be followed to build it:

- [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)