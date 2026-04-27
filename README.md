# Hospital Management System V3.0

This is a web-based application designed to streamline the operations of "Pure Hearts Hospital." It facilitates the interaction between administrators, doctors, and patients through a centralized platform. The system is built using a raw Java technology stack (Servlets, JSP, JDBC) to demonstrate a deep understanding of core web development concepts and the MVC architecture without relying on heavy frameworks.

## Features

The system implements role-based access control with three distinct user types, each with specific capabilities:

### 1. Administrator Module
* **Doctor Management:** Register new doctors into the system and remove existing ones when necessary.
* **Patient Oversight:** View the complete list of registered patients and discharge (delete) patient records.
* **System Dashboard:** Overview of hospital statistics and staff status.

### 2. Doctor Module
* **Personal Dashboard:** A secure, private view for doctors to manage their daily workflow.
* **Appointment Timeline:** A visual schedule of upcoming appointments sorted by time.
* **Cancellation Control:** Ability to cancel appointments directly from the dashboard if availability changes.

### 3. Patient Module
* **User Registration:** Secure sign-up and login functionality.
* **Appointment Booking:** Patients can browse available doctors and book appointments.
* **History & Management:** View a list of upcoming appointments with the option to cancel them if needed.

## Technology Stack

* **Backend:** Java (Jakarta EE), Servlets, JSP
* **Database:** MySQL
* **Data Access:** JDBC, Custom GenericDAO implementation
* **Frontend:** HTML5, CSS3 (Mobile Responsive)
* **Server:** Apache Tomcat 10+
* **Security:** Session Management, Role-based Authorization filters

## Project Structure

The project follows the Model-View-Controller (MVC) architectural pattern:

* `src/main/java/com/hospital/daos`: Data Access Objects handling direct database SQL operations.
* `src/main/java/com/hospital/pojos`: Plain Old Java Objects representing database entities.
* `src/main/java/com/hospital/servlets`: Controllers that process HTTP requests and manage application logic.
* `src/main/webapp`: JSP views, CSS stylesheets, and client-side resources.

## Setup and Installation

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Apache Tomcat 10.1 or higher
* MySQL Server

### Database Configuration
1.  Create a MySQL database named `hms`.
2.  Import the schema script provided in the `sql/` directory.
3.  **Configuration:** This application uses environment variables for database credentials to ensure security. You must set the following environment variables on your machine:
    * `DB_URL`: `jdbc:mysql://localhost:3306/your_database_name`
    * `DB_USER`: Your MySQL username
    * `DB_PASSWORD`: Your MySQL password

### Deployment
1.  Clone the repository.
2.  Open the project in your IDE (IntelliJ IDEA or Eclipse).
3.  Build the artifact (WAR file).
4.  Run the server and access the application at `http://localhost:8080/HMS-Web`.

## Future Improvements
* **Password Encryption:** Implement hashing (e.g., BCrypt) to securely store user passwords.
* **Email Notifications:** Automated email confirmation for booked appointments.
* **Medical Records:** Allow doctors to upload prescription files for patients.

## What i have learned ?
* **Your url's should be passed to the servlet not the page (frontend) this error happened to me a lot and thank god i know it now**
* **When you want to set an error setting in as session error will make it appear for all other pages. However, to solve it you just set it in the requset**
* **When we have a delete appointmentservlet we should redict the user to the page he is in. that will cause it to be updated.**
* **sometimes debugging inside the method that should work and behave probably will show you the real cause**
* **forms. In HTML supports only get and post. Even though i know exactly which method to use yet i couldn't due to its restrictions.**
* **When we want to delete a databse entity that has a user_id we must delete the assoicated user also. Otherwise it'll be lost but still in db !**

## Possible questions
* **Why didn't you jsut use spring to make this project since it's more modern ?** 
*  answer: I did this project to prove my fundementals and to strengthen them
*  **Why didn't you add encryption for the password ?**
*  answer: TBH i thought i'd be a cool thing to learn in the upcoming version of this servlet edition (V3.1)
*  **Why some methods seems unclean ? (messy code)**
*  answer: in the early phases of this project i just wanted to get used to the enviroment of servlet and jsp. Therefore i didn't focus much in the SRP. However, in the upcoming versions i will refactor it
*  **Why there are no nurses ?**
*  answer: well. I thought of adding nurses yet i saw it unrealistic because all that matters for a patient are the doctors.
  

## License
This project is open-source and available for educational purposes.
