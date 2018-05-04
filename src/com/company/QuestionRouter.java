package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;

public class QuestionRouter {
    public QuestionRouter(QuestionController questionController){
        Gson gson = new Gson();
        path("/questions", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> questionController.getAll(req, res), gson::toJson);
            get("/:id", (req, res) -> questionController.get(req, res), gson::toJson);
            post("/", (req, res) -> questionController.create(req, res), gson::toJson);
            put("/:id", (req, res) -> questionController.update(req, res), gson::toJson);
            delete("/:id", (req, res) -> questionController.delete(req, res), gson::toJson);
        });
    }
}
