package dev.alexcoss.service.generator;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;
import dev.alexcoss.service.GroupService;
import dev.alexcoss.service.StudentService;
import dev.alexcoss.service.generator.rangomizer.GroupRandomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentManager {
    private final StudentService studentService;
    private final GroupRandomizer groupRandomizer;
    private final StudentGenerator studentGenerator;
    private final GroupService groupService;

    public StudentManager(StudentService studentService, GroupRandomizer groupRandomizer,
                          StudentGenerator studentGenerator, GroupService groupService) {
        this.studentService = studentService;
        this.groupRandomizer = groupRandomizer;
        this.studentGenerator = studentGenerator;
        this.groupService = groupService;
    }

    public void generateAndSaveStudentsToDatabase() {
        List<StudentDTO> students = studentGenerator.generateStudents();
        List<GroupDTO> groupsFromDatabase = groupService.getGroups();
        List<StudentDTO> studentsInGroups = groupRandomizer.assignStudentsToGroups(students, groupsFromDatabase);
        studentService.addStudents(studentsInGroups);
    }
}
