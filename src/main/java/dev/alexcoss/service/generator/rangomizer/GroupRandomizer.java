package dev.alexcoss.service.generator.rangomizer;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.dto.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupRandomizer extends Randomizer {
    private static final int MIN_STUDENTS_IN_GROUP = 10;
    private static final int MAX_STUDENTS_IN_GROUP = 30;

    public List<StudentDTO> assignStudentsToGroups(List<StudentDTO> students, List<GroupDTO> groups) {
        shuffleCollections(students, groups);

        List<StudentDTO> studentsInGroups = new ArrayList<>();
        distributeStudentsToGroups(students, groups, studentsInGroups);
        studentsInGroups.addAll(students);

        return studentsInGroups;
    }

    private void distributeStudentsToGroups(List<StudentDTO> students, List<GroupDTO> groups, List<StudentDTO> studentsInGroups) {
        for (GroupDTO group : groups) {
            int groupSize = getRandomInteger(MAX_STUDENTS_IN_GROUP, MIN_STUDENTS_IN_GROUP);
            addStudentsToGroup(students, studentsInGroups, groupSize, group);
        }
    }

    private void addStudentsToGroup(List<StudentDTO> students, List<StudentDTO> studentsInGroups, int groupSize, GroupDTO groupDTO) {
        for (int i = 0; i < groupSize && !students.isEmpty(); i++) {
            StudentDTO student = students.remove(0);
            student.setGroup(groupDTO);
            studentsInGroups.add(student);
        }
    }
}
