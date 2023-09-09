package dao.utils;

import dao.сonnection.ConnectionConsumer;
import dao.сonnection.ConnectionFactory;
import dao.сonnection.ConnectionMapper;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLUtils {
    public static <T> T fromTransaction(ConnectionFactory factory, ConnectionMapper<T> mapper) {
        try (Connection con = factory.getConnection()) {
            try {
                con.setAutoCommit(false);
                T result = mapper.apply(con);
                con.commit();
                return result;
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e); // u can use custom exception here
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void inTransaction(ConnectionFactory factory, ConnectionConsumer consumer) {
        try (Connection con = factory.getConnection()) {
            try {
                con.setAutoCommit(false);
                consumer.consume(con);
                con.commit();
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e); // u can use custom exception here
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
