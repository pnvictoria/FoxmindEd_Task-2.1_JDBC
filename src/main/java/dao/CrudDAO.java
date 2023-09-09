package dao;

import exceptions.SchoolDAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T extends HasId<K>, K> {

    Optional<T> findById(Connection con, K id) throws SchoolDAOException;

    List<T> findAll(Connection con) throws SchoolDAOException;

    T save(Connection con, T entity) throws SchoolDAOException;

    void deleteById(Connection con, K id) throws SchoolDAOException;
}
