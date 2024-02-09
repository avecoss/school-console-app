package dev.alexcoss.service;

import dev.alexcoss.dao.StudentDao;
import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService extends AbstractService {

    private StudentDao studentRepository;
    private ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentDao studentRepository, ModelMapper modelMapper) {
        super(StudentService.class.getName());
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<StudentDTO> getStudentById(int id) {
        return studentRepository.getStudentById(id)
            .map(student -> modelMapper.map(student, StudentDTO.class));
    }

    public List<StudentDTO> getStudentsByCourse(String courseName) {
        List<Student> students = studentRepository.getStudentsByCourse(courseName);
        return getStudentDTOList(students);
    }

    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.getAllItems();
        return getStudentDTOList(students);
    }

    public void addStudents(List<StudentDTO> studentList) {
        if (isValidStudentList(studentList)) {
            List<Student> students = studentList.stream()
                .map(studentDTO -> modelMapper.map(studentDTO, Student.class))
                .toList();

            studentRepository.addAllItems(students);
        }
    }

    public void addStudent(StudentDTO student) {
        if (isValidStudent(student)) {
            studentRepository.addItem(modelMapper.map(student, Student.class));
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