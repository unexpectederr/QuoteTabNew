package models.Authors;

import com.google.gson.annotations.SerializedName;

import models.Authors.Fields;

/**
 * Created by Spaja on 26-Dec-16.
 */
public class Results {

    @SerializedName("_index")
    private String index;

    @SerializedName("_type")
    private String type;

    @SerializedName("_id")
    private String id;

    private Fields fields;

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
