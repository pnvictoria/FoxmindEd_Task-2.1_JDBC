package dao.сonnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionConsumer {
    void consume(Connection connection) throws SQLException;
}
