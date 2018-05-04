package com.company;

import spark.Request;

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
    public List<ResponseObject> getAll() {
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
    public ResponseObject get(String id) {
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
    public ResponseObject update(String id, Request req) {
        return null;
    }

    @Override
    public ResponseObject delete(String id) {
        return null;
    }

    @Override
    public ResponseObject create(String title, String categoryId, String answer1, String answer2, String answer3, String realAnswer) {
        return null;
    }
}
