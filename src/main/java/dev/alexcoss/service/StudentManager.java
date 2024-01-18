package dev.alexcoss.service;

import dev.alexcoss.dao.GroupDao;
import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.util.List;

public class StudentManager {
    private final StudentDao studentDao;
    private final GroupRandomizer groupRandomizer;
    private final StudentGenerator studentGenerator;
    private final GroupDao groupDao;

    public StudentManager(StudentDao studentDao, GroupRandomizer groupRandomizer,
                          StudentGenerator studentGenerator, GroupDao groupDao) {

        this.studentDao = studentDao;
        this.groupRandomizer = groupRandomizer;
        this.studentGenerator = studentGenerator;
        this.groupDao = groupDao;
    }

    public void generateAndSaveStudentsToDatabase() {
        List<Student> students = generateStudents();
        List<Group> groupsFromDatabase = getGroupsFromDatabase();
        List<Student> studentsInGroups = assignStudentsToGroups(students, groupsFromDatabase);
        saveStudentsToDatabase(studentsInGroups);
    }

    private List<Student> generateStudents() {
        return studentGenerator.generateStudents();
    }

    private List<Group> getGroupsFromDatabase() {
        return groupDao.getAllItems();
    }

    private List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        return groupRandomizer.assignStudentsToGroups(students, groups);
    }

    private void saveStudentsToDatabase(List<Student> students) {
        studentDao.addAllItems(students);
    }
}
