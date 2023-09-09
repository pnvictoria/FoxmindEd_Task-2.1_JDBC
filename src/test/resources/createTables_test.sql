DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(5) NOT NULL);

DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students (
student_id SERIAL PRIMARY KEY,
group_id INT,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
FOREIGN KEY (group_id) REFERENCES groups(group_id));

DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses (
course_id SERIAL PRIMARY KEY,
course_name VARCHAR(50) NOT NULL,
course_description VARCHAR(255) NOT NULL);

DROP TABLE IF EXISTS students_courses;
CREATE TABLE IF NOT EXISTS students_courses (
student_id INT NOT NULL, 
course_id INT NOT NULL,
CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students(student_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses(course_id ) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT student_course UNIQUE (student_id, course_id));