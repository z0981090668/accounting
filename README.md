
實作一個使用 Java Spring Boot MVC 架構並串接 PostgreSQL 的會計管理系統。系統中，Model 用於創建資料表，並提供 getter 和 setter 方法。Controller 層被劃分為 Repository、Service 和 Controller 三個部Repository 負責連接 Model 並創建儲存層，Service 層負責實作業務邏輯，Controller 則將其包裝為 API 供前端 View 使用。View 層進一步分為 template 和 static，template 用於存放 HTML 介面，static 用於存放 CSS 和 JS 以渲染 HTML。

目前實現的功能包括：

帳號登入系統與相關介面

基本使用者介面

Excel 資料表上傳功能

目前功能持續在增加和強化中

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
