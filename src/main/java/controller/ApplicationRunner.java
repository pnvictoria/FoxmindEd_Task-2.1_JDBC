package controller;

import dao.CourseDAO;
import dao.GroupDAO;
import dao.StudentDAO;
import dao.impl.CourseDAOImpl;
import dao.impl.GroupDAOImpl;
import dao.impl.StudentDAOImpl;
import dao.сonnection.ConnectionFactory;
import exceptions.SchoolDAOException;
import model.Course;
import model.Group;
import model.Student;
import view.ConsoleIO;
import view.UserInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static constant.QueryConstants.POSTGRES_PROPERTIES;

public class ApplicationRunner {
    public void runApp() throws SQLException {
        DataGenerator generator = new DataGenerator();
        List<Student> students = generator.getStudents();
        List<Group> groups = generator.getGroups();
        List<Course> courses = generator.getCourses();
        generator.relateStudentsToGroups(students, groups);
        generator.relateStudentsToCourses(students, courses);

        PropertyReader propReader = new PropertyReader();
        Properties properties = propReader.getProperties(POSTGRES_PROPERTIES);
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);

        GroupDAO groupDAO = new GroupDAOImpl();
        StudentDAO studentDAO = new StudentDAOImpl();
        CourseDAO courseDAO = new CourseDAOImpl();

        SchoolManager manager = new SchoolManager(groupDAO, studentDAO, courseDAO, connectionFactory);
        ConsoleIO consoleIO = new ConsoleIO();
        UserInterface userInterface = new UserInterface(manager, consoleIO);

        try {
            manager.createGroups(groups);
            manager.createStudents(students);
            manager.createCourses(courses);
            manager.assignStudentsToCourse(students);
            userInterface.runMenu();
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
    }
}
