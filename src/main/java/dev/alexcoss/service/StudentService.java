package dev.alexcoss.service;

import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends AbstractService {

    private StudentDao studentRepository;

    @Autowired
    public StudentService(StudentDao studentRepository) {
        super(StudentService.class.getName());
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudentById(int id) {
        try {
            return studentRepository.getStudentById(id);
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting student by id from database");
            return Optional.empty();
        }
    }

    public List<Student> getStudentsByCourse(String courseName) {
        try {
            return studentRepository.getStudentsByCourse(courseName);
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting students by course from database");
            return Collections.emptyList();
        }
    }

    public List<Student> getStudents() {
        try {
            return studentRepository.getAllItems();
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting students from database");
            return Collections.emptyList();
        }
    }

    public void addStudents(List<Student> studentList) {
        if (isValidStudentList(studentList)) {
            try {
                studentRepository.addAllItems(studentList);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding students to database");
            }
        }
    }

    public void addStudent(Student student) {
        if (isValidStudent(student)) {
            try {
                studentRepository.addItem(student);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding student to database");
            }
        } else {
            handleServiceException("Invalid student data: First name or last name is empty");
        }
    }

    public void removeStudentById(int studentId) {
        Optional<Student> existingStudent = getStudentById(studentId);

        if (existingStudent.isPresent()) {
            try {
                studentRepository.removeStudentById(studentId);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error removing student from database");
            }
        } else {
            handleServiceException("Student with ID " + studentId + " not found");
        }
    }

    private boolean isValidStudent(Student student) {
        return student != null && student.getFirstName() != null && student.getLastName() != null;
    }

    private boolean isValidStudentList(List<Student> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            handleServiceException("Student list is null or empty");
            return false;
        }

        for (Student student : studentList) {
            if (!isValidStudent(student)) {
                handleServiceException("Invalid student in the list");
                return false;
            }
        }
        return true;
    }
}