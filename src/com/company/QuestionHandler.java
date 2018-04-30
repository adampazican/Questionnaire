package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionHandler {
    private Statement statement;

    public QuestionHandler(Statement statement){
        this.statement = statement;
    }

    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        return questions;
    }

    public Question getQuestion(String id){
        String retrieveQuestion = String.format("SELECT * FROM questions WHERE id=%s;", id);

        Question question = null;

        try {
            ResultSet rs = this.statement.executeQuery(retrieveQuestion);
            while(rs.next()){
                String title = rs.getString("title");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String realAnswer = rs.getString("realAnswer");
                question = new Question(title, "",answer1, answer2, answer3, realAnswer);
            }
        }
        catch (SQLException e){

        }


        return question;
    }

    public Question createQuestion(String title, int categoryId, String answer1, String answer2, String answer3, String realAnswer){
        String newQuestion = String.format("INSERT INTO questions (title, categoryId, answer1, answer2, answer3, realAnswer) VALUES" +
                "('%s','%d','%s','%s','%s','%s');", title, categoryId, answer1, answer2, answer3, realAnswer);

        try {
            this.statement.executeQuery(newQuestion);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return new Question();
    }
    public Question updateQuestion(String id, String title, String category, String answer){
        return new Question();
    }
}
