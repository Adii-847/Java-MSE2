# Student Profile and Skill Development Management System

**Java Spring Boot + MySQL Project for Mid Semester Exam 2 (MSE2)**

---

## 📌 Project Overview
This project implements the complete solution for the **Student Profile and Skill Development Management System** using **Java**, **Spring Boot**, **Spring Data JPA**, and **MySQL**.

### Question Coverage:
- **Q1 (5 Marks):** `Student` model class with all required attributes, encapsulation, constructors, getters, setters, and appropriate object methods (`getAverageScore()`, `getPerformanceCategory()`, `getStrongerSubject()`, `toString()`, `equals()`, `hashCode()`).
- **Q2 (5 Marks):** MySQL database & normalized schema (`students` and `student_skills`), constraints (duplicate ID prevention, 0–100 score validation, unique email), and business rules.
- **Q3 (10 Marks):** Spring Boot REST APIs for adding, retrieving, searching, updating, deleting students, and displaying the merit list.

---

## 📁 Project Structure

```
Java Mse2/
├── pom.xml                                    # Maven Dependencies & Configuration
├── schema.sql                                 # MySQL Database & Table Creation Script
├── README.md                                  # Complete Documentation & Test Guide
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── student/
        │           └── management/
        │               ├── StudentManagementApplication.java  # Main Application & Data Seeder
        │               ├── model/
        │               │   ├── Student.java                   # Q1: Student Entity with Encapsulation & Logic
        │               │   └── UpdateScoresAndSkillsRequest.java
        │               ├── repository/
        │               │   └── StudentRepository.java         # Spring Data JPA Repository
        │               ├── service/
        │               │   └── StudentService.java            # Q2: Business Logic & Validations
        │               └── controller/
        │                   └── StudentController.java         # Q3: REST APIs
        └── resources/
            ├── application.properties         # Database connection settings
            └── schema.sql                     # Schema & SQL verification queries
```

---

## 🗄️ Database Setup (MySQL)

