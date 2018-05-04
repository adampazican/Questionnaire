package com.company;

import spark.Request;
import spark.Response;

import java.util.List;

public interface IController {
    List<ResponseObject> getAll(Request req, Response res);
    ResponseObject get(Request req, Response res);
    ResponseObject update(Request req, Response res);
    ResponseObject delete(Request req, Response res);
    ResponseObject create(Request req, Response res);
}
