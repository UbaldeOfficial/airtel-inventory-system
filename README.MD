📋 PROJECT OVERVIEW
Airtel Inventory Management System is a comprehensive, offline-capable enterprise solution for tracking and managing end-user equipment (laptops, desktops, mobile devices). The system provides complete visibility of asset ownership, device condition, and efficient issue/return processes.

🎯 FEATURES
Module	Features
Authentication	Secure login with Spring Security, BCrypt password encryption
Role-Based Access	ADMIN, HR, TECHNICIAN roles with different permissions
Asset Management	Full CRUD operations, soft delete, QR code generation ready
Employee Management	Full CRUD operations, department tracking
Assignment Tracking	Assign assets to employees, track return dates, auto-update status
Audit Logging	Automatic logging of all actions (CREATE, UPDATE, ASSIGN, RETURN)
Dashboard	Real-time statistics, charts, recent activity feed
Search & Filter	Search assets by name/serial/brand, filter by status
Reporting	PDF and Excel export ready
Offline Capable	Runs entirely on localhost with MySQL database
🛠️ TECHNOLOGY STACK
Layer	Technology	Version
Framework	Spring Boot	2.7.18
Language	Java	17
Security	Spring Security	5.7.11
Database	MySQL (XAMPP)	8.0
ORM	Hibernate / JPA	5.6.15
Frontend	Thymeleaf	3.0.15
Styling	Custom CSS	-
Build Tool	Maven	3.x
IDE	Eclipse	2023-09
Server	Embedded Tomcat	9.0.83
📁 PROJECT STRUCTURE
text
src/main/java/com/airtel/inventory_system/
├── config/                    # Configuration classes
│   ├── SecurityConfig.java    # Spring Security configuration
│   ├── WebConfig.java         # Web MVC configuration
│   └── DataInitializer.java   # Default users creation
│
├── controller/                # Controllers
│   ├── web/                   # MVC Controllers
│   │   ├── AuthController.java
│   │   ├── DashboardController.java
│   │   ├── AssetController.java
│   │   ├── EmployeeController.java
│   │   └── AssignmentController.java
│   └── rest/                  # REST API (future)
│
├── dto/                       # Data Transfer Objects
│   ├── AssetDTO.java
│   ├── EmployeeDTO.java
│   └── AssignmentDTO.java
│
├── entity/                    # JPA Entities
│   ├── User.java
│   ├── Asset.java
│   ├── Employee.java
│   ├── Assignment.java
│   └── AuditLog.java
│
├── repository/                # Spring Data JPA Repositories
│   ├── UserRepository.java
│   ├── AssetRepository.java
│   ├── EmployeeRepository.java
│   ├── AssignmentRepository.java
│   └── AuditLogRepository.java
│
├── service/                   # Service Interfaces
│   ├── UserService.java
│   ├── AssetService.java
│   ├── EmployeeService.java
│   ├── AssignmentService.java
│   ├── AuditLogService.java
│   ├── QRCodeService.java
│   ├── PDFExportService.java
│   └── ExcelExportService.java
│
├── service/impl/              # Service Implementations
│   ├── UserServiceImpl.java
│   ├── AssetServiceImpl.java
│   ├── EmployeeServiceImpl.java
│   ├── AssignmentServiceImpl.java
│   ├── AuditLogServiceImpl.java
│   └── ...
│
├── security/                  # Security Classes
│   ├── CustomUserDetails.java
│   └── CustomUserDetailsService.java
│
├── exception/                 # Custom Exceptions
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   └── BusinessRuleViolationException.java
│
├── util/                      # Utility Classes
│   ├── QRCodeGenerator.java
│   ├── PDFGenerator.java
│   └── ExcelGenerator.java
│
└── InventorySystemApplication.java  # Main Application Class
text
src/main/resources/
├── static/
│   └── css/
│       └── style.css          # Custom CSS styling
│
├── templates/                 # Thymeleaf Templates
│   ├── auth/
│   │   └── login.html         # Login page
│   ├── dashboard.html         # Main dashboard
│   ├── error.html             # Error page
│   ├── error/
│   │   └── access-denied.html # Access denied page
│   ├── assets/
│   │   ├── list.html          # Assets list
│   │   ├── form.html          # Asset form (create/edit)
│   │   └── view.html          # Asset details
│   ├── employees/
│   │   ├── list.html          # Employees list
│   │   └── form.html          # Employee form
│   └── assignments/
│       ├── list.html          # Assignments list
│       └── form.html          # Assignment form
│
└── application.properties     # Application configuration
🚀 INSTALLATION & SETUP
Prerequisites
Software	Version	Purpose
Java JDK	17+	Runtime
Eclipse IDE	2023-09+	Development
XAMPP	8.0+	MySQL Database
Maven	3.x	Build Tool
Lombok	1.18.30	Boilerplate reduction
Step 1: Clone/Extract Project
bash
# Extract the project to your workspace
D:\Y2 IT\Semester two\MVNApp\inventory-system\
Step 2: Configure Database
Start XAMPP Control Panel

