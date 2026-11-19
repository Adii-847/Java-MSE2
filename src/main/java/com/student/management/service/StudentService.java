package com.student.management.service;

import com.student.management.model.Student;
import com.student.management.model.UpdateScoresAndSkillsRequest;
import com.student.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Requirement 8: Validate score between 0 and 100.
     */
    private void validateScore(double score, String subjectName) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(subjectName + " score must be between 0 and 100. Provided: " + score);
        }
    }

    /**
     * Q3 - 1: Add a student.
     * Requirement 8: Prevent duplicate student IDs and validate scores between 0 and 100.
     */
    public Student addStudent(Student student) {
        if (student.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null.");
        }

        // Prevent duplicate student IDs
        if (studentRepository.existsById(student.getStudentId())) {
            throw new IllegalArgumentException("Student with ID " + student.getStudentId() + " already exists.");
        }

        // Validate scores between 0 and 100
        validateScore(student.getProgrammingScore(), "Programming");
        validateScore(student.getMathematicsScore(), "Mathematics");

        return studentRepository.save(student);
    }

    /**
     * Q3 - 2: Retrieve all students.
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Q3 - 3 & Requirement 4: Search for a student by ID.
     */
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student with ID " + studentId + " not found."));
    }

    /**
     * Requirement 4: Search student by email address.
     */
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmailAddressIgnoreCase(email)
                .orElseThrow(() -> new NoSuchElementException("Student with email '" + email + "' not found."));
    }

    /**
     * Requirement 4: Search students by course name.
     */
    public List<Student> getStudentsByCourse(String courseName) {
        return studentRepository.findByCourseNameIgnoreCase(courseName);
    }

    /**
     * Q3 - 4: Update student scores and skills.
     */
    public Student updateScoresAndSkills(Long studentId, UpdateScoresAndSkillsRequest request) {
        Student student = getStudentById(studentId);

        if (request.getProgrammingScore() != null) {
            validateScore(request.getProgrammingScore(), "Programming");
            student.setProgrammingScore(request.getProgrammingScore());
        }

        if (request.getMathematicsScore() != null) {
            validateScore(request.getMathematicsScore(), "Mathematics");
            student.setMathematicsScore(request.getMathematicsScore());
        }

        if (request.getTechnicalSkills() != null) {
            student.setTechnicalSkills(request.getTechnicalSkills());
        }

        return studentRepository.save(student);
    }

    /**
     * General Update for all student attributes.
     */
    public Student updateStudent(Long studentId, Student updatedData) {
        Student existingStudent = getStudentById(studentId);

        validateScore(updatedData.getProgrammingScore(), "Programming");
        validateScore(updatedData.getMathematicsScore(), "Mathematics");

        existingStudent.setStudentName(updatedData.getStudentName());
        existingStudent.setEmailAddress(updatedData.getEmailAddress());
        existingStudent.setCourseName(updatedData.getCourseName());
        existingStudent.setProgrammingScore(updatedData.getProgrammingScore());
        existingStudent.setMathematicsScore(updatedData.getMathematicsScore());
        existingStudent.setTechnicalSkills(updatedData.getTechnicalSkills());

        return studentRepository.save(existingStudent);
    }

    /**
     * Q3 - 5: Delete a student record.
     */
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new NoSuchElementException("Cannot delete: Student with ID " + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }

    /**
     * Q3 - 6 & Requirement 5: Generate merit list sorted by average score in descending order.
     * Uses Java Collections / Streams.
     */
    public List<Student> getMeritList() {
        List<Student> students = studentRepository.findAll();
        // Sort descending by average score
        students.sort((s1, s2) -> Double.compare(s2.getAverageScore(), s1.getAverageScore()));
        return students;
    }

    /**
     * Requirement 6: Identify students who have the same technical skills.
     * Returns a Map grouping technical skill -> list of students who possess that skill.
     */
    public Map<String, List<Student>> getStudentsGroupedBySkills() {
        List<Student> students = studentRepository.findAll();
        Map<String, List<Student>> skillMap = new LinkedHashMap<>();

        for (Student student : students) {
            if (student.getTechnicalSkills() != null) {
                for (String skill : student.getTechnicalSkills()) {
                    String cleanSkill = skill.trim();
                    skillMap.computeIfAbsent(cleanSkill, k -> new ArrayList<>()).add(student);
                }
            }
        }
        return skillMap;
    }

    /**
     * Requirement 6: Search students by a specific technical skill.
     */
    public List<Student> getStudentsBySkill(String skill) {
        return studentRepository.findBySkill(skill);
    }

    /**
     * Requirement 7: Display students who need improvement in programming (< 50)
     * but have a mathematics score of at least 75.
     */
    public List<Student> getStudentsNeedingProgrammingImprovementWithHighMath() {
        return studentRepository.findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(50.0, 75.0);
    }
}
