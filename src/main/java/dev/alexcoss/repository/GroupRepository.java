package dev.alexcoss.repository;

import dev.alexcoss.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Override
    <S extends Group> List<S> saveAllAndFlush(Iterable<S> entities);

    @Query("""
        SELECT g, COUNT(s)
        FROM Group g
        JOIN g.students s
        GROUP BY g
        ORDER BY COUNT(s)
           """)
    List<Object[]> findAllGroupsWithStudents();

}
