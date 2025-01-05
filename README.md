# Football Manager Application

## Running with Java

1. Create PostgreSQL database:

```
CREATE DATABASE football_manager;
```

2. Set up database credentials in application.properties:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/football_manager
spring.datasource.username=petro
spring.datasource.password=root
```

3. Run the application:

```
java -jar CodeSeekTestTask-1.0-SNAPSHOT.jar
```

## Running with Docker (Recommended)

1. Pull and run the Docker image:

```
docker pull ostapkh/football-manager:latest
docker run -p 8081:8081 ostapkh/football-manager:latest
```

