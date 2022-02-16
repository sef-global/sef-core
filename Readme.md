# SEF Core

This repository contains the following APIs,
    1. Multiverse Programme
    2. Fellowship Programme
    3. Academix Project
## Setup Development Environment

### Prerequisites
1. Tomcat 9
2. MySQL 8
3. Maven

### Configure Database
1. Create an empty MySQL database
2. Create a copy of application.properties.example file on the backend module and add your credentials.
   ```
    cp src/main/resources/application.properties.example src/main/resources/application.properties
    nano src/main/resources/application.properties
   ```
   Replace ${DB_NAME} ${DB_USERNAME} and ${DB_PASSWORD}. 
   
   ex : 
   
   replace `spring.datasource.username = ${DB_USERNAME}` with
   `spring.datasource.username = root`

### Building the project

1. Build the project using maven
   ```
   mvn install
   ```
    Optional : You can build the modules separately
    
2. Copy the generated `.war` file to the tomcat's webapp directory.  
   ```
   cp target/core.war /path/to/tomcat/webapps
   ```