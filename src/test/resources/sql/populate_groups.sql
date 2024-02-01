CREATE TABLE IF NOT EXISTS groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL
);

INSERT INTO groups (group_name) VALUES ('Test1');
INSERT INTO groups (group_name) VALUES ('Test2');
INSERT INTO groups (group_name) VALUES ('Test3');

