package dao.impl;

import dao.GroupDAO;
import exceptions.SchoolDAOException;
import model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static constant.QueryConstants.*;

public class GroupDAOImpl implements GroupDAO {
    @Override
    public Optional<Group> findById(Connection con, Integer id) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_GET_OBJECT_BY_ID)) {
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
    public List<Group> findAll(Connection con) throws SchoolDAOException {
        List<Group> result = new ArrayList<>();
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_GET_ALL_OBJECTS)) {
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
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_REMOVE_OBJECT)) {
            pStatement.setInt(1, id);
            pStatement.addBatch();
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public void createGroups(Connection con, List<Group> groups) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_ADD_OBJECT)) {
            for (Group group : groups) {
                pStatement.setString(1, group.getName());
                pStatement.addBatch();
            }
            pStatement.executeBatch();
        } catch (SQLException e) {
            throw new SchoolDAOException(e.getMessage());
        }
    }

    @Override
    public Map<Group, Integer> getGroupsByStudentCount(Connection con, int count) throws SchoolDAOException {
        Map<Group, Integer> result = new HashMap<>();
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_GET_BY_STUDENT_COUNT)) {
            pStatement.setInt(1, count);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.put(extractSingleObject(resultSet), resultSet.getInt(3));
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
    public Group create(Connection con, Group entity) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_ADD_OBJECT)) {
            pStatement.setString(1, entity.getName());
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
    public Group update(Connection con, Group entity) throws SchoolDAOException {
        try (PreparedStatement pStatement = con.prepareStatement(GROUP_UPDATE_OBJECT)) {
            pStatement.setString(1, entity.getName());
            pStatement.setInt(3, entity.getId());
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

    private Group extractSingleObject(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt(1));
        group.setName(resultSet.getString(2));
        return group;
    }
}
