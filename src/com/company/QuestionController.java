package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;

public class QuestionController {
    public QuestionController(QuestionHandler questionHandler){
        Gson gson = new Gson();
        path("/questions", () -> {
            get("/", (req, res) -> questionHandler.getAllQuestions(), gson::toJson);
            get("/:id", (req, res) -> questionHandler.getQuestion(req.params(":id")), gson::toJson);
            post("/", (req, res) ->
                    questionHandler.createQuestion("Super star", 2, "Jozko", "Knet", "Dominika", "VUwuuuuvus"),
                    gson::toJson);
            put("/:id", (req, res) -> questionHandler.updateQuestion(req.params(":id"), "", "", ""), gson::toJson);
        });
    }
}
