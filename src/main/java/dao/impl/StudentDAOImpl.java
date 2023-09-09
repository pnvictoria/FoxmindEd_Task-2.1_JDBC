package dao.impl;

import dao.StudentDAO;
import exceptions.SchoolDAOException;
import model.Course;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constant.QueryConstants.*;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public Optional<Student> findById(Connection con, Integer id) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_GET_OBJECT_BY_ID)) {
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
    public List<Student> findAll(Connection con) throws SchoolDAOException {
        List<Student> result = new ArrayList<>();
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_GET_ALL_OBJECTS)) {
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
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_REMOVE_OBJECT)) {
            pStatement.setInt(1, id);
            pStatement.addBatch();
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public void createStudents(Connection con, List<Student> students) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_ADD_OBJECT)) {
            for (Student student : students) {
                if (student.getGroupId() != 0) {
                    pStatement.setInt(1, student.getGroupId());
                    pStatement.setString(2, student.getFirstName());
                    pStatement.setString(3, student.getLastName());
                    pStatement.execute();
                } else {
                    create(con, student);
                }
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public List<Student> getStudentsByCourseName(Connection con, String courseName) throws SchoolDAOException {
        List<Student> result = new ArrayList<>();
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_BY_COURSE_NAME)) {
            pStatement.setString(1, courseName);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(extractSingleObject(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
        return result;
    }

    @Override
    public void assignStudentsToCourse(Connection con, int studentId, int courseId) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_ASSIGN_TO_COURSE)) {
            pStatement.setInt(1, studentId);
            pStatement.setInt(2, courseId);
            pStatement.execute();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public void deleteStudentFromCourse(Connection con, int studentId, int courseId) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_DELETE_FROM_COURSE)) {
            pStatement.setInt(1, studentId);
            pStatement.setInt(2, courseId);
            pStatement.execute();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public void assignStudentsToCourse(Connection con, List<Student> students) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_ASSIGN_TO_COURSE)) {
            for (Student student : students) {
                for (Course course : student.getCourses()) {
                    pStatement.setInt(1, student.getId());
                    pStatement.setInt(2, course.getId());
                    pStatement.addBatch();
                }
            }
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public Student create(Connection con, Student entity) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_ADD_OBJECT)) {
            pStatement.setInt(1, entity.getGroupId());
            pStatement.setString(2, entity.getFirstName());
            pStatement.setString(3, entity.getLastName());
            pStatement.addBatch();
            pStatement.executeBatch();
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractSingleObject(resultSet);
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new SchoolDAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public Student update(Connection con, Student entity) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(STUDENT_UPDATE_OBJECT)) {
            pStatement.setInt(1, entity.getGroupId());
            pStatement.setString(2, entity.getFirstName());
            pStatement.setString(3, entity.getLastName());
            pStatement.setInt(4, entity.getId());
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractSingleObject(resultSet);
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new SchoolDAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    private Student extractSingleObject(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt(1));
        student.setGroupId(resultSet.getInt(2));
        student.setFirstName(resultSet.getString(3));
        student.setLastName(resultSet.getString(4));
        return student;
    }
}
