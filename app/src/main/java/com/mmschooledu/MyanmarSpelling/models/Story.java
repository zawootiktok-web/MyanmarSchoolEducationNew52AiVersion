package com.mmschooledu.MyanmarSpelling.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {

    private int id;
    private int story_type_id; // Added field
    private String nameUnicode;
    private String descriptionUnicode;

    // Constructor
    public Story(int id, int story_type_id, String nameUnicode, String descriptionUnicode) {
        this.id = id;
        this.story_type_id = story_type_id;
        this.nameUnicode = nameUnicode;
        this.descriptionUnicode = descriptionUnicode;
    }

    // Parcelable constructor
    protected Story(Parcel in) {
        id = in.readInt();
        story_type_id = in.readInt(); // Read story_type_id from Parcel
        nameUnicode = in.readString();
        descriptionUnicode = in.readString();
    }

    // Parcelable Creator
    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    // Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoryTypeId() {
        return story_type_id;
    }

    public String getNameUnicode() {
        return nameUnicode;
    }

    public String getDescriptionUnicode() {
        return descriptionUnicode;
    }

    // Setters
    public void setStoryTypeId(int story_type_id) {
        this.story_type_id = story_type_id;
    }

    public void setNameUnicode(String nameUnicode) {
        this.nameUnicode = nameUnicode;
    }

    public void setDescriptionUnicode(String descriptionUnicode) {
        this.descriptionUnicode = descriptionUnicode;
    }

    // toString Method
    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", story_type_id=" + story_type_id +
                ", nameUnicode='" + nameUnicode + '\'' +
                ", descriptionUnicode='" + descriptionUnicode + '\'' +
                '}';
    }

    // Parcelable Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(story_type_id); // Write story_type_id to Parcel
        parcel.writeString(nameUnicode);
        parcel.writeString(descriptionUnicode);
    }
}
