package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;
import static spark.Spark.delete;

public class CategoryRouter {

    public CategoryRouter(CategoryController categoryController) {
        Gson gson = new Gson();

        path("/categories", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> categoryController.getAll(), gson::toJson);
            get("/:id", (req, res) -> categoryController.get(req.params(":id")), gson::toJson);
            post("/", (req, res) -> {
                String title = req.headers("title");
                String categoryId = req.headers("categoryId");
                String answer1 = req.headers("answer1");
                String answer2 = req.headers("answer2");
                String answer3 = req.headers("answer3");
                String realAnswer = req.headers("realAnswer");


                return categoryController.create(title, categoryId, answer1, answer2, answer3, realAnswer);
            }, gson::toJson);
            put("/:id", (req, res) -> categoryController.update(req.params(":id"), req), gson::toJson);
            delete("/:id", (req, res) -> categoryController.delete(req.params(":id")), gson::toJson);
        });
    }
}
