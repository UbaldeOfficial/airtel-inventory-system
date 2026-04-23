*Note on Accessing the Live System* 

The application is hosted on a free-tier cloud platform. Due to this limitation, the system automatically goes into a sleep state after approximately 15 minutes of inactivity.

When accessed again, the application may take around 30 to 60 seconds (or slightly longer) to load as the server initializes.

Kindly allow this initial loading time. Once the system is active, it will perform normally without delays.









Inventory Management System
Project Overview

IMS (Inventory Management System) is a complete solution designed to help organizations manage and track end-user equipment such as laptops, desktops, and mobile devices.

The system makes it easy to know who is using which device, what condition it is in, and how assets move within the organization. It also simplifies issuing, returning, and monitoring equipment while keeping a clear history of all actions.

Live Demo

You can access the deployed system here:
https://airtel-inventory.onrender.com

Since the system is hosted on a free tier, it may take around 30 to 60 seconds to load the first time.

Developers

This project was developed by:

IBYIMANIKORA Ubalde – Registration Number: 24RP05770
CYUZUZO Aline – Registration Number: 24RP03478

System Access Credentials

The system has different user roles with specific access levels:

System Administrator
Username: 24RP05770
Password: 24RP03478

HR
Username: 24RP03478
Password: Aline123

Technician
Username: 24RP05770tech
Password: UbaldeTech123

Technology Stack

The system is built using modern backend and frontend technologies.

It uses Spring Boot as the main framework with Java 17 as the programming language. Security is handled using Spring Security with encrypted passwords.

For databases, MySQL is used in the local environment through XAMPP, while PostgreSQL is used in the cloud deployment on Render. Hibernate and JPA are used for database interaction.

The frontend is built with Thymeleaf templates combined with custom CSS. Maven is used for dependency management, and the project was developed using Eclipse IDE.

The application is deployed on Render using Docker.

Key Features

The system includes secure authentication with role-based access control, ensuring that each user only accesses what they are allowed to.

It provides full asset management functionality, allowing users to create, update, view, and delete records. Instead of permanently deleting data, a soft delete approach is used so that records remain available for auditing.

Employees can also be managed within the system, including their departments and activity status.

Assets can be assigned to employees, and the system automatically tracks assignment dates, returns, and condition updates.

Every action performed in the system is logged, creating a complete audit trail that shows who did what and when.

A dashboard provides real-time statistics, recent activity updates, and alerts such as maintenance reminders. Reports can also be prepared for export in formats like PDF and Excel.

Deployment Overview

The project is designed to work both locally and in the cloud.

During development, it runs on a local machine using Eclipse, Spring Boot, and MySQL through XAMPP.

For production, it is deployed on Render using Docker, with PostgreSQL as the cloud database.

This setup ensures flexibility and allows the system to scale beyond local use.

Local Installation Guide

To run the project locally, you need Java JDK 17 or higher, Eclipse IDE, XAMPP for MySQL, Maven, and Lombok.

Start by extracting the project into your workspace. Then open XAMPP and start Apache and MySQL.

Create a database named airtel_inventory_db using phpMyAdmin.

Import the project into Eclipse as a Maven project. After that, install Lombok by running its installer and linking it to your Eclipse IDE.

Update Maven dependencies to ensure everything is correctly loaded.

Finally, run the application as a Spring Boot project and open your browser at http://localhost:8080
.

Cloud Deployment

The system is deployed on Render using Docker containers.

The deployment uses a production configuration file that connects to a PostgreSQL database through environment variables such as database URL, username, and password.

The application runs in the cloud environment and is accessible through the provided public link.

Application Configuration

For local development, the system connects to a MySQL database running on localhost.

In production, the system uses environment variables to securely connect to PostgreSQL. This allows the same application to run in different environments without changing the code.

Troubleshooting

If the application fails to start because port 8080 is already in use, you can stop the conflicting application or change the port in the configuration.

If the database connection fails, ensure that MySQL is running in XAMPP.

If Lombok-related errors appear, reinstall Lombok and restart Eclipse.

If templates or styles are not loading, check file locations or refresh your browser.

If the deployed site does not load immediately, wait a short time for the free hosting service to wake up.

Project Structure

The project follows a standard Spring Boot structure.

The Java source code includes configuration files, controllers, entities, repositories, services, security components, and utility classes.

Resources include static files like CSS and dynamic Thymeleaf templates for different sections such as authentication, assets, employees, and assignments.

Future Improvements

Several features are planned for future development. These include QR code scanning, email notifications, a REST API for integration, a mobile application, barcode support, and more advanced analytics.

License

This project was developed as part of the Backend Development with Java coursework at Rwanda Polytechnic. All rights are reserved.

Acknowledgments

Special thanks to the teams and tools that made this project possible, including Spring Boot, Hibernate, Thymeleaf, Lombok, Apache POI, ZXing, and Render for cloud hosting.

© 2026 IBYIMANIKORA Ubalde and CYUZUZO Aline
