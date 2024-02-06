package dev.alexcoss.mapper;

import dev.alexcoss.dto.StudentDTO;
import dev.alexcoss.model.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO mapToDTO(Student student);

    Student mapToEntity(StudentDTO studentDTO);

    List<StudentDTO> mapToDTOList(List<Student> studentList);

    List<Student> mapToEntityList(List<StudentDTO> studentDTOList);

}
