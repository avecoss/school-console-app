package dev.alexcoss.repository;

import dev.alexcoss.dto.GroupWithStudentCountDTO;
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
        SELECT new dev.alexcoss.dto.GroupWithStudentCountDTO(g, CAST(COUNT(s) AS int))
        FROM Group g
        JOIN g.students s
        GROUP BY g
        ORDER BY COUNT(s)
           """)
    List<GroupWithStudentCountDTO> findAllGroupsWithStudents();
}
