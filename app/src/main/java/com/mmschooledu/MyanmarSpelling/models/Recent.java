package com.mmschooledu.MyanmarSpelling.models;


public class Recent {
    private int id;
    private int storyTypeId;
    private String nameUnicode;
    private String descriptionUnicode;

    public Recent(int id,int storyTypeId, String nameUnicode, String descriptionUnicode) {
        this.id = id;
        this.storyTypeId = storyTypeId;
        this.nameUnicode = nameUnicode;
        this.descriptionUnicode = descriptionUnicode;
    }

    public int getId() {
        return id;
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
    public String getDescriptionUnicode(){ return descriptionUnicode;}
}


