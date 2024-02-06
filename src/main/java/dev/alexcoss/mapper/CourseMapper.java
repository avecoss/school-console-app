package dev.alexcoss.mapper;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO mapToDTO(Course course);

    Course mapToEntity(CourseDTO courseDTO);

    List<CourseDTO> mapToDTOList(List<Course> courseList);

    List<Course> mapToEntityList(List<CourseDTO> courseDTOList);
}
