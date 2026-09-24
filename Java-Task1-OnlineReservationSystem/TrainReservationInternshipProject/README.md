TRAIN RESERVATION SYSTEM
Internship Project

Technology:
- Java 17
- Java Swing
- JDBC
- MySQL
- Maven

PROJECT FEATURES
1. Login using database credentials
2. Dashboard
3. Search train by train number
4. Book a ticket
5. Automatic PNR generation
6. Booking confirmation
7. Cancel ticket using PNR
8. Input validation
9. MySQL database storage

DEFAULT LOGIN
Username: admin
Password: admin123

SETUP
1. Install JDK 17 or newer.
2. Install MySQL Server.
3. Create/import the database using:
   database/train_reservation.sql
4. Open src/DBConnection.java.
5. Change the MySQL password:
   private static final String PASSWORD = "YOUR_PASSWORD";
6. Open the project folder in VS Code or IntelliJ IDEA.
7. Let Maven download the MySQL Connector/J dependency.
8. Run Main.java.

MAVEN COMMANDS
mvn clean compile
mvn exec:java

TEST FLOW
Login -> Book Ticket -> Find Train -> Enter details -> Book Ticket
-> Note the PNR -> Cancel Ticket -> Enter PNR -> Fetch -> Confirm Cancellation

NOTE
This is a student internship project. The application uses a simple Swing/JDBC structure so that the source code is easy to understand and maintain.
