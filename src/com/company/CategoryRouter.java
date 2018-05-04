package com.company;

import com.google.gson.Gson;

import static spark.Spark.*;
import static spark.Spark.delete;

public class CategoryRouter {

    public CategoryRouter(CategoryController categoryController) {
        Gson gson = new Gson();

        path("/categories", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> categoryController.getAll(req, res), gson::toJson);
            get("/:id", (req, res) -> categoryController.get(req, res), gson::toJson);
            post("/", (req, res) -> categoryController.create(req, res), gson::toJson);
            put("/:id", (req, res) -> categoryController.update(req, res), gson::toJson);
            delete("/:id", (req, res) -> categoryController.delete(req, res), gson::toJson);
        });
    }
}
