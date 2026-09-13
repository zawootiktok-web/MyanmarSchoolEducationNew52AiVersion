package com.mmschooledu.MyanmarSpelling.models;

public class Favourite {
    private int id;
    private int storyTypeId;
    private String nameUnicode;
    private String descriptionUnicode;

    public Favourite(int id,int storyTypeId, String nameUnicode, String descriptionUnicode) {
        this.id = id;
        this.storyTypeId = storyTypeId;
        this.nameUnicode = nameUnicode;
        this.descriptionUnicode = descriptionUnicode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStoryTypeId(int storyTypeId) {
        this.storyTypeId = storyTypeId;
    }

    public int getStoryTypeId() {
        return storyTypeId;
    }

    public String getNameUnicode() {
        return nameUnicode;
    }

    public void setNameUnicode(String nameUnicode) {
        this.nameUnicode = nameUnicode;
    }

    public String getDescriptionUnicode(){ return descriptionUnicode;}

    public void setDescriptionUnicode(String descriptionUnicode) {
        this.descriptionUnicode = descriptionUnicode;
    }
}


