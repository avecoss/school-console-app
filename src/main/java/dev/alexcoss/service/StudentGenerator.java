package dev.alexcoss.service;

import dev.alexcoss.model.Student;
import dev.alexcoss.util.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentGenerator {

    private static final String FIRST_NAMES_PATH = "src/main/resources/data/first_names.txt";
    private static final String LAST_NAMES_PATH = "src/main/resources/data/last_names.txt";
    private static final int STUDENTS_COUNT = 200;

    private final List<String> firstNamesList;
    private final List<String> lastNamesList;

    public StudentGenerator() {
        this.firstNamesList = readList(FIRST_NAMES_PATH);
        this.lastNamesList = readList(LAST_NAMES_PATH);
    }

    public List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            students.add(generateStudent());
        }
        return students;
    }

    private Student generateStudent() {
        String firstName = getRandomField(firstNamesList);
        String lastName = getRandomField(lastNamesList);

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);

        return student;
    }

    private String getRandomField(List<String> list){
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
