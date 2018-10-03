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
