CREATE TABLE IF NOT EXISTS courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(100) NOT NULL,
    course_description VARCHAR(100)
);

INSERT INTO courses (course_name, course_description) VALUES ('name_1', 'description');
INSERT INTO courses (course_name, course_description) VALUES ('name_2', 'description');
INSERT INTO courses (course_name, course_description) VALUES ('name_3', 'description');
