# 📁 FILE MANAGER
### TECHNOLOGIES
* Spring Boot
* Javascript + Jquery AJAX + Bootstrap
* PostgreSQL 9.4

### BUILD AND DEPLOY
* Install database PostgreSQL 9.4
* Maven `mvn clean install`
* Open browser `http://localhost:8083/` (for changing port look at application.properties)

### API
* upload POST `http://localhost:8083/api/upload/multi` with form-data [fileName: text, dateDurationDescription: text; multipartFile: file]
* download GET `http://localhost:8083/api/download/{key}`
* delete file by key DELETE `http://localhost:8083/api/delete/{key}`
* delete all files DELETE `http://localhost:8083/api/delete/all`

> You can use Postman for testing or working with service by using API

### SOME MAGIC PROPERTY FOR DATABASE INITIALIZATION
1. How to skip SQL errors (for example if user or for command "insert values" value already exists)
`spring.datasource.continue-on-error=true`
2. How to initialize our database on startup
`spring.datasource.initialization-mode = always`

FROM openjdk:8
ADD target/file-manager.jar file-manager.jar
CMD ["java", "-jar", "file-manager.jar"]

version: '2'
services:
  db:
    image: postgres:9.4
    container_name: spring_app_data
    environment:
    - POSTGRES_PASSWORD=12345
    ports:
    - "5434:5432"
  file-manager:
    build: ..
    container_name: file-manager
    image: file-manager
    links:
    - db
    ports:
    - "8083:8083"
    environment:
    - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
    - SPRING_DATASOURCE_USERNAME=postgres
    - SPRING_DATASOURCE_PASSWORD=
    - SERVER_PORT=8083
    
    docker build -f Dockerfile -t spring-boot-with-docker .

docker run -p 8088:8088 spring-boot-with-docker
