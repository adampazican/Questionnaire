package com.company;

public class Category extends ResponseObject{
    private String id;
    private String name;

    public Category(String name){
        super(200, null);
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
