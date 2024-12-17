
The system is implemented using the Java Spring Boot MVC framework and connects to a PostgreSQL database. In this architecture, the Model layer is used to create database tables and provide getter and setter methods. The Controller layer is divided into three parts: Repository, Service, and Controller.

Repository: Responsible for connecting with the Model and creating the data storage layer.
Service: Implements business logic.
Controller: Packages the business logic into APIs for the front-end View layer to use.

The View layer is further divided into two parts:
Template: Stores HTML interfaces.
Static: Contains CSS and JavaScript files used to render the HTML.


The system currently includes the following features:
User account login system and related interfaces
Basic user interface
Excel spreadsheet upload functionality
Additional functionalities are continuously being added and enhanced.
