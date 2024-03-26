package dev.alexcoss.repository;

import dev.alexcoss.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Override
    <S extends Course> List<S> saveAllAndFlush(Iterable<S> entities);
}
