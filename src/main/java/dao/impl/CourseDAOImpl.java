package dao.impl;

import dao.CourseDAO;
import exceptions.SchoolDAOException;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constant.QueryConstants.*;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public void createCourses(Connection con, List<Course> courses) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(COURSE_ADD_OBJECT)) {
            for (Course course : courses) {
                pStatement.setString(1, course.getName());
                pStatement.setString(2, course.getDescription());
                pStatement.addBatch();
            }
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public List<Course> getCoursesByStudentId(Connection con, int id) throws SchoolDAOException {
        List<Course> result = new ArrayList<>();
        try (PreparedStatement pStatement = con.prepareStatement(COURSE_GET_BY_STUDENT_ID)) {
            pStatement.setInt(1, id);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(extractSingleObject(resultSet));
                }
            } catch (Exception e) {
                throw new SchoolDAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Course> findById(Connection con, Integer id) throws SchoolDAOException {
        try {
            PreparedStatement pStatement = con.prepareStatement(COURSE_GET_OBJECT_BY_ID);
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractSingleObject(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public List<Course> findAll(Connection con) throws SchoolDAOException {
        List<Course> result = new ArrayList<>();
        try {
            PreparedStatement pStatement = con.prepareStatement(COURSE_GET_ALL_OBJECTS);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                result.add(extractSingleObject(resultSet));
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
        return result;
    }

    @Override
    public void deleteById(Connection con, Integer id) throws SchoolDAOException {
        try {
            PreparedStatement pStatement = con.prepareStatement(COURSE_REMOVE_OBJECT);
            pStatement.setInt(1, id);
            pStatement.addBatch();
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public Course create(Connection con, Course entity) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(COURSE_ADD_OBJECT)) {
            pStatement.setString(1, entity.getName());
            pStatement.setString(2, entity.getDescription());
            pStatement.addBatch();
            pStatement.executeBatch();
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractSingleObject(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public Course update(Connection con, Course entity) throws SchoolDAOException {
        try {
            PreparedStatement pStatement = con.prepareStatement(COURSE_UPDATE_OBJECT);
            pStatement.setString(1, entity.getName());
            pStatement.setString(2, entity.getDescription());
            pStatement.setInt(3, entity.getId());
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractSingleObject(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    private Course extractSingleObject(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt(1));
        course.setName(resultSet.getString(2));
        course.setDescription(resultSet.getString(3));
        return course;
    }
}
