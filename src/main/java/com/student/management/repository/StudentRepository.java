package com.student.management.repository;

import com.student.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Requirement 4: Search for students by email
    Optional<Student> findByEmailAddressIgnoreCase(String emailAddress);

    // Requirement 4: Search for students by course
    List<Student> findByCourseNameIgnoreCase(String courseName);

    // Requirement 6: Search students having a specific technical skill
    @Query("SELECT DISTINCT s FROM Student s JOIN s.technicalSkills skill WHERE LOWER(skill) = LOWER(:skill)")
    List<Student> findBySkill(@Param("skill") String skill);

    // Requirement 7: Students who need improvement in programming (< 50) and math >= 75
    List<Student> findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(double programmingScore, double mathematicsScore);
}
