package dao;

import dao.impl.AbstractCrudDAOImpl;
import exceptions.SchoolDAOException;
import model.Course;

import java.sql.Connection;
import java.util.List;

public interface CourseDAO extends AbstractCrudDAOImpl<Course, Integer> {
    void createCourses(Connection con, List<Course> courses) throws SchoolDAOException;

    List<Course> getCoursesByStudentId(Connection con, int id) throws SchoolDAOException;
}
