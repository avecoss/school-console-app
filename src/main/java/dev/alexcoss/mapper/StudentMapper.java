package dev.alexcoss.mapper;

import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StudentDTO mapToDTO(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

    public Student mapToEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }

    public List<StudentDTO> mapToDTOList(List<Student> studentList) {
        return studentList.stream()
            .map(student -> modelMapper.map(student, StudentDTO.class))
            .collect(Collectors.toList());
    }

    public List<Student> mapToEntityList(List<StudentDTO> studentDTOList) {
        return studentDTOList.stream()
            .map(studentDTO -> modelMapper.map(studentDTO, Student.class))
            .collect(Collectors.toList());
    }
}
