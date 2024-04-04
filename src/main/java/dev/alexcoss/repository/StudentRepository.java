package dev.alexcoss.repository;

import dev.alexcoss.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Override
    <S extends Student> List<S> saveAllAndFlush(Iterable<S> entities);

    @Query("""
        SELECT s FROM Course c
        JOIN c.students s
        WHERE c.name = :courseName
        """)
    List<Student> findByCoursesName(@Param("courseName") String courseName);
}
