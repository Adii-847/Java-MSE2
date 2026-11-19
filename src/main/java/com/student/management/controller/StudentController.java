package com.student.management.controller;

import com.student.management.model.Student;
import com.student.management.model.UpdateScoresAndSkillsRequest;
import com.student.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ========================================================
    // Q3 - 1: Add a student
    // ========================================================
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.addStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================================================
    // Q3 - 2: Retrieve all students
    // ========================================================
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ========================================================
    // Q3 - 3: Search for a student by ID
    // ========================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================================================
    // Q2 - 4: Search for students by Email
    // ========================================================
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getStudentByEmail(@PathVariable String email) {
        try {
            Student student = studentService.getStudentByEmail(email);
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================================================
    // Q2 - 4: Search for students by Course Name
    // ========================================================
    @GetMapping("/course/{courseName}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String courseName) {
        return ResponseEntity.ok(studentService.getStudentsByCourse(courseName));
    }

    // ========================================================
    // Q3 - 4: Update student scores and skills
    // ========================================================
    @PutMapping("/{id}/scores-and-skills")
    public ResponseEntity<?> updateScoresAndSkills(@PathVariable Long id,
                                                   @RequestBody UpdateScoresAndSkillsRequest request) {
        try {
            Student updatedStudent = studentService.updateScoresAndSkills(id, request);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // General update for all student fields
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================================================
    // Q3 - 5: Delete a student record
    // ========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(Map.of("message", "Student with ID " + id + " deleted successfully."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================================================
    // Q3 - 6 & Q2 - 5: Display the merit list
    // (Sorted by average score in descending order)
    // ========================================================
    @GetMapping("/merit-list")
    public ResponseEntity<List<Student>> getMeritList() {
        return ResponseEntity.ok(studentService.getMeritList());
    }

    // ========================================================
    // Q2 - 6: Identify students who have the same technical skills
    // Returns skills mapped to list of students
    // ========================================================
    @GetMapping("/skills/same")
    public ResponseEntity<Map<String, List<Student>>> getStudentsGroupedBySkills() {
        return ResponseEntity.ok(studentService.getStudentsGroupedBySkills());
    }

    // Q2 - 6: Search students having a specific technical skill
    @GetMapping("/skills/search")
    public ResponseEntity<List<Student>> getStudentsBySkill(@RequestParam String skill) {
        return ResponseEntity.ok(studentService.getStudentsBySkill(skill));
    }

    // ========================================================
    // Q2 - 7: Display students who need improvement in programming (< 50)
    // but have a mathematics score of at least 75
    // ========================================================
    @GetMapping("/filter/programming-improvement-math-75")
    public ResponseEntity<List<Student>> getStudentsNeedingProgrammingImprovementWithHighMath() {
        return ResponseEntity.ok(studentService.getStudentsNeedingProgrammingImprovementWithHighMath());
    }
}
