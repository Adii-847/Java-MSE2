package com.student.management;

import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("Student Profile and Skill Development Management System is running!");
        System.out.println("API Base URL: http://localhost:8080/api/students");
        System.out.println("==================================================================");
    }

    /**
     * Optional sample data initialization on startup.
     * Inserts sample students if database table is empty.
     */
    @Bean
    public CommandLineRunner initData(StudentRepository studentRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                System.out.println("Populating sample student data...");

                // Student 1: Excellent performer, stronger in programming
                Student s1 = new Student(
                        101L,
                        "Aarav Sharma",
                        "aarav.sharma@example.com",
                        "Computer Science",
                        92.0,
                        88.0,
                        Arrays.asList("Java", "Spring Boot", "MySQL", "Git")
                );

                // Student 2: Good performer, stronger in mathematics
                Student s2 = new Student(
                        102L,
                        "Diya Patel",
                        "diya.patel@example.com",
                        "Information Technology",
                        72.0,
                        84.0,
                        Arrays.asList("Java", "Python", "SQL")
                );

                // Student 3: Needs improvement in programming (< 50) but high math score (>= 75) -> Tests Requirement 7
                Student s3 = new Student(
                        103L,
                        "Rohan Verma",
                        "rohan.verma@example.com",
                        "Computer Science",
                        42.0,
                        80.0,
                        Arrays.asList("C++", "MySQL")
                );

                // Student 4: Satisfactory performer, shares skills with s1 and s2
                Student s4 = new Student(
                        104L,
                        "Priya Singh",
                        "priya.singh@example.com",
                        "Data Science",
                        65.0,
                        60.0,
                        Arrays.asList("Python", "MySQL", "R")
                );

                // Student 5: Needs overall improvement (< 50 average)
                Student s5 = new Student(
                        105L,
                        "Kunal Nair",
                        "kunal.nair@example.com",
                        "Information Technology",
                        45.0,
                        40.0,
                        Arrays.asList("HTML", "CSS", "JavaScript")
                );

                studentRepository.saveAll(List.of(s1, s2, s3, s4, s5));
                System.out.println("Sample student data populated successfully!");
            }
        };
    }
}
