# eg-demo
Demo Repository for showing the usage of Java JMS service

# requirement:
Scenario:
A hospital management system has a hierarchical tree structure representing
different hospitals and groups of clinicians within those hospitals (see frontend mock-
up).
In this exercise, we want you to create a Java Message Service (JMS) to produce
and consume messages when groups and created or deleted.
Core Functionalities
1. Producer:
   Create a basic producer to send messages when a group/node is
   created or deleted.
    - Message structure should include:
        - Group ID
        - Parent Group ID
        - Operation (CREATE, DELETE)
        - Timestamp
        - Use in-memory messaging (no external broker needed), or
          RabbitMQ/ActiveMQ if you have time for setup.     

2. Consumer/Listeners:
   - Implement a simple listener per operation type (CREATE, DELETE)
   - Filter messages based on operation type (basic filtering: CREATE
   listeners listen only for CREATE messages, etc.).

3. Log:
   - Create a basic log to track the messages

# implementation details
- Technology used
    - [Java 21](https://docs.oracle.com/en/java/javase/21/)
    - [Spring boot framework](https://docs.spring.io/spring-boot/reference/index.html)
    - [Apache Active MQ for messaging service](https://activemq.apache.org/components/classic/documentation/)
    - [Gradle as build tool](https://docs.gradle.org)
    - [Docker for deployment](https://docs.spring.io/spring-boot/3.4.2/reference/features/dev-services.html#features.dev-services.docker-compose)
    - [Prometheus for monitoring](https://docs.spring.io/spring-boot/3.4.2/reference/actuator/metrics.html#actuator.metrics.export.prometheus)

# System Requirement to run the application
- Docker 
- Java JDK 21

# Commands to run the application
All the below commands should be run in terminal from the project root location
- command to build the application
    - ./gradlew clean build
- command to bring the application up
    - docker-compose up --build
- command to bring the application down
    - docker-compose down
- active Mq admin console
    - http://localhost:8161/admin/queues.jsp
- command to connect the database
    - mysql -h 127.0.0.1 -P 3306 -u user -p
    - check for the password in application properties file
- Swagger documentation for API
    - http://localhost:8080/swagger-ui/index.html#
    - Api to send the message: http://localhost:8080/swagger-ui/index.html#/jms-controller/sendMessage
- Application monitoring using Grafana
    - http://localhost:9090/