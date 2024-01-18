package dev.alexcoss.service;

import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.util.*;

public class GroupRandomizer extends Randomizer {

    private static final int MIN_STUDENTS_IN_GROUP = 10;
    private static final int MAX_STUDENTS_IN_GROUP = 30;

    public List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        shuffleCollections(students, groups);

        List<Student> studentsInGroups = new ArrayList<>();

        distributeStudentsToGroups(students, groups, studentsInGroups);
        studentsInGroups.addAll(students);

        return studentsInGroups;
    }

    private void distributeStudentsToGroups(List<Student> students, List<Group> groups, List<Student> studentsInGroups) {
        for (Group group : groups) {
            int groupSize = getRandomInteger(MAX_STUDENTS_IN_GROUP, MIN_STUDENTS_IN_GROUP);
            addStudentsToGroup(students, studentsInGroups, groupSize, group.getId());
        }
    }

    private void addStudentsToGroup(List<Student> students, List<Student> studentsInGroups, int groupSize, int groupId) {
        for (int i = 0; i < groupSize && !students.isEmpty(); i++) {
            Student student = students.remove(0);
            student.setGroupId(groupId);
            studentsInGroups.add(student);
        }
    }
}
