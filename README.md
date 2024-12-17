# Accounting System

## System Architecture Overview

This system is implemented using the Java Spring Boot MVC framework and connects to a PostgreSQL database for basic accounting purposes. The MVC architecture is divided into three parts: **Model**, **View**, and **Controller**. Below is an overview of the architecture and key components of the system.

### All Layers:
- **Repository**: Responsible for interacting with the Model and handling the data storage layer.
- **Service**: Implements the business logic.
- **Model**: Responsible for creating database tables and providing getter and setter methods.
- **Controller**: Packages the business logic into APIs for the front-end **View** layer to consume.

The **View** layer is further divided into two parts:
- **Template**: Stores HTML interfaces.
- **Static**: Contains CSS and JavaScript files used to render the HTML.

## Features

Currently, the system includes the following features:
- User account login system and related interfaces
- Basic user interface
- Excel spreadsheet upload functionality
- Expenditure data search based on conditions and display for the front-end interface

## Future Enhancements

- Ongoing addition of new features
- Continuous improvement of existing functionality

## Technologies Used

- **Java** (Spring Boot MVC Framework)
- **PostgreSQL** (Database)
- **HTML/CSS/JavaScript** (Frontend Templates and Static Files)

