package dev.alexcoss.service;

import dev.alexcoss.dao.JPAStudentDao;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import dev.alexcoss.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final JPAStudentDao jpaStudentDao;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public Optional<StudentDTO> getStudentById(int id) {
        return jpaStudentDao.findItemById(id)
            .map(student -> {
                log.info("Found student with ID {}: {}", id, student);
                return modelMapper.map(student, StudentDTO.class);
            });
    }

    public List<StudentDTO> getStudentsByCourse(String courseName) {
        List<Student> students = jpaStudentDao.findStudentsByCourse(courseName);
        List<StudentDTO> studentDTOList = getStudentDTOList(students);
        log.info("Found {} students for course: {}", studentDTOList.size(), courseName);

        return studentDTOList;
    }

    public List<StudentDTO> getStudents() {
        List<Student> students = jpaStudentDao.findAllItems();
        List<StudentDTO> studentDTOList = getStudentDTOList(students);
        log.info("Found {} students", studentDTOList.size());

        return studentDTOList;
    }

    @Transactional
    public void addStudents(List<StudentDTO> studentList) {
        if (isValidStudentList(studentList)) {
            List<Student> students = studentList.stream()
                .map(studentDTO -> modelMapper.map(studentDTO, Student.class))
                .toList();

            studentRepository.saveAllAndFlush(students);
            log.info("Added {} students to the repository", studentList.size());
        }
    }

    public void addStudent(StudentDTO student) {
        if (isValidStudent(student)) {
            jpaStudentDao.saveItem(modelMapper.map(student, Student.class));
            log.info("Added student to the repository: {}", student);
        } else {
            log.error("Invalid student data: First name or last name is empty");
        }
    }

    public void removeStudentById(int studentId) {
        Optional<StudentDTO> existingStudent = getStudentById(studentId);

        if (existingStudent.isPresent()) {
            jpaStudentDao.deleteItemById(studentId);
            log.info("Removed student with ID {}", studentId);
        } else {
            throw new NoSuchElementException("Student with ID " + studentId + " not found");
        }
    }

    private List<StudentDTO> getStudentDTOList(List<Student> students) {
        return students.stream()
            .map(student -> modelMapper.map(student, StudentDTO.class))
            .toList();
    }

    private boolean isValidStudent(StudentDTO student) {
        return student != null && student.getFirstName() != null && student.getLastName() != null;
    }

    private boolean isValidStudentList(List<StudentDTO> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            log.error("Student list is null or empty");
            return false;
        }

        for (StudentDTO student : studentList) {
            if (!isValidStudent(student)) {
                log.error("Invalid student in the list");
                return false;
            }
        }
        return true;
    }
}