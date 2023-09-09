package dao.impl;

import controller.DataGenerator;
import controller.PropertyReader;
import controller.SchoolManager;
import controller.ScriptExecutor;
import dao.BaseDaoTest;
import dao.сonnection.ConnectionFactory;
import exceptions.SchoolDAOException;
import model.Course;
import model.Group;
import model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static constant.QueryConstants.POSTGRES_PROPERTIES_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class GroupDAOImplTest extends BaseDaoTest {
    @Test
    final void getByStudentCount_returnGroupWhenInputIsStudentCount() {
        Map<Group, Integer> actual = null;
        int input = 30;
        try {
            actual = manager.getGroupsByStudentCount(input);
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        
        assertTrue(actual.size() > 5);
        for (Map.Entry<Group, Integer> entry : actual.entrySet()) {
            assertTrue(input >= entry.getValue());
        }
    }
    
    @Test
    final void getByStudentCount_returnGroupWhenInputIsNotStudentCount() {
        Map<Group, Integer> actual = null;
        try {
            actual = manager.getGroupsByStudentCount(-100);
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0, actual.size());
    }
}
