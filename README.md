 Weather Services API
=========================================

## Reading guide
File names are relative to this repository's root unless otherwise stated

### Development Stack
- Java 8
- Spring Boot 2.1.6.RELEASE
- Gradle 5.2.1 (gradlew can be used )
- Swagger
- Spock
- Mocha and SuperTest for api-tests

### Set up guide for  Mac
- install homebrew
- install openjdk java8 
    - brew tap adoptopenjdk/openjdk 
    - brew cask install adoptopenjdk8)
- install npm 
    - brew install npm


### Repository  Layout  
```
.
├── README.md
├── api-tests
│   ├── node_modules //Node modules required for api-test
│   ├── package-lock.json
│   ├── package.json 
│   └── test 
         ├──....//api-test js files
├── build
│   ├──.... //Build folder
├── build.gradle //Build file
├── docs
|    └── swagger.yaml //Swagger documentation
├── gradle
│   └── wrapper
├── gradlew
├── gradlew.bat
├── postman-collection  //PostMan collection for the API's
│   └── Accounts&Transactions.postman_collection.json
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── ..//Application source code
    │   └── resources
    │       ├── application.properties 
    │       └── data.sql //SQL to load into h2database
    └── test
        ├── groovy
        │   └── .. //Spring spock test files
        └── resources


```

### Running application locally 
Before running the application ensure that the setup required for mac is completed
   -  git clone https://github.com/raghunadhpokkalath/anz-wd-account-svc.git
   -  cd anz-wd-account-svc
   - run "./gradlew clean build"  (This will build and run the test .Jacoco plugin is integrated to ensure code coverage is 100%)
   - run "./gradlew bootRun"  ("This will start the application in 8080 port")
   - access the api using below endpoint or import the postman collection from "postman-collection/Accounts&Transactions.postman_collection.json"
   - Accounts API  - http://localhost:8080/api/customers/10000000/accounts
   - Transactions API - http://localhost:8080/api/accounts/5678678692/transactions
   - Swagger-ui  - http://localhost:8080/api/swagger-ui.html

### Running API-Tests
 Before running api-test ensure that application is running at 8080 port
 - cd api-tests 
 - "Before running npm ensure that npm is installed on your local machine
 -  run 'npm test'


 
### H2 DB console
H2 in-memory database is used to store the account and transaction details. The H2 console can be accessed using below link 

http://localhost:8080/api/h2-console

Before clicking connect button .Please make sure JDBC URL is configured as "jdbc:h2:mem:accountdb"

click connect without giving a password for user 'sa' 

Data for H2 database is loaded from 'src/main/resources/data.sql' 
