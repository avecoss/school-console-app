package dev.alexcoss.service.generator;

import dev.alexcoss.dao.TableValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateStartingData {
    private final GroupManager groupManager;
    private final CourseManager courseManager;
    private final StudentManager studentManager;
    private final StudentsCoursesManager studentsCoursesManager;
    private final TableValidator tableValidator;

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
