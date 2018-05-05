package com.company;

import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionController implements IController {
    private Statement statement;

    public QuestionController(Statement statement){
        this.statement = statement;
    }

    public List<ResponseObject> getAll(Request req, Response res){
        List<ResponseObject> questions = new ArrayList<>();
        String retrieveQuestions = "SELECT questions.title, questions.answer1, questions.answer2, questions.answer3, questions.realAnswer, categories.name " +
                "FROM questions " +
                "INNER JOIN categories " +
                "ON questions.categoryId = categories.id;";

        try{
            ResultSet rs = this.statement.executeQuery(retrieveQuestions);
            while(rs.next()){
                String title = rs.getString("title");
                String categoryName = rs.getString("name");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String realAnswer = rs.getString("realAnswer");
                questions.add(new Question(title, categoryName, answer1, answer2, answer3, realAnswer));
            }
        }
        catch (SQLException e){
            questions.add(new ResponseObject(500, "Internal server error"));
        }
        finally {
            return questions;
        }
    }

    public ResponseObject get(Request req, Response res){
        String id = req.params(":id");
        String retrieveQuestion = String.format("SELECT questions.title, questions.answer1, questions.answer2, questions.answer3, questions.realAnswer, categories.name " +
                "FROM questions " +
                "INNER JOIN categories " +
                "ON questions.categoryId = categories.id " +
                "WHERE questions.id=%s;", id);

        Question question = null;

        try {
            ResultSet rs = this.statement.executeQuery(retrieveQuestion);
            while(rs.next()){
                String title = rs.getString("title");
                String categoryName = rs.getString("name");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String realAnswer = rs.getString("realAnswer");
                question = new Question(title, categoryName, answer1, answer2, answer3, realAnswer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return new ResponseObject(500, "Internal server error");
        }


        return question == null ? new ResponseObject(404, "Question not found") : question;
    }

    public ResponseObject create(Request req, Response res){
        String title = req.headers("title");
        String categoryId = req.headers("categoryId");
        String answer1 = req.headers("answer1");
        String answer2 = req.headers("answer2");
        String answer3 = req.headers("answer3");
        String realAnswer = req.headers("realAnswer");

        // TODO: check params not null (db?)
        if(!this.categoryExists(categoryId)) {
            System.out.println("jo");
            return new ResponseObject(400, "Category doesn't exist");
        }

        String newQuestion = String.format("INSERT INTO questions (title, categoryId, answer1, answer2, answer3, realAnswer) VALUES" +
                "('%s','%s','%s','%s','%s','%s');", title, categoryId, answer1, answer2, answer3, realAnswer);

        try {
            this.statement.executeQuery(newQuestion);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return new Question(title, categoryId, answer1, answer2, answer3, realAnswer);
    }

    public boolean categoryExists(String id){
        String sql = String.format("select * from categories where id=%s;", id);

        try {
            ResultSet rs = this.statement.executeQuery(sql);
            if(rs.next()){
                return true;
            }
        }
        catch (SQLException e){
            return false;
        }

        return false;
    }

    public ResponseObject update(Request req, Response res){
        System.out.println("jo");
        String id = req.params(":id");
        ResponseObject question = this.get(req, res);
        List<String> paramList = Arrays.asList("title", "categoryId", "answer1", "answer2", "answer3", "realAnswer");


        if(!(question instanceof Question)){
            return new ResponseObject(400, "Bad request");
        }

        for(String param : paramList){
            String value = req.headers(param);
            if(value != null){
                String sql = String.format("UPDATE questions SET %s='%s' WHERE id=%s;", param, value, id);
                try {
                    this.statement.executeQuery(sql);
                    question = this.get(req, res);
                }
                catch (SQLException e){
                    e.printStackTrace();
                    return new ResponseObject(400, "Bad request");
                }
            }
        }

        return question;
    }

    public ResponseObject delete(Request req, Response res){
        String id = req.params(":id");
        String sql = String.format("DELETE FROM questions WHERE id=%s;", id);
        ResponseObject question = this.get(req, res);

        try {
            this.statement.executeQuery(sql);
        }
        catch (SQLException e){
            return new ResponseObject(400, "Bad request");
        }


        return question;
    }
}
