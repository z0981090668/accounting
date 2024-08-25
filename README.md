
實作一個使用 Java Spring Boot MVC 架構並串接 PostgreSQL 的會計管理系統。系統中，Model 用於創建資料表，並提供 getter 和 setter 方法。Controller 層被劃分為 Repository、Service 和 Controller 三個部分。Repository 負責連接 Model 並創建儲存層，Service 層負責實作業務邏輯，Controller 則將其包裝為 API 供前端 View 使用。View 層進一步分為 template 和 static，template 用於存放 HTML 介面，static 用於存放 CSS 和 JS 以渲染 HTML。

目前實現的功能包括：

帳號登入系統與相關介面

基本使用者介面

Excel 資料表上傳功能
