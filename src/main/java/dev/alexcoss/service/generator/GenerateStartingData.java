package dev.alexcoss.service.generator;

import dev.alexcoss.dao.EmptyTableChecker;
import dev.alexcoss.dao.TableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateStartingData {
    private final GroupManager groupManager;
    private final CourseManager courseManager;
    private final StudentManager studentManager;
    private final StudentsCoursesManager studentsCoursesManager;
    private final TableValidator tableValidator;

    @Autowired
    public GenerateStartingData(GroupManager groupManager, CourseManager courseManager, StudentManager studentManager,
                                StudentsCoursesManager studentsCoursesManager, EmptyTableChecker tableValidator) {
        this.groupManager = groupManager;
        this.courseManager = courseManager;
        this.studentManager = studentManager;
        this.studentsCoursesManager = studentsCoursesManager;
        this.tableValidator = tableValidator;
    }

    public void generateDataForDatabase() {
        generateDataIfTableIsEmpty("groups", groupManager::generateAndSaveGroupsToDatabase);
        generateDataIfTableIsEmpty("courses", courseManager::generateAndSaveCoursesToDatabase);
        generateDataIfTableIsEmpty("students", studentManager::generateAndSaveStudentsToDatabase);
        generateDataIfTableIsEmpty("students_courses", studentsCoursesManager::assignStudentsToCoursesAndSave);
    }

    private void generateDataIfTableIsEmpty(String tableName, Runnable dataGenerationFunction) {
        if (tableValidator.isTableEmpty(tableName)) {
            dataGenerationFunction.run();
        }
    }
}
