package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;

public class QuestionController {
    public QuestionController(QuestionHandler questionHandler){
        Gson gson = new Gson();
        path("/questions", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> questionHandler.getAll(), gson::toJson);
            get("/:id", (req, res) -> questionHandler.get(req.params(":id")), gson::toJson);
            post("/", (req, res) -> {
                String title = req.headers("title");
                String categoryId = req.headers("categoryId");
                String answer1 = req.headers("answer1");
                String answer2 = req.headers("answer2");
                String answer3 = req.headers("answer3");
                String realAnswer = req.headers("realAnswer");


                return questionHandler.create(title, categoryId, answer1, answer2, answer3, realAnswer);
            }, gson::toJson);
            put("/:id", (req, res) -> questionHandler.update(req.params(":id"), req), gson::toJson);
            delete("/:id", (req, res) -> questionHandler.delete(req.params(":id")), gson::toJson);
        });
    }
}
