package dev.alexcoss.service;

import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService extends AbstractService {

    private StudentDao studentRepository;
    private StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentDao studentRepository, StudentMapper studentMapper) {
        super(StudentService.class.getName());
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Optional<StudentDTO> getStudentById(int id) {
        return studentRepository.getStudentById(id)
            .map(studentMapper::mapToDTO);
    }

    public List<StudentDTO> getStudentsByCourse(String courseName) {
        return studentMapper.mapToDTOList(studentRepository.getStudentsByCourse(courseName));
    }

    public List<StudentDTO> getStudents() {
        return studentMapper.mapToDTOList(studentRepository.getAllItems());
    }

    public void addStudents(List<StudentDTO> studentList) {
        if (isValidStudentList(studentList)) {
            studentRepository.addAllItems(studentMapper.mapToEntityList(studentList));
        }
    }

    public void addStudent(StudentDTO student) {
        if (isValidStudent(student)) {
            studentRepository.addItem(studentMapper.mapToEntity(student));
        } else {
            handleServiceException("Invalid student data: First name or last name is empty");
        }
    }

    public void removeStudentById(int studentId) {
        Optional<StudentDTO> existingStudent = getStudentById(studentId);

        if (existingStudent.isPresent()) {
            studentRepository.removeStudentById(studentId);
        } else {
            throw new NoSuchElementException("Student with ID " + studentId + " not found");
        }
    }

    private boolean isValidStudent(StudentDTO student) {
        return student != null && student.getFirstName() != null && student.getLastName() != null;
    }

    private boolean isValidStudentList(List<StudentDTO> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            handleServiceException("Student list is null or empty");
            return false;
        }

        for (StudentDTO student : studentList) {
            if (!isValidStudent(student)) {
                handleServiceException("Invalid student in the list");
                return false;
            }
        }
        return true;
    }
}