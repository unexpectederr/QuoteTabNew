package models.images;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class ImageSuggestion {

    private int imageId;
    private boolean selected;

    @SerializedName("largeImageUrl")
    private String largeImageUrl;

    @SerializedName("previewUrl")
    private String previewImageUrl;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
