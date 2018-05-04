package com.company;

import spark.Request;

import java.util.List;

public interface IController {
    List<ResponseObject> getAll();
    ResponseObject get(String id);
    ResponseObject update(String id, Request req);
    ResponseObject delete(String id);
    ResponseObject create(String title, String categoryId, String answer1, String answer2, String answer3, String realAnswer);
}
