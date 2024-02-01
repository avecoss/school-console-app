CREATE TABLE IF NOT EXISTS courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(100) NOT NULL,
    course_description VARCHAR(100)
);