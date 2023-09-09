package dao.сonnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMapper<T> {
    T apply(Connection connection) throws SQLException;
}
