package dev.alexcoss.mapper;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseDTO mapToDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public Course mapToEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }

    public List<CourseDTO> mapToDTOList(List<Course> courseList) {
        return courseList.stream()
            .map(course -> modelMapper.map(course, CourseDTO.class))
            .collect(Collectors.toList());
    }

    public List<Course> mapToEntityList(List<CourseDTO> courseDTOList) {
        return courseDTOList.stream()
            .map(courseDTO -> modelMapper.map(courseDTO, Course.class))
            .collect(Collectors.toList());
    }
}
