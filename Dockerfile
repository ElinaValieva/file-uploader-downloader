FROM openjdk:8
ADD target/file-manager.jar file-manager.jar
CMD ["java", "-jar", "file-manager.jar"]