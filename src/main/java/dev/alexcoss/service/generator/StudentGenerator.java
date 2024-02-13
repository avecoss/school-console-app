package dev.alexcoss.service.generator;

import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.util.FileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class StudentGenerator {
    private static final String FIRST_NAMES_PATH = "src/main/resources/data/first_names.txt";
    private static final String LAST_NAMES_PATH = "src/main/resources/data/last_names.txt";

    private final List<String> firstNamesList;
    private final List<String> lastNamesList;

    @Value("${data.students.count}")
    private int studentsCount;

    public StudentGenerator() {
        this.firstNamesList = readList(FIRST_NAMES_PATH);
        this.lastNamesList = readList(LAST_NAMES_PATH);
    }

    public List<StudentDTO> generateStudents() {
        List<StudentDTO> students = new ArrayList<>();
        for (int i = 0; i < studentsCount; i++) {
            students.add(generateStudent());
        }
        return students;
    }

    private StudentDTO generateStudent() {
        String firstName = getRandomField(firstNamesList);
        String lastName = getRandomField(lastNamesList);

        StudentDTO student = new StudentDTO();
        student.setFirstName(firstName);
        student.setLastName(lastName);

        return student;
    }

    private String getRandomField(List<String> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size() - 1));
    }

    private List<String> readList(String path) {
        FileReader reader = new FileReader();
        return reader.fileRead(path, bufferedReader -> {
            List<String> list = new ArrayList<>();
            bufferedReader.lines().forEach(list::add);
            return list;
        });
    }
}