1. Open **MySQL Workbench** or your MySQL command line.
2. Execute the script inside [`schema.sql`](file:///C:/Users/Aditya%20Agarwal/Desktop/Java%20Mse2/schema.sql):
```sql
CREATE DATABASE IF NOT EXISTS student_management_db;
```
3. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

*(Note: If MySQL is not running on your machine, you can also uncomment the H2 in-memory database lines in `application.properties` to run and test immediately!)*

---

## 🚀 How to Import and Run in Eclipse IDE (Step-by-Step)

The project has been configured with native **Eclipse metadata files** (`.project`, `.classpath`, `.launch`) and Maven m2e support so it imports seamlessly without configuration errors.

### Step 1: Open Eclipse and Import Project
1. Open **Eclipse IDE**.
2. Click **File** ➔ **Import...**
3. Select **Maven** ➔ **Existing Maven Projects** (or **General** ➔ **Existing Projects into Workspace**).
4. Click **Next**.
5. In **Root Directory**, click **Browse...** and select:
   ```
   C:\Users\Aditya Agarwal\Desktop\Java Mse2
   ```
6. Ensure `/pom.xml com.student:student-management:1.0.0:jar` is checked.
7. Click **Finish**. Eclipse will import and configure the project.

*(Optional Tip: If Eclipse shows any sync icon, right-click the project folder ➔ **Maven** ➔ **Update Project...** (shortcut `Alt + F5`) ➔ click **OK**).*

---

### Step 2: Configure Database in Eclipse
Open `src/main/resources/application.properties` from Eclipse's Package Explorer:
- If your local MySQL `root` password is not `root`, change `spring.datasource.password=your_password`.
- If you don't have MySQL started, you can uncomment the **H2 database** lines at the bottom of `application.properties` to test immediately without MySQL.

---

### Step 3: Run the Application in Eclipse
You can run it in either of these simple ways:
- **Method A:** In Package Explorer, open `src/main/java` ➔ `com.student.management` ➔ right-click `StudentManagementApplication.java` ➔ **Run As** ➔ **Java Application** (or **Spring Boot App**).
- **Method B:** Right-click the pre-configured `StudentManagementApplication.launch` file in the project root ➔ **Run As** ➔ **StudentManagementApplication**.

You will see the Spring Boot console output:
```
==================================================================
Student Profile and Skill Development Management System is running!
API Base URL: http://localhost:8080/api/students
==================================================================
```

---

## 📡 REST API Reference (Q3 & Q2)

Base URL: `http://localhost:8080/api/students`

| S.No | Method | Endpoint | Description |
|---|---|---|---|
| 1 | `POST` | `/api/students` | **Add a student** (Validates duplicate IDs and scores between 0-100) |
| 2 | `GET` | `/api/students` | **Retrieve all students** |
| 3 | `GET` | `/api/students/{id}` | **Search student by ID** |
| 4 | `GET` | `/api/students/email/{email}` | Search student by Email |
| 5 | `GET` | `/api/students/course/{courseName}` | Search students by Course |
| 6 | `PUT` | `/api/students/{id}/scores-and-skills` | **Update student scores and skills** |
| 7 | `PUT` | `/api/students/{id}` | Full update of student details |
| 8 | `DELETE` | `/api/students/{id}` | **Delete a student record** |
| 9 | `GET` | `/api/students/merit-list` | **Display merit list** (Sorted by average score descending) |
| 10 | `GET` | `/api/students/skills/same` | Identify students with same technical skills (Grouped) |
| 11 | `GET` | `/api/students/skills/search?skill=Java` | Search students with a specific skill |
| 12 | `GET` | `/api/students/filter/programming-improvement-math-75` | Students with programming < 50 and math >= 75 |

---

## 🧪 Sample Payloads & Postman Examples

### 1. Add a Student (POST `/api/students`)
```json
{
  "studentId": 106,
  "studentName": "Sneha Gupta",
  "emailAddress": "sneha.gupta@example.com",
  "courseName": "Computer Science",
  "programmingScore": 88.5,
  "mathematicsScore": 91.0,
  "technicalSkills": ["Java", "Spring Boot", "React"]
}
```

**Response (201 Created):**
```json
{
  "studentId": 106,
  "studentName": "Sneha Gupta",
  "emailAddress": "sneha.gupta@example.com",
  "courseName": "Computer Science",
  "programmingScore": 88.5,
  "mathematicsScore": 91.0,
  "technicalSkills": [
    "Java",
    "Spring Boot",
    "React"
  ],
  "averageScore": 89.75,
  "performanceCategory": "Excellent",
  "strongerSubject": "Mathematics"
}
```

---

### 2. Update Student Scores and Skills (PUT `/api/students/106/scores-and-skills`)
```json
{
  "programmingScore": 95.0,
  "mathematicsScore": 93.0,
  "technicalSkills": ["Java", "Spring Boot", "React", "Docker", "Kubernetes"]
}
```

---

### 3. Display Merit List (GET `/api/students/merit-list`)
Returns all students sorted by `averageScore` in descending order.

---

### 4. Display Students Needing Programming Improvement but Math >= 75
**Endpoint:** `GET /api/students/filter/programming-improvement-math-75`  
Finds students who scored `< 50` in programming and `>= 75` in mathematics (e.g. Student 103: Rohan Verma).

---

## 🎯 Question-by-Question Verification

| Question | Requirement | Implementation in Code |
|---|---|---|
| **Q1** | Attributes: studentId, name, email, course, scores, skills list | `Student.java` attributes |
| **Q1** | Encapsulation, constructors, getters, setters, object methods | `Student.java` (private fields, public getters/setters, `getAverageScore()`, `getPerformanceCategory()`, `getStrongerSubject()`, `toString()`, `equals()`) |
| **Q2.1** | Calculate average score | `Student.getAverageScore()`: `(programmingScore + mathematicsScore) / 2.0` |
| **Q2.2** | Performance category (>=85: Excellent, 70-84.99: Good, 50-69.99: Satisfactory, <50: Needs Improvement) | `Student.getPerformanceCategory()` |
| **Q2.3** | Stronger subject identification | `Student.getStrongerSubject()` |
| **Q2.4** | Search by ID, email, course | `StudentRepository.findById`, `findByEmailAddressIgnoreCase`, `findByCourseNameIgnoreCase` |
| **Q2.5** | Merit list sorted by average score descending | `StudentService.getMeritList()` using Java Collections sort |
| **Q2.6** | Identify students who have the same technical skills | `StudentService.getStudentsGroupedBySkills()` |
| **Q2.7** | Students needing programming improvement (<50) and math >= 75 | `StudentService.getStudentsNeedingProgrammingImprovementWithHighMath()` |
| **Q2.8** | Prevent duplicate IDs & validate scores between 0 and 100 | Validation in `StudentService.java` + SQL Primary Key & CHECK constraints |
| **Q3.1-6** | REST APIs for Add, Retrieve all, Search ID, Update scores/skills, Delete, Merit list | `StudentController.java` endpoints |
