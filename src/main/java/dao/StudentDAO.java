package dao;

import dao.impl.AbstractCrudDAOImpl;
import exceptions.SchoolDAOException;
import model.Student;

import java.sql.Connection;
import java.util.List;


public interface StudentDAO extends AbstractCrudDAOImpl<Student, Integer> {
    void createStudents(Connection con, List<Student> students) throws SchoolDAOException;

    List<Student> getStudentsByCourseName(Connection con, String courseName) throws SchoolDAOException;

    void assignStudentsToCourse(Connection con, int studentId, int courseId) throws SchoolDAOException;

    void deleteStudentFromCourse(Connection con, int studentId, int courseId) throws SchoolDAOException;

    void assignStudentsToCourse(Connection con, List<Student> students) throws SchoolDAOException;
}
