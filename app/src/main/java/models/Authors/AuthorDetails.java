package models.Authors;

import com.google.gson.annotations.SerializedName;

import models.Authors.Fields;

/**
 * Created by Spaja on 26-Dec-16.
 */
public class AuthorDetails {

    @SerializedName("_index")
    private String index;

    @SerializedName("_type")
    private String type;

    @SerializedName("_id")
    private String id;

    private Fields fields;
    private boolean isHeader;
    private int sectionFirstPosition;
    private String letter;

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }

    public int getSectionFirstPosition() {
        return sectionFirstPosition;
    }

    public void setSectionFirstPosition(int sectionFirstPosition) {
        this.sectionFirstPosition = sectionFirstPosition;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean header) {
        isHeader = header;
    }

    public Fields getFields() {
        return fields;
    }

    public String getId() {
        return id;
    }

    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }
}
