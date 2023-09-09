package dao;

import dao.impl.AbstractCrudDAOImpl;
import exceptions.SchoolDAOException;
import model.Group;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface GroupDAO extends AbstractCrudDAOImpl<Group, Integer> {
    void createGroups(Connection con, List<Group> groups) throws SchoolDAOException;

    Map<Group, Integer> getGroupsByStudentCount(Connection con, int count) throws SchoolDAOException;
}
