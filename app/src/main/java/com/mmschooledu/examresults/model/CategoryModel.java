package com.mmschooledu.examresults.model;
public class CategoryModel {
    private String name ;
    public CategoryModel() {
    }
    public CategoryModel(String name ) {
        this.name = name;

    }
    public String getPosition() {
        return name;
    }
    public void setPosition(String p) {
        this.name = p;
    }

}

