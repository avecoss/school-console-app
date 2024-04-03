package dev.alexcoss.service;

import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import dev.alexcoss.repository.StudentRepository;
import dev.alexcoss.util.exceptions.EntityNotExistException;
import dev.alexcoss.util.exceptions.IllegalStudentException;
import dev.alexcoss.util.exceptions.NullStudentListException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public Optional<StudentDTO> getStudentById(int id) {
        return studentRepository.findById(id)
            .map(student -> modelMapper.map(student, StudentDTO.class));

    }

    public List<StudentDTO> getStudentsByCourse(String courseName) {
        List<Student> students = studentRepository.findByCoursesName(courseName);
        return getStudentDTOList(students);
    }

    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
        return getStudentDTOList(students);
    }

    @Transactional
    public void addStudents(List<StudentDTO> studentList) {
        isValidStudentList(studentList);

        List<Student> students = studentList.stream()
            .map(studentDTO -> modelMapper.map(studentDTO, Student.class))
            .toList();

        studentRepository.saveAllAndFlush(students);
    }

    @Transactional
    public void addStudent(StudentDTO student) {
        isValidStudent(student);
        studentRepository.save(modelMapper.map(student, Student.class));
    }

    @Transactional
    public void removeStudentById(int studentId) {
        Optional<StudentDTO> existingStudent = getStudentById(studentId);

        if (existingStudent.isPresent()) {
            studentRepository.deleteById(studentId);
        } else {
            throw new EntityNotExistException("Student with ID " + studentId + " not found");
        }
    }

    private List<StudentDTO> getStudentDTOList(List<Student> students) {
        return students.stream()
            .map(student -> modelMapper.map(student, StudentDTO.class))
            .toList();
    }

    private void isValidStudent(StudentDTO student) {
        if (student != null && student.getFirstName() != null && student.getLastName() != null) {
            throw new IllegalStudentException("Invalid student data: First name or last name is empty");
        }
    }

    private void isValidStudentList(List<StudentDTO> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            throw new NullStudentListException("Student list is null or empty");
        }

        for (StudentDTO student : studentList) {
            isValidStudent(student);
        }
    }
}