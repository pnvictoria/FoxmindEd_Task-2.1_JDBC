package dao;

import controller.DataGenerator;
import controller.PropertyReader;
import controller.SchoolManager;
import controller.ScriptExecutor;
import dao.impl.CourseDAOImpl;
import dao.impl.GroupDAOImpl;
import dao.impl.StudentDAOImpl;
import dao.сonnection.ConnectionFactory;
import exceptions.SchoolDAOException;
import model.Course;
import model.Group;
import model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static constant.QueryConstants.POSTGRES_PROPERTIES_TEST;

public abstract class BaseDaoTest {
    private static final Properties dbPropertiesForTesting = new PropertyReader().getProperties(POSTGRES_PROPERTIES_TEST);
    protected static final ScriptExecutor scriptExec = new ScriptExecutor();
    protected static SchoolManager manager;
    protected static Connection connection;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withReuse(true)
            .withDatabaseName(dbPropertiesForTesting.getProperty("db.name"))
            .withUsername(dbPropertiesForTesting.getProperty("db.user"))
            .withPassword(dbPropertiesForTesting.getProperty("db.password"));

    @BeforeAll
    public static void before() {
        Properties properties = new Properties();
        properties.setProperty("db.url", postgreSQLContainer.getJdbcUrl());
        properties.setProperty("db.user", postgreSQLContainer.getUsername());
        properties.setProperty("db.password", postgreSQLContainer.getPassword());

        try {
            connection = new ConnectionFactory(properties).getConnection();
            manager = new SchoolManager(new GroupDAOImpl(), new StudentDAOImpl(), new CourseDAOImpl(), new ConnectionFactory(properties));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        scriptExec.execute(connection,"createTables_test.sql");

        DataGenerator generator = new DataGenerator();
        List<Student> students = generator.getStudents();
        List<Group> groups = generator.getGroups();
        List<Course> courses = generator.getCourses();
        generator.relateStudentsToGroups(students, groups);
        generator.relateStudentsToCourses(students, courses);

        try {
            manager.createCourses(courses);
            manager.createGroups(groups);
            manager.createStudents(students);
            manager.assignStudentsToCourse(students);
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    public static void after() {
        scriptExec.execute(connection, "dropObjects_test.sql");
    }
}
