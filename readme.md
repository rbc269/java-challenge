### How to run this project

- To start, simply run the script: ./start.sh ->  this will build, create an image and run the Docker
- To stop, simply run the script: ./stop.sh -> this will stop and remove the Docker container

To try, please access via: 
- Swagger UI : http://localhost:8080/swagger-ui.html

H2 database:
- H2 UI : http://localhost:8080/h2-console
- Note: Make sure JDBC URL is set to: 
  jdbc:h2:mem:testdb


### Changes made in this application
- Spring Boot application
- CRUD of Employee
- JUnit tests
- Caching for DB calls
- Dockerized this application

