# SEF Core

This repository contains two modules
1. Backend 
    Contains the following APIs,
    1. Multiverse Programme
    2. Fellowship Programme
    3. Academix Project
    
2. Frontend
    Contains the admin dashboard
    
    
## Setup Development Environment

### Prerequisites
1. Tomcat 9
2. MySQL 8
3. Maven

### Configure Database
1. Create an empty MySQL database
2. Create a copy of application.properties.example file on the backend module and add your credentials.
   ```
    cp backend/src/main/resources/application.properties.example backend/src/main/resources/application.properties
    nano backend/src/main/resources/application.properties
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
   cp backend/target/core.war /path/to/tomcat/webapps
   cp frontend/target/dashboard.war /path/to/tomcat/webapps
   ```
3. You can access the UI with http://localhost:8080/dashboard
4. **Default credentials** are, 
    - username : admin
    - password : admin


### Frontend development

#### Creating Symlinks (important)

1. [Build the project](#Building the project) and deploy it to the tomcat. 
2. Navigate to the frondend app's dist directory
   ```
   cd frontend/app/dist
   ``` 
3. Remove the contents of the dist directory and create the symlinks as follows
   ```
   rm -rf *
   ln -s /path/to/tomcat/webapps/dashboard/index.html
   ln -s /path/to/tomcat/webapps/dashboard/main.js
   ln -s /path/to/tomcat/webapps/dashboard/main.js.map
   ln -s /path/to/tomcat/webapps/dashboard/main.css
   ln -s /path/to/tomcat/webapps/dashboard/main.css.map
   ```

#### Rebuild using npm
This will rebuild the app and update the deployed application on the tomcat because it has been linked with symlinks.
```
cd frontend/app
npm run build
```
(You should create the symlinks first)

#### Auto update while developing application
Run the watch command,
```
    cd frontend/app
    npm run watch
```
This will watch files and recompile whenever they change.

(You should create the symlinks first)
