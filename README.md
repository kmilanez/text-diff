Text Diff
===================
Tool to analyze differences between two base64 encoded payloads (json, xml, plain text, etc) of the same size

# About the architecture
The app was built using a distributed approach in a microservice architecture. Each service is responsible for a single capabilite and interfaces with each other when needed. I choose this approach to demonstrate my knowledge on the subject.

App is composed of the following services:

| Service  | Description |
| ------------- | ------------- |
| diff-service  |  Compare values and return their differences in pairs (offset,length) |
| decode-service | Value decoding (currently, only base64 implemented)  |
| cache-service  | In-memory cache, that can be enabled in standlone or distributed way  |
| gateway  |  Entry point to expose services to frontend systems and users |
| registry  | Service registry and discovery pattern |

App was designed with scalability in mind. Each service is stateless and can scale horizontaly. All the state is in cache, that works in replicated mode, which means it can scale horizontaly as well.

# About technologies used
The stack used is for the services is Spring with Spring Boot. Infrastructure is based on Spring Cloud, that integrates Netflix OSS stack to Spring ecosystem.

For more information about them, please refer to documentation:

[Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

[Spring Cloud Documentation](https://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html)

For caching, I choose Hazelcast. There is no special reason for that, I just wanted to test it in a microservice architecture. For more information about Hazelcast, refer to documentation:

[Hazelcast Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-hazelcast.html)

# Testing and Test coverage
I choose to use Spock as my testing framework in this project due its conciseness and easy integration with Spring.

For mocking, I'm using the good old Mockito.

Test coverage is around 80%, considering all the projects together. It includes unit and integration tests.

# How to run
The project provides a script for starting and stopping the app, in standlone approach or using Docker.

To start and stop the app in standalone mode:

```shell
> ./text-diff.sh start
```

```shell
> ./text-diff.sh stop
```

To start and stop using Docker

```shell
> ./text-diff.sh start docker
```

```shell
> ./text-diff.sh stop docker
```

In case you don't want to use the script, each project can be executed using gradle wrapper:

```shell
> ./diff-service/gradlew bootRun
```

Just make sure you start the whole app before running a transaction

Each project also provide a Dockerfile that you can build and executed using Docker Engine, with docker compose or manually running docker command. Just keep in mind that you will need to build the project to package the jar before building the image:

```shell
> ./diff-service/gradlew build
```

It compiles, run the tests and package the jar file.

The provided script have functions that automate these tasks. You can adapt them if necessary.

# Running a transaction

Once the app starts, you can run a transaction through the gateway, that is exposed on port 8080. Right now only provides the interface with diff service:

http://localhost:8080/v1/diff

Here's a sample transaction:

Save same value on left an right and assert it's a ARE_EQUAL response

```shell
> curl -d "eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0="  -H "Content-Type: application/json" -X POST http://localhost:8080/v1/diff/1/left
{"id":"1","value":"{\"message\":\"Hello World!\"}","status":"SAVED"}%
```

```shell
> curl -d "eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0="  -H "Content-Type: application/json" -X POST http://localhost:8080/v1/diff/1/right
{"id":"1","value":"{\"message\":\"Hello World!\"}","status":"SAVED"}%
```

```shell
> curl -H "Content-Type: application/json" -X GET http://localhost:8080/v1/diff/1
{"id":"1","valuePair":{"leftValue":"{\"message\":\"Hello World!\"}","rightValue":"{\"message\":\"Hello World!\"}"},"status":"ARE_EQUAL"}%
```

Now change value on the right:

```shell
> curl -d "eyJtZXNzYWdlIjoiSGVsbE8gd29ybGQhIn0"  -H "Content-Type: application/json" -X POST http://localhost:8080/v1/diff/1/right
{"id":"1","value":"{\"message\":\"HellO world!\"}","status":"SAVED"}%
```

And evaluate that response is HAVE_DIFFERENCES type:

```shell
> curl -H "Content-Type: application/json" -X GET http://localhost:8080/v1/diff/1
{"id":"1","valuePair":{"leftValue":"{\"message\":\"Hello World!\"}","rightValue":"{\"message\":\"HellO world!\"}"},"diffs":[{"offset":16,"length":1},{"offset":18,"length":1}],"status":"HAVE_DIFFERENCES"}%
```

# Bad things can happen

* You might receive a Hystrix read timeout from time to time. I have disabled timeout for the evaluation purposes, but if it still happens, just retry the transaction.
* If you receive a internal server error or load balance error after starting the app, give a couple seconds for services to synch with Eureka and try again.
