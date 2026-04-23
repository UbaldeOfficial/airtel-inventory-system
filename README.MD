
## **Live Demo**

**Access the system online:**  
https://airtel-inventory.onrender.com  

**Note on Accessing the Live System**

The application is hosted on a **free-tier cloud platform**. Due to this limitation, the system automatically goes into a sleep state after approximately **15 minutes of inactivity**.

When accessed again, the application may take around **30 to 60 seconds (or slightly longer)** to load as the server initializes.

Kindly allow this initial loading time. Once the system is active, it will perform normally without delays.

---




 Inventory Management System

## **Project Overview**

IMS (Inventory Management System) is a complete solution designed to manage and track end-user equipment such as laptops, desktops, and mobile devices.

The system provides clear visibility of **asset ownership**, **device condition**, and **asset movement**, while simplifying the process of issuing and returning equipment.

---


## **Developers**

**IBYIMANIKORA Ubalde** – 24RP05770  
**CYUZUZO Aline** – 24RP03478  

---

## **System Access Credentials**

**System Administrator**  
Username: 24RP05770  
Password: 24RP03478  

**HR**  
Username: 24RP03478  
Password: Aline123  

**Technician**  
Username: 24RP05770tech  
Password: UbaldeTech123  

---

## **Technology Stack**

The system is built using modern technologies:

- **Spring Boot** (Backend Framework)  
- **Java 17** (Programming Language)  
- **Spring Security** (Authentication & Authorization)  
- **MySQL (XAMPP)** for local development  
- **PostgreSQL** for cloud deployment  
- **Hibernate / JPA** for database management  
- **Thymeleaf + Custom CSS** for frontend  
- **Maven** for dependency management  
- **Eclipse IDE** for development  
- **Render (Docker)** for cloud hosting  

---

## **Key Features**

### **Authentication & Security**
- Secure login using **Spring Security**
- **BCrypt password encryption**
- **Role-based access control** (SysAdmin, HR, Technician)

### **Asset Management**
- Full **CRUD operations** (Create, Read, Update, Delete)
- **Soft delete** for audit purposes
- **Asset condition tracking**
- Search by **name, serial number, or brand**
- Ready for **QR code integration**

### **Employee Management**
- Manage employee records
- Track **departments**
- Control **active/inactive status**

### **Assignment Tracking**
- Assign assets to employees
- Track **assignment and return dates**
- Automatic **status updates**
- Record **return condition notes**

### **Audit Logging**
- Automatic tracking of all system actions
- Records **who performed each action and when**
- Full **audit trail for accountability**

### **Dashboard & Reporting**
- **Real-time statistics**
- **Recent activity monitoring**
- Maintenance alerts
- Support for **PDF and Excel reports**

---

## **Deployment Overview**

The system works in both **local** and **cloud** environments.

- **Local Development:** Runs using Eclipse, Spring Boot, and MySQL (XAMPP)  
- **Cloud Deployment:** Hosted on Render using Docker and PostgreSQL  

This setup ensures flexibility and scalability.

---

## **Local Installation Guide**

### **Requirements**
- Java JDK 17 or higher  
- Eclipse IDE  
- XAMPP (MySQL)  
- Maven  
- Lombok  

### **Steps**

1. Extract the project into your workspace  
2. Start **Apache and MySQL** in XAMPP  
3. Create a database named **airtel_inventory_db** in phpMyAdmin  
4. Import the project into Eclipse as a **Maven project**  
5. Install **Lombok** and restart Eclipse  
6. Update Maven dependencies  
7. Run the application as a **Spring Boot App**  
8. Open: http://localhost:8080  

---

## **Cloud Deployment**

The system is deployed on **Render** using Docker.

- Uses **PostgreSQL** as the cloud database  
- Configuration handled via **environment variables**  
- Accessible through the public deployment link  

---

## **Application Configuration**

- **Local environment:** Uses MySQL on localhost  
- **Production environment:** Uses PostgreSQL with secure environment variables  

This allows the system to run seamlessly in different environments.

---

## **Troubleshooting**

- If port **8080 is in use**, stop the running app or change the port  
- If **MySQL fails**, ensure XAMPP is running  
- If **Lombok errors appear**, reinstall and restart Eclipse  
- If **pages or CSS do not load**, refresh the browser  
- If the **live system is slow**, wait for the server to wake up  

---

## **Project Structure**

The project follows a standard **Spring Boot architecture**:

- Configuration files  
- Controllers (handling requests)  
- Entities (database models)  
- Repositories (data access)  
- Services (business logic)  
- Security components  
- Utility classes (QR, PDF, Excel)

Resources include:
- Static files (CSS)  
- Thymeleaf templates for UI (login, assets, employees, assignments)  

---

## **Future Improvements**

Planned features include:

- QR code scanning  
- Email notifications  
- REST API integration  
- Mobile application  
- Barcode support  
- Advanced analytics  

---

## **License**

This project was developed as part of **Backend Development with Java coursework** at Rwanda Polytechnic.  
**All rights reserved.**

---

## **Acknowledgments**

Thanks to:

- Spring Boot  
- Hibernate  
- Thymeleaf  
- Lombok  
- Apache POI  
- ZXing  
- Render Cloud Hosting  

---

**© 2026 IBYIMANIKORA Ubalde & CYUZUZO Aline**
