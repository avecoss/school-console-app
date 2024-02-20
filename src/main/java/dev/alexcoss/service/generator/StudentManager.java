package dev.alexcoss.service.generator;

import dev.alexcoss.dto.GroupDTO;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.service.GroupService;
import dev.alexcoss.service.StudentService;
import dev.alexcoss.service.generator.rangomizer.GroupRandomizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class StudentManager {
    private final StudentService studentService;
    private final GroupRandomizer groupRandomizer;
    private final StudentGenerator studentGenerator;
    private final GroupService groupService;

    public void generateAndSaveStudentsToDatabase() {
        List<StudentDTO> students = studentGenerator.generateStudents();
        log.info("Generated {} students", students.size());

        List<GroupDTO> groupsFromDatabase = groupService.getGroups();
        List<StudentDTO> studentsInGroups = groupRandomizer.assignStudentsToGroups(students, groupsFromDatabase);

        studentService.addStudents(studentsInGroups);
        log.info("Students generated and saved to the database");
    }
}
