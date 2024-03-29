package dev.alexcoss.repository;

import dev.alexcoss.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Override
    <S extends Student> List<S> saveAllAndFlush(Iterable<S> entities);
}
