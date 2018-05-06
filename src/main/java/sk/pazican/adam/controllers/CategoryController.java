package sk.pazican.adam.controllers;

import sk.pazican.adam.ResponseObject;
import sk.pazican.adam.ResponseStatus;
import sk.pazican.adam.databaseItems.Category;
import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryController implements IController {
    private Statement statement;

    public CategoryController(Statement statement){
        this.statement = statement;
    }

    @Override
    public List<ResponseObject> getAll(Request req, Response res) {
        String sql = "SELECT * FROM categories;";
        List<ResponseObject> categories = new ArrayList<>();

        try {
            ResultSet rs = this.statement.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                categories.add(new Category(name));
            }
        }
        catch (SQLException e){
            categories.add(new ResponseObject(500, ResponseStatus.INTERNALERROR.getResponseMessage()));
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
            return new ResponseObject(500, ResponseStatus.INTERNALERROR.getResponseMessage());
        }

        return category == null ? new ResponseObject(404, ResponseStatus.NOTFOUND.getResponseMessage()) : category;
    }

    @Override
    public ResponseObject update(Request req, Response res) {
        String id = req.params(":id");
        ResponseObject category = this.get(req, res);
        List<String> paramList = Arrays.asList("name");


        if(!(category instanceof Category)){
            return new ResponseObject(400, ResponseStatus.BADREQUEST.getResponseMessage());
        }

        for(String param : paramList){
            String value = req.headers(param);
            if(value != null){
                String sql = String.format("UPDATE categories SET %s='%s' WHERE id=%s;", param, value, id);
                try {
                    this.statement.executeQuery(sql);
                    category = this.get(req, res);
                }
                catch (SQLException e){
                    return new ResponseObject(400, ResponseStatus.BADREQUEST.getResponseMessage());
                }
            }
        }

        return category;
    }

    @Override
    public ResponseObject delete(Request req, Response res) {
        String id = req.params(":id");
        String sql = String.format("DELETE FROM categories WHERE id=%s;", id);
        ResponseObject category = this.get(req, res);

        try {
            this.statement.executeQuery(sql);
        }
        catch (SQLException e){
            return new ResponseObject(400, ResponseStatus.BADREQUEST.getResponseMessage());
        }


        return category;
    }

    @Override
    public ResponseObject create(Request req, Response res) {
        String name = req.headers("name");

        String sql = String.format("INSERT INTO categories (name) VALUES" +
                "('%s');", name);

        try {
            this.statement.executeQuery(sql);

        }
        catch (SQLException e){
            return new ResponseObject(400, ResponseStatus.BADREQUEST.getResponseMessage());
        }

        return new Category(name);
    }
}
