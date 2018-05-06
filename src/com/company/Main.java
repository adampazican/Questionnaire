package com.company;

import com.company.controllers.CategoryController;
import com.company.controllers.QuestionController;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //TODO: check params not null (db?)
        //TODO: Response codes enum
        Connection connection;
        Statement statement = null;
        ConfigReader cr = new ConfigReader();

        try{
            connection = DriverManager.getConnection(cr.getValue("DB_URL"), cr.getValue("USER"), cr.getValue("PASS"));
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT 1 FROM questions LIMIT 1;");
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

        new CRUDRouter(new QuestionController(statement), "questions");
        new CRUDRouter(new CategoryController(statement), "categories");
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

        String categoriesTable = "CREATE TABLE categories(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "PRIMARY KEY(id)" +
                ");";

        try {
            statement.executeQuery(questionsTable);
            statement.executeQuery(categoriesTable);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
