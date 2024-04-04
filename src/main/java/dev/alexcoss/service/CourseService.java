package dev.alexcoss.service;

import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.model.Course;
import dev.alexcoss.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
            .map(course -> modelMapper.map(course, CourseDTO.class))
            .toList();
    }

    @Transactional
    public void addCourses(List<CourseDTO> courseList) {
        if (isValidCourseList(courseList)) {
            List<Course> courses = courseList.stream()
                .map(courseDTO -> modelMapper.map(courseDTO, Course.class))
                .toList();

            courseRepository.saveAllAndFlush(courses);
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
