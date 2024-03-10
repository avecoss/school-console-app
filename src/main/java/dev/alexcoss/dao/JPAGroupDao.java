package dev.alexcoss.dao;

import dev.alexcoss.model.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class JPAGroupDao implements GroupDao<Group, List<Group>> {

    private static final String SELECT_ALL_HQL = "SELECT g FROM Group g";
    private static final String SELECT_ALL_WITH_STUDENTS_HQL = """
        SELECT g, COUNT(s)
        FROM Group g
        JOIN g.students s
        GROUP BY g
        ORDER BY COUNT(s)
        """;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveItem(Group group) {
        try {
            entityManager.persist(group);

            log.info("Adding group to database: {}", group);
        } catch (DataAccessException e) {
            log.error("Error adding group to database \nParameters: {}", group, e);
        }
    }

    @Override
    @Transactional
    public void saveAllItems(List<Group> groupList) {
        try {
            for (Group group : groupList) {
                entityManager.persist(group);
            }
            log.info("Adding groups to database: {}", groupList);
        } catch (DataAccessException e) {
            log.error("Error adding groups to database \nParameters: {}", groupList, e);
        }
    }

    @Override
    public List<Group> findAllItems() {
        try {
            List<Group> groups = entityManager.createQuery(SELECT_ALL_HQL, Group.class).getResultList();

            log.info("Getting groups from database: {}", groups);
            return groups;
        } catch (DataAccessException e) {
            log.error("Error getting groups from database. \nHQL: {}", SELECT_ALL_HQL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Map<Group, Integer> findAllGroupsWithStudents() {
        try {
            List<Object[]> results = entityManager.createQuery(SELECT_ALL_WITH_STUDENTS_HQL, Object[].class).getResultList();

            Map<Group, Integer> groupsWithStudents = results.stream()
                .collect(Collectors.toMap(
                    result -> (Group) result[0],
                    result -> ((Long) result[1]).intValue()
                ));

            log.info("Getting all groups with students from database: {}", groupsWithStudents);
            return groupsWithStudents;
        } catch (DataAccessException e) {
            log.error("Error getting all groups with students from database. \nSQL: {}", SELECT_ALL_WITH_STUDENTS_HQL, e);
            return Collections.emptyMap();
        }
    }
}