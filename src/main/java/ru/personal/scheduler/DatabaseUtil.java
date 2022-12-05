package ru.personal.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final Connection connection;

    static {
        try {
            String dbUrl = "jdbc:derby:DerbyDB;create=true";
            connection = DriverManager.getConnection(dbUrl);
            System.out.println("установлено соединение");
//            checkTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection connection() {
        return connection;
    }

    private static void checkTables() throws SQLException {
        connection.createStatement().execute(
                ""
        );
    }

    public static String executeSqlQuery(CharSequence sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(String.valueOf(sql));
            ResultSet resultSet = statement.getResultSet();
            int columns = resultSet.getMetaData().getColumnCount();
            StringBuilder stringBuilder =new StringBuilder();
            while(resultSet.next()){
                for (int i = 1; i <= columns; i++){
                    stringBuilder.append(resultSet.getString(i)).append("\n");
                }
            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
