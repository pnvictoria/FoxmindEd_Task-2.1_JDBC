package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptExecutor {
    public void execute(Connection connection, String script) {
        String scriptPath = getScriptPath(script);
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
             Statement statement = connection.createStatement();) {
            String line;
            StringBuilder query = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                query.append(line);
            }
            statement.execute(query.toString());
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String getScriptPath(String script) {
        String scriptPath = "";
        try {
            if (getClass().getClassLoader().getResource(script) == null) {
                throw new IOException("File \"" + script + "\" not found");
            }
            scriptPath = getClass().getClassLoader().getResource(script).getFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return scriptPath;
    }
}
