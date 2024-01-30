CREATE TABLE IF NOT EXISTS groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT          REFERENCES groups (group_id) ON DELETE SET NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(100) NOT NULL,
    course_description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INT REFERENCES students (student_id) ON DELETE CASCADE ,
    course_id  INT REFERENCES courses (course_id) ON DELETE CASCADE ,
    PRIMARY KEY (student_id, course_id)
);