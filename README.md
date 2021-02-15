# person-details
Created Person springboot application to Create,Update,Get,Delete the person data like firestName, lastName, age, favourite colour.

## Technologies used:

I have developed the application in Springboot with JPA.
Project: Maven Project
SpringBoot Version:2.4.2
Java version:8

### Dependencies added:

1.spring-boot-starter-data-jpa
2.spring-boot-starter-web
3.spring-boot-starter-test
4.spring-security-test
5.Junit test
6.spring-boot-starter-security
7.mockito-inline
8.springfox-swagger2
9.springfox-swagger-ui

#### DataBase Used:

in memory database H2.

##### plugins used

spring-boot-maven-plugin

###### Business logic

PersonController.class---> In this class i used Rest end points like @GetMapping,@PostMapping,@PutMapping,@DeleteMapping for retraving,updating,creating,deleting with some validation code

PersonServiceImpl ----> In this class i written main logic by invoking JPA Repository methods from Service interface methods to support controller class methods.

PersonService ----> In this i defined methods to be implemented in ServiceImpl class.

###### Exception Handling

PersonNotFoundException.class ----> In this class i declared customised exceptions to be used across the classes to handle or to throw the exception when person details not found

###### Security

PersonSecurityConfig.class--->In this class i have extended WebSecurityConfigurerAdapter class for authentication and authorization and login page for scalabilty or to impliment in future if required

###### Properties

application.properties -----> I have given basic properties like port,database connection,username,password and mainly Oauth2 security properties to be extended in future if required for advanced security.

###### Dockerizing the application:
Dockerfile---->given commands and instructions like FROM,EXPOSE,ADD,ENTRYPOINT to upload the application jar in container for creation of the image.


###### Test Cases Created:
PersonMokitoIntegrationTest.class --->For integration Testing using Mockito on controller class for RestAPI end points.
with this without effecting the actual database did the operations like POST,GET,PUT,DELETE with positive and negative scenarios.

PersonMokitoTest  ----> written Junit testcases on ServiceImpl class using testEntityManager,
for POST,GET,PUT,DELETE without effecting actual database with positive and negative scenarios

PersonTest ---> written Junit testcases on repository methods on POST,GET,PUT,DELETE with positive and negative scenarios.Without effecting the database.


