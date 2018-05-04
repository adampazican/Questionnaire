package com.company;

import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryController implements IController {
    private Statement statement;

    public CategoryController(Statement statement){
        this.statement = statement;
    }

    @Override
    public List<ResponseObject> getAll(Request req, Response res) {
        String sql = "SELECT * from categories;";
        List<ResponseObject> categories = new ArrayList<>();

        try {
            ResultSet rs = this.statement.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                categories.add(new Category(name));
            }
        }
        catch (SQLException e){
            categories.add(new ResponseObject(500, "Internal server error"));
        }

        return categories;
    }

    @Override
    public ResponseObject get(Request req, Response res) {
        String id = req.params(":id");
        String sql = String.format("SELECT * FROM categories WHERE id=%s", id);
        Category category = null;

        try {
            ResultSet rs = this.statement.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                category = new Category(name);
            }
        }
        catch (SQLException e){
            return new ResponseObject(500, "Internal server error");
        }

        return category == null ? new ResponseObject(404, "Question not found") : category;
    }

    @Override
    public ResponseObject update(Request req, Response res) {
        return null;
    }

    @Override
    public ResponseObject delete(Request req, Response res) {
        return null;
    }

    @Override
    public ResponseObject create(Request req, Response res) {
        return null;
    }
}
