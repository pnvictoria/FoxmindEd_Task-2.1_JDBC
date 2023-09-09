package dao.impl;

import dao.BaseDaoTest;
import exceptions.SchoolDAOException;
import model.Student;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class StudentDAOImplTest extends BaseDaoTest {
    @Test
    final void testGetByCourseName() {
        List<Student> actual = null;
        try {
            actual = manager.getStudentsByCourseName("Biology");
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        List<String> expected = Arrays.asList("Andrew", "Robert", "Mike", "Diego", "Fred", "Lenny", "Stephan", "Milko", "Roberto",
            "Frank", "Kirk", "Kasper", "Kate", "Lili", "Damistas", "Lory", "Menny", "Brian", "Bondie", "Dyondi");
        
        for (Student s : actual) {
            assertTrue(expected.contains(s.getFirstName()));
        }
        assertTrue(actual.size() > 3);
    }

    @Test
    final void testGetByCourseNames() {
        List<Student> actual = null;
        try {
            actual = manager.getStudentsByCourseName("Biola");
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        
        assertTrue(actual.size() == 0);
    }
}
