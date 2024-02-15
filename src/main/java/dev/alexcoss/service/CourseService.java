package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseDao courseRepository;
    private final ModelMapper modelMapper;

    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.getAllItems();
        log.info("Getting all courses from the database");

        List<CourseDTO> courseDTOList = courses.stream()
            .map(course -> modelMapper.map(course, CourseDTO.class))
            .toList();

        log.info("Retrieved {} courses from the database", courseDTOList.size());
        return courseDTOList;
    }

    public void addCourses(List<CourseDTO> courseList) {
        if (isValidCourseList(courseList)) {
            List<Course> courses = courseList.stream()
                .map(courseDTO -> modelMapper.map(courseDTO, Course.class))
                .toList();

            courseRepository.addAllItems(courses);
            log.info("Added {} courses to the database", courses.size());
        }
    }

    private boolean isValidCourseList(List<CourseDTO> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            log.error("Course list is null or empty");
            return false;
        }

        for (CourseDTO course : courseList) {
            if (course == null) {
                log.error("Invalid course in the list");
                return false;
            }
        }
        return true;
    }
}
