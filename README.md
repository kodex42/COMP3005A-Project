# COMP3005A-Project
This is a Spring Boot project which requires the following software to run:
1. Intellij Community/Ultimate
2. Java 11 SDK
3. A PostgreSQL database with a schema to be created using the included SQL.sql file (the connection details of the database can be configured in the application.properties file)
4. A web browser

Before running this application, be sure to execute the SQL contained in the files DDL.sql, Triggers.sql, and Functions.sql located in the subdirectory SQL.

To run this project, simply download the source code as a zip file from this repository and open it with Intellij.
With the project open, it should start initializing the project with its given Maven configuration.
Once the project is loaded, start the included run configuration.

Upon startup, the project will populate the database with some publishers, books, and users.
The normal user has username and password "user". The admin user has username and password "admin".
If the application throws a PLSQLException on startup, please check your configuration of the application.properties file at src/main/resources/.

To access the application, open any web browser and visit the address localhost:8080.
With the web page open, you can log in with either the default "user" account or the default "admin" account.
