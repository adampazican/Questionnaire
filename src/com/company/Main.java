package com.company;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://localhost/test";

    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select 1 from questions limit 1;");
            //statement.executeUpdate(sql);
            System.out.println("connected");
        }
        catch (SQLSyntaxErrorException e){
            Main.setup(statement);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        /*finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {

            }
        }*/





        new QuestionController(new QuestionHandler(statement));
    }

    public static void setup(Statement statement){
        String questionsTable = "CREATE TABLE questions(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(255)," +
                "categoryId INT," +
                "answer1 VARCHAR(255)," +
                "answer2 VARCHAR(255)," +
                "answer3 VARCHAR(255)," +
                "realAnswer VARCHAR(255)," +
                "PRIMARY KEY(id)" +
                ");";
        try {
            statement.executeQuery(questionsTable);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
