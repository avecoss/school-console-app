CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INT REFERENCES students (student_id) ON DELETE CASCADE ,
    course_id  INT REFERENCES courses (course_id) ON DELETE CASCADE ,
    PRIMARY KEY (student_id, course_id)
);