package controller;

import dao.CourseDAO;
import dao.GroupDAO;
import dao.StudentDAO;
import dao.сonnection.ConnectionFactory;
import exceptions.SchoolDAOException;
import model.Course;
import model.Group;
import model.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dao.utils.SQLUtils.fromTransaction;
import static dao.utils.SQLUtils.inTransaction;

public class SchoolManager {
    private GroupDAO groupDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    private final ConnectionFactory factory;

    public SchoolManager(GroupDAO groupDAO, StudentDAO studentDAO, CourseDAO courseDAO, ConnectionFactory factory) {
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.factory = factory;
    }

    public void createGroups(List<Group> groups) throws SchoolDAOException {
        inTransaction(factory, connection -> groupDAO.createGroups(connection, groups));
    }


    public Map<Group, Integer> getGroupsByStudentCount(int studentCount) throws SchoolDAOException {
        return fromTransaction(factory, connection -> groupDAO.getGroupsByStudentCount(connection, studentCount));
    }

    public List<Group> getGroups() throws SchoolDAOException {
        return fromTransaction(factory, connection -> groupDAO.findAll(connection));
    }

    public void createCourses(List<Course> courses) throws SchoolDAOException {
        inTransaction(factory, connection -> courseDAO.createCourses(connection, courses));
    }

    public List<Course> getCourses() throws SchoolDAOException {
        return fromTransaction(factory, connection -> courseDAO.findAll(connection));
    }

    public List<Course> getCoursesByStudentId(int studentId) throws SchoolDAOException {
        return fromTransaction(factory, connection -> courseDAO.getCoursesByStudentId(connection, studentId));
    }

    public void createStudents(List<Student> students) throws SchoolDAOException {
        inTransaction(factory, connection -> studentDAO.createStudents(connection, students));
    }

    public List<Student> getStudentsByCourseName(String courseName) throws SchoolDAOException {
        return fromTransaction(factory, connection -> studentDAO.getStudentsByCourseName(connection, courseName));
    }

    public void assignStudentsToCourse(int studentId, int courseId) throws SchoolDAOException {
        inTransaction(factory, connection -> studentDAO.assignStudentsToCourse(connection, studentId, courseId));
    }

    public void assignStudentsToCourse(List<Student> students) throws SchoolDAOException {
        inTransaction(factory, connection -> studentDAO.assignStudentsToCourse(connection, students));
    }

    public void deleteStudentFromCourse(int studentId, int courseId) throws SchoolDAOException {
        inTransaction(factory, connection -> studentDAO.deleteStudentFromCourse(connection, studentId, courseId));
    }

    public List<Student> getStudents() throws SchoolDAOException {
        return fromTransaction(factory, connection -> studentDAO.findAll(connection));
    }

    public boolean addNewStudent(Student student) throws SchoolDAOException {
        return fromTransaction(factory, connection -> studentDAO.create(connection, student)) != null;
    }

    public boolean deleteStudent(int studentId) {
        inTransaction(factory, connection -> studentDAO.deleteById(connection, studentId));
        return true;
    }

    public boolean addStudentToCourse(int studentId, int courseId) throws SchoolDAOException {
        return fromTransaction(factory, conn -> {
            List<Course> studentCourses = getCoursesByStudentId(studentId);
            if (!getCourseIdList(studentCourses).contains(courseId)) {
                assignStudentsToCourse(studentId, courseId);
                return true;
            } else {
                throw new SchoolDAOException("Student already on this course.");
            }
        });
    }

    public boolean removeStudentFromCourse(int studentId, int courseId) throws SchoolDAOException {
        return fromTransaction(factory, conn -> {
            List<Course> studentCourses = getCoursesByStudentId(studentId);
            if (getCourseIdList(studentCourses).contains(courseId)) {
                deleteStudentFromCourse(studentId, courseId);
                return true;
            } else {
                throw new SchoolDAOException("Student havn't this course.");
            }
        });
    }

    private List<Integer> getCourseIdList(List<Course> course) {
        return course.stream()
                .map(Course::getId)
                .collect(Collectors.toList());
    }
}
