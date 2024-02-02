package dev.alexcoss.service;

import dev.alexcoss.dao.CourseDao;
import dev.alexcoss.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseService extends AbstractService {

    private CourseDao courseRepository;

    @Autowired
    public CourseService(CourseDao courseRepository) {
        super(CourseService.class.getName());
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        try {
            return courseRepository.getAllItems();
        } catch (DataAccessException e) {
            handleServiceException(e, "Error getting courses from database");
            return Collections.emptyList();
        }
    }

    public void addCourses(List<Course> courseList) {
        if (isValidCourseList(courseList)) {
            try {
                courseRepository.addAllItems(courseList);
            } catch (DataAccessException e) {
                handleServiceException(e, "Error adding courses to database");
            }
        }
    }

    private boolean isValidCourseList(List<Course> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            handleServiceException("Course list is null or empty");
            return false;
        }

        for (Course course : courseList) {
            if (course == null) {
                handleServiceException("Invalid course in the list");
                return false;
            }
        }
        return true;
    }
}
