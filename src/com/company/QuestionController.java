package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;

public class QuestionController {
    public QuestionController(QuestionHandler questionHandler){
        Gson gson = new Gson();
        path("/questions", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> questionHandler.getAllQuestions(), gson::toJson);
            get("/:id", (req, res) -> questionHandler.getQuestion(req.params(":id")), gson::toJson);
            post("/", (req, res) -> {
                String title = req.headers("title");
                int categoryId = Integer.parseInt(req.headers("categoryId"));
                String answer1 = req.headers("answer1");
                String answer2 = req.headers("answer2");
                String answer3 = req.headers("answer3");
                String realAnswer = req.headers("realAnswer");

                String ahoj = req.headers("joj");
                System.out.println(ahoj);

                return questionHandler.createQuestion(title, categoryId, answer1, answer2, answer3, realAnswer);
            }, gson::toJson);
            put("/:id", (req, res) -> questionHandler.updateQuestion(req.params(":id"), "", "", ""), gson::toJson);
        });
    }
}
