package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.dto.CourseDTO;
import dev.alexcoss.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends AbstractService {

    private CourseDao courseRepository;
    private CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseDao courseRepository, CourseMapper courseMapper) {
        super(CourseService.class.getName());
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> getCourses() {
        return courseMapper.mapToDTOList(courseRepository.getAllItems());
    }

    public void addCourses(List<CourseDTO> courseList) {
        if (isValidCourseList(courseList)) {
            courseRepository.addAllItems(courseMapper.mapToEntityList(courseList));
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
