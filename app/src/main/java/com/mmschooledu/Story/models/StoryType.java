package com.mmschooledu.Story.models;

public class StoryType {
    private int id;
    private String nameUnicode;

    public StoryType(int id, String nameUnicode) {
        this.id = id;
        this.nameUnicode = nameUnicode;
    }

    public int getId() {
        return id;
    }

    public String getNameUnicode() {
        return nameUnicode;
    }
}

