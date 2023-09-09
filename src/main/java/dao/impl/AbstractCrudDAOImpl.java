package dao.impl;

import dao.CrudDAO;
import dao.HasId;
import exceptions.SchoolDAOException;

import java.sql.Connection;

public interface AbstractCrudDAOImpl<T extends HasId<K>, K> extends CrudDAO<T, K> {

    T create(Connection con, T entity) throws SchoolDAOException;

    T update(Connection con, T entity) throws SchoolDAOException;

    default T save(Connection con, T entity) throws SchoolDAOException {
        return entity.getId() == null ? create(con, entity) : update(con, entity);
    }
}
