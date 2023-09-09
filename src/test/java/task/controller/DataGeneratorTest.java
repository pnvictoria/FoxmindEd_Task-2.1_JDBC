package task.controller;

import controller.DataGenerator;
import model.Course;
import model.Group;
import model.Student;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {
    private final DataGenerator generator = new DataGenerator();
    @Test
    public void getStudentsShouldReturnStudentList() {
        List<Student> actual = generator.getStudents();
        assertEquals(200, actual.size());
        
        Set<String> names = new HashSet<>();
        Set<String> lastNames = new HashSet<>();
        for (Student student : actual) {
            names.add(student.getFirstName());
            lastNames.add(student.getLastName());
        }
        assertTrue(names.contains("Roberto"));
        assertTrue(lastNames.contains("Linkoln"));
    }

    @Test
    public void getCoursesShouldReturnCourseList() {
        List<Course> actual = generator.getCourses();
        assertEquals(10, actual.size());
        
        Set<String> courses = new HashSet<>();
        Set<String> descriptions = new HashSet<>();
        for (Course course : actual) {
            courses.add(course.getName());
            descriptions.add(course.getDescription());
        }
        assertTrue(courses.contains("Biology"));
        assertTrue(descriptions.contains("Learn how to count apples"));
    }

    @Test
    public void getGroupsShouldReturnGroupList() {
        List<Group> actual = generator.getGroups();
        assertEquals(10, actual.size());
        
        String groupName = actual.get(0).getName();
        assertEquals(5, groupName.length());
        assertTrue(groupName.contains("-"));
    }

}
