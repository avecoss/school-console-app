CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT          REFERENCES groups (group_id) ON DELETE SET NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL
);

INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'John', 'Doe');
INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'Jane', 'Doe');
INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Bob', 'Robinson');