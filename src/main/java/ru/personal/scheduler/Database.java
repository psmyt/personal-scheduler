package ru.personal.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final Connection connection;

    static {
        try {
            String dbUrl = "jdbc:derby:DerbyDB;create=true";
//            String dbUrl = "jdbc:derby:memory:demo;create=true";
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection connection() {
        return connection;
    }

    public static void normalDbUsage() throws SQLException {
        Statement statement = connection.createStatement();
        // drop table
        // stmt.executeUpdate("Drop Table users");

        // create table
        statement.executeUpdate("Create table users (id int primary key, name varchar(30))");

        // insert 2 rows
        statement.executeUpdate("insert into users values (1,'tom')");
        statement.executeUpdate("insert into users values (2,'peter')");

        // query
        ResultSet rs = statement.executeQuery("SELECT * FROM users");

        // print out query result
        while (rs.next()) {
            System.out.printf("%d\t%s\n", rs.getInt("id"), rs.getString("name"));
        }
    }

    public static String executeSqlQuery(CharSequence sql) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.valueOf(sql));
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
