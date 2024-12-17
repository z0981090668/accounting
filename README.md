The system is implemented using the Java Spring Boot MVC framework and connects to a PostgreSQL database. In this architecture, the Model layer is responsible for creating database tables and providing getter and setter methods. The MVC architecture is divided into three parts: Model, View, and Controller.

All Layers:

Repository: Responsible for interacting with the Model and handling the data storage layer.
Service: Implements the business logic.
Controller: Packages the business logic into APIs for the front-end View layer to consume.

The View layer is further divided into two parts:

Template: Stores HTML interfaces.
Static: Contains CSS and JavaScript files used to render the HTML.

Currently, the system includes the following features:

User account login system and related interfaces
Basic user interface
Excel spreadsheet upload functionality
Expenditure data search based on conditions and display for the front-end interface
Additional functionalities are continuously being added and enhanced.
