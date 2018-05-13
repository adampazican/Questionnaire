package sk.pazican.adam;

import sk.pazican.adam.controllers.CategoryController;
import sk.pazican.adam.controllers.QuestionController;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

public class Main {
    public static void main(String[] args) {
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


        new CRUDRouter(new QuestionController(statement), "questions");
        new CRUDRouter(new CategoryController(statement), "categories");
    }

    public static void setup(Statement statement){
        String questionsTable = "CREATE TABLE questions(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "categoryId INT NOT NULL," +
                "answer1 VARCHAR(255) NOT NULL," +
                "answer2 VARCHAR(255) NOT NULL," +
                "answer3 VARCHAR(255) NOT NULL," +
                "realAnswer VARCHAR(255) NOT NULL," +
                "PRIMARY KEY(id)" +
                ");";

        String categoriesTable = "CREATE TABLE categories(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
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
