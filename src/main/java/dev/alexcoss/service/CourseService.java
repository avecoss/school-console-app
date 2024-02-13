package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends AbstractService {

    private CourseDao courseRepository;
    private ModelMapper modelMapper;

    public CourseService(CourseDao courseRepository, ModelMapper modelMapper) {
        super(CourseService.class.getName());
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.getAllItems();
        return courses.stream()
            .map(course -> modelMapper.map(course, CourseDTO.class))
            .toList();
    }

    public void addCourses(List<CourseDTO> courseList) {
        if (isValidCourseList(courseList)) {
            List<Course> courses = courseList.stream()
                .map(courseDTO -> modelMapper.map(courseDTO, Course.class))
                .toList();

            courseRepository.addAllItems(courses);
        }
    }

    private boolean isValidCourseList(List<CourseDTO> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            handleServiceException("Course list is null or empty");
            return false;
        }

        for (CourseDTO course : courseList) {
            if (course == null) {
                handleServiceException("Invalid course in the list");
                return false;
            }
        }
        return true;
    }
}
