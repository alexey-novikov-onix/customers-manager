### Customer Management Application

#### Main tech stack
Spring Boot 3, Java 21, H2

#### Swagger
Swagger UI is available at http://localhost:8080/swagger-ui/index.html, and the Swagger JSON is available at http://localhost:8080/v3/api-docs.
OpenAPI version is 3.0.1.

#### Health check
Health check is implemented with Spring Actuator and is available at http://localhost:8080/actuator/health

#### Building and Running the Project
This command cleans the target directories and compiles the project source code:
```shell
./mvnw clean compile
```
This command builds and runs your Spring Boot application:
```shell
./mvnw spring-boot:run
```
This command builds the project as a .jar file in the target directory:
```shell
./mvnw clean package
```
This command builds the project and installs the artifact into the local Maven repository:
```shell
./mvnw install
```
Important Notes:
- On Windows, use `mvnw.cmd` instead of `./mvnw` (use the `mvnw.cmd` command file).
- Maven Wrapper automatically downloads the required version of Maven during the first command execution, so the initial run may take longer.
