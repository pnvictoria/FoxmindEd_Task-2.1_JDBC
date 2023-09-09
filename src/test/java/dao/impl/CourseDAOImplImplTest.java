package dao.impl;

import dao.BaseDaoTest;
import exceptions.SchoolDAOException;
import model.Course;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class CourseDAOImplImplTest extends BaseDaoTest {
    @Test
    final void getByStudentId_returnCourseListByStudentId() {
        List<Course> actual = null;
        try {
            actual = manager.getCoursesByStudentId(10);
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        
        List<String> expected = new ArrayList<>();
        expected.add("Mathematics");
        expected.add("Physics");
        expected.add("Biology");
        expected.add("Chemistry");
        expected.add("Business");
        expected.add("Geography");
        expected.add("Astronomy");
        expected.add("Political");
        expected.add("History");
        expected.add("Literature");
        
        assertTrue(expected.contains(actual.get(0).getName()));
    }

    @Test
    final void getByStudentIdd_returnEmptyListIfInputIsNotStudentId() throws SchoolDAOException {
        List<Course> actual = manager.getCoursesByStudentId(-10);
        assertTrue(actual.isEmpty());
    }
}
