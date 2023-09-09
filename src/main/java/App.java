import dao.сonnection.ConnectionFactory;
import controller.ApplicationRunner;
import controller.PropertyReader;
import controller.ScriptExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static constant.QueryConstants.POSTGRES_PROPERTIES;

public class App {
    private static final String CREATE_TABLES = "createTables.sql";

    public static void main(String[] args) {
        ScriptExecutor scriptExec = new ScriptExecutor();
        PropertyReader propReader = new PropertyReader();
        Properties properties = propReader.getProperties(POSTGRES_PROPERTIES);
        Connection connection = null;
        try {
            connection = new ConnectionFactory(properties).getConnection();
            scriptExec.execute(connection, CREATE_TABLES);
            ApplicationRunner runner = new ApplicationRunner();
            runner.runApp();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
