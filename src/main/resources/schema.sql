-- =====================================================================
-- Question 2: MySQL Database Creation and Implementation Requirements
-- Problem: Student Profile and Skill Development Management System
-- =====================================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- Step 2: Create Main Student Table (with Primary Key, Unique constraints, and CHECK constraints for Q2.8)
CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT PRIMARY KEY,                             -- Requirement 8: Prevents duplicate student IDs
    student_name VARCHAR(100) NOT NULL,
    email_address VARCHAR(100) NOT NULL UNIQUE,                -- Unique constraint for email
    course_name VARCHAR(100) NOT NULL,
    programming_score DOUBLE NOT NULL CHECK (programming_score >= 0 AND programming_score <= 100), -- Requirement 8: Validate 0-100
    mathematics_score DOUBLE NOT NULL CHECK (mathematics_score >= 0 AND mathematics_score <= 100)  -- Requirement 8: Validate 0-100
);

-- Step 3: Create Student Skills Table (Normalized table for Java Collections List<String> technicalSkills)
CREATE TABLE IF NOT EXISTS student_skills (
    student_id BIGINT NOT NULL,
    skill VARCHAR(100) NOT NULL,
    PRIMARY KEY (student_id, skill),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- =====================================================================
-- Sample Data Insertion
-- =====================================================================
INSERT INTO students (student_id, student_name, email_address, course_name, programming_score, mathematics_score) VALUES
(101, 'Aarav Sharma', 'aarav.sharma@example.com', 'Computer Science', 92.0, 88.0),
(102, 'Diya Patel', 'diya.patel@example.com', 'Information Technology', 72.0, 84.0),
(103, 'Rohan Verma', 'rohan.verma@example.com', 'Computer Science', 42.0, 80.0),
(104, 'Priya Singh', 'priya.singh@example.com', 'Data Science', 65.0, 60.0),
(105, 'Kunal Nair', 'kunal.nair@example.com', 'Information Technology', 45.0, 40.0)
ON DUPLICATE KEY UPDATE student_name=VALUES(student_name);

INSERT INTO student_skills (student_id, skill) VALUES
(101, 'Java'), (101, 'Spring Boot'), (101, 'MySQL'), (101, 'Git'),
(102, 'Java'), (102, 'Python'), (102, 'SQL'),
(103, 'C++'), (103, 'MySQL'),
(104, 'Python'), (104, 'MySQL'), (104, 'R'),
(105, 'HTML'), (105, 'CSS'), (105, 'JavaScript')
ON DUPLICATE KEY UPDATE skill=VALUES(skill);

-- =====================================================================
-- SQL Queries Implementing Q2 Requirements
-- =====================================================================

-- Requirement 1 & 2 & 3:
-- 1. Calculate each student's average score
-- 2. Assign a performance category based on average:
--    - 85 or above: Excellent
--    - 70–84.99: Good
--    - 50–69.99: Satisfactory
--    - Below 50: Needs Improvement
-- 3. Identify the stronger subject for each student
SELECT 
    student_id,
    student_name,
    programming_score,
    mathematics_score,
    (programming_score + mathematics_score) / 2.0 AS average_score,
    CASE 
        WHEN (programming_score + mathematics_score) / 2.0 >= 85.0 THEN 'Excellent'
        WHEN (programming_score + mathematics_score) / 2.0 >= 70.0 THEN 'Good'
        WHEN (programming_score + mathematics_score) / 2.0 >= 50.0 THEN 'Satisfactory'
        ELSE 'Needs Improvement'
    END AS performance_category,
    CASE 
        WHEN programming_score > mathematics_score THEN 'Programming'
        WHEN mathematics_score > programming_score THEN 'Mathematics'
        ELSE 'Equal'
    END AS stronger_subject
FROM students;

-- Requirement 4: Search for students by ID, email or course
-- By ID:
SELECT * FROM students WHERE student_id = 101;
-- By Email:
SELECT * FROM students WHERE LOWER(email_address) = LOWER('aarav.sharma@example.com');
-- By Course:
SELECT * FROM students WHERE LOWER(course_name) = LOWER('Computer Science');

-- Requirement 5: Generate a merit list sorted by average score in descending order
SELECT 
    student_id,
    student_name,
    course_name,
    (programming_score + mathematics_score) / 2.0 AS average_score
FROM students
ORDER BY average_score DESC;

-- Requirement 6: Identify students who have the same technical skills
SELECT 
    skill,
    GROUP_CONCAT(s.student_name SEPARATOR ', ') AS students_with_skill,
    COUNT(s.student_id) AS student_count
FROM student_skills ss
JOIN students s ON ss.student_id = s.student_id
GROUP BY skill
HAVING COUNT(s.student_id) > 1;

-- Requirement 7: Display students who need improvement in programming (< 50) 
-- but have a mathematics score of at least 75
SELECT 
    student_id,
    student_name,
    programming_score,
    mathematics_score
FROM students
WHERE programming_score < 50 AND mathematics_score >= 75;

-- Requirement 8: Prevent duplicate student IDs and validate scores between 0 and 100
-- (Enforced via PRIMARY KEY on student_id and CHECK constraints on programming_score and mathematics_score)