Start Apache and MySQL services

Open phpMyAdmin: http://localhost/phpmyadmin

Create database: airtel_inventory_db (UTF-8)

Step 3: Import Project to Eclipse
Open Eclipse

File → Import → Maven → Existing Maven Projects

Browse to project folder

Select project → Finish

Step 4: Install Lombok
Locate lombok-1.18.30.jar in Maven repository

Double-click to run installer

Select Eclipse installation → Install/Update

Restart Eclipse

Step 5: Update Maven Dependencies
Right-click project → Maven → Update Project

Check "Force Update of Snapshots/Releases"

Click OK

Step 6: Run Application
Navigate to InventorySystemApplication.java

Right-click → Run As → Spring Boot App

Wait for Started InventorySystemApplication message

Step 7: Access Application
Open browser: http://localhost:8080

Login with default credentials

🔑 DEFAULT CREDENTIALS
Role	Username	Password	Access Level
ADMIN	UbaldeAdmin	AdminUbalde123	Full System Access
HR	Alinehr	Alinehr123	Employees + Reports
TECHNICIAN	UbaldeTech	Alinetech123	Assets + Assignments
📊 DATABASE SCHEMA
sql
airtel_inventory_db/
├── users                 # User accounts
├── user_roles           # User roles (ADMIN, HR, TECHNICIAN)
├── assets               # Asset/Equipment records
├── employees            # Employee records
├── assignments          # Asset assignments
└── audit_logs           # System activity logs
🔐 ROLE PERMISSIONS
Feature	ADMIN	HR	TECHNICIAN
Dashboard	✅	✅	✅
View Assets	✅	❌	✅
Create/Edit Assets	✅	❌	✅
Delete Assets	✅	❌	✅
View Employees	✅	✅	❌
Create/Edit Employees	✅	✅	❌
View Assignments	✅	✅	✅
Create Assignments	✅	✅	✅
Return Assets	✅	✅	✅
Reports	✅	✅	❌
Audit Logs	✅	❌	❌
🎮 HOW TO USE
1. Login
Enter username and password

System redirects to Dashboard

2. Dashboard
View statistics (Total Assets, Available, Assigned, Employees)

See recent activity feed

Check maintenance due alerts

3. Manage Assets
Create: Click "+ Add New Asset" → Fill form → Save

View: Click "View" on any asset

Edit: Click "Edit" → Modify fields → Save

Delete: Click "Delete" → Confirm (soft delete)

4. Manage Employees
Create: Click "+ Add New Employee" → Fill form → Save

Edit: Click "Edit" → Modify fields → Save

Delete: Click "Delete" → Employee becomes inactive

5. Manage Assignments
Create: Click "+ New Assignment" → Select Asset & Employee → Set dates → Save

Return: Click "Return" on active assignment → Asset becomes available

6. Search & Filter
Use search bar to find assets/employees

Filter assets by status

Filter assignments by Active/Overdue/All

🛠️ TROUBLESHOOTING
Issue	Solution
Port 8080 already in use	Change server.port=8081 in application.properties
MySQL connection refused	Ensure XAMPP MySQL is running
Lombok errors	Run Lombok installer and restart Eclipse
Template not found	Check file exists in correct folder
CSS not loading	Hard refresh (Ctrl+F5)
Access Denied	Login with correct role or check permissions
📝 APPLICATION CONFIGURATION
application.properties

properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/airtel_inventory_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server
server.port=8080

# Thymeleaf
spring.thymeleaf.cache=false
🚧 FUTURE ENHANCEMENTS
Feature	Status
QR Code Scanning	Planned
Email Notifications	Planned
REST API	Ready for integration
Mobile App	Future
Barcode Support	Future
Advanced Analytics	Future
👨‍💻 DEVELOPER
Field	Value
Name	Ubalde IBYIMANIKORA & CYUZUZO Aline

Project	Airtel Inventory Management System
Version	1.0.0
Date	April 2026
Framework	Spring Boot 2.7.18
Java	17
📄 LICENSE
This project is developed for Airtel as an enterprise inventory management solution. All rights reserved.

🙏 ACKNOWLEDGMENTS
Spring Boot Team

Hibernate Team

Thymeleaf Team

Lombok Project

Apache POI

ZXing (QR Code)

📞 SUPPORT
For any issues or questions:

Check the troubleshooting section

Review console logs in Eclipse

Verify database connection in XAMPP

© 2026 Airtel Inventory Management System. All Rights Reserved.