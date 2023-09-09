package constant;

public class QueryConstants {
    public static final String POSTGRES_PROPERTIES = "db.properties";
    public static final String POSTGRES_PROPERTIES_TEST = "db_test.properties";

    public static final String COURSE_ADD_OBJECT = "INSERT INTO courses (course_name, course_description) VALUES (?, ?);";
    public static final String COURSE_REMOVE_OBJECT = "DELETE FROM courses WHERE course_id = ?;";
    public static final String COURSE_UPDATE_OBJECT = "UPDATE courses SET course_name=?, course_description=? WHERE course_id = ?;";
    public static final String COURSE_GET_OBJECT_BY_ID = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?;";
    public static final String COURSE_GET_ALL_OBJECTS = "SELECT course_id, course_name, course_description FROM courses;";
    public static final String COURSE_GET_BY_STUDENT_ID = "SELECT c.course_id, c.course_name, course_description FROM students_courses sc JOIN courses c USING (course_id) JOIN students s USING (student_id) WHERE sc.student_id = ?;";

    public static final String GROUP_ADD_OBJECT = "INSERT INTO groups (group_name) VALUES (?);";
    public static final String GROUP_REMOVE_OBJECT = "DELETE FROM groups WHERE group_id = ?;";
    public static final String GROUP_UPDATE_OBJECT = "UPDATE groups SET group_name = ? WHERE group_id = ?;";
    public static final String GROUP_GET_OBJECT_BY_ID = "SELECT group_id, group_name FROM groups WHERE group_id = ?;";
    public static final String GROUP_GET_ALL_OBJECTS = "SELECT group_id, group_name FROM groups;";
    public static final String GROUP_GET_BY_STUDENT_COUNT = "SELECT g.group_id, g.group_name,COUNT(s.group_id) AS students FROM groups g JOIN students s USING (group_id) GROUP BY g.group_id, g.group_name HAVING COUNT(*) <= ?;";

    public static final String STUDENT_ADD_OBJECT = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?);";
    public static final String STUDENT_REMOVE_OBJECT = "DELETE FROM students WHERE student_id = ?;";
    public static final String STUDENT_UPDATE_OBJECT = "UPDATE students SET group_id=?, first_name=?, last_name=? WHERE student_id = ?;";
    public static final String STUDENT_GET_OBJECT_BY_ID = "SELECT student_id, group_id, first_name, last_name FROM students WHERE student_id = ?;";
    public static final String STUDENT_GET_ALL_OBJECTS = "SELECT student_id, group_id, first_name, last_name FROM students;";
    public static final String STUDENT_BY_COURSE_NAME = "SELECT s.student_id, s.group_id, s.first_name, s.last_name, c.course_name FROM students_courses JOIN courses c USING (course_id) JOIN students s USING (student_id) WHERE c.course_name = ?;";
    public static final String STUDENT_ASSIGN_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?);";
    public static final String STUDENT_DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?;";
}
