package dev.alexcoss.repository;

import dev.alexcoss.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Override
    <S extends Group> List<S> saveAllAndFlush(Iterable<S> entities);
}
