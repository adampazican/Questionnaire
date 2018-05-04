package com.company;

import spark.Request;

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
    //TODO: handler interface (getAll, create, update methods) for question and category handlers

    public List<ResponseObject> getAll(){
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

    public ResponseObject get(String id){
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

    public ResponseObject create(String title, String categoryId, String answer1, String answer2, String answer3, String realAnswer){
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

    public ResponseObject update(String id, Request req){
        ResponseObject question = this.get(id);
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
                }
                catch (SQLException e){
                    return new ResponseObject(400, "Bad request");
                }
            }
        }

        return new ResponseObject(404, "Not found");
    }

    public ResponseObject delete(String id){
        String sql = String.format("DELETE FROM questions WHERE id=%s;", id);
        ResponseObject question = this.get(id);

        try {
            this.statement.executeQuery(sql);
        }
        catch (SQLException e){
            return new ResponseObject(400, "Bad request");
        }


        return question;
    }
}
