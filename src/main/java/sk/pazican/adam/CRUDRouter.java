package sk.pazican.adam;

import sk.pazican.adam.controllers.IController;
import com.google.gson.Gson;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class CRUDRouter {

    public CRUDRouter(IController controller, String name) {
        Gson gson = new Gson();

        path("/" + name, () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/", (req, res) -> controller.getAll(req, res), gson::toJson);
            get("/:id", (req, res) -> controller.get(req, res), gson::toJson);
            post("/", (req, res) -> controller.create(req, res), gson::toJson);
            put("/:id", (req, res) -> controller.update(req, res), gson::toJson);
            delete("/:id", (req, res) -> controller.delete(req, res), gson::toJson);
        });
    }
}
