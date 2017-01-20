package models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DashboardData {

    @SerializedName("popularAuthorsList")
    List<PopularAuthor> popularAuthors;

    @SerializedName("topPhotos")
    List<TopPhotos> topPhotos;

    public List<PopularAuthor> getPopularAuthors() {
        return popularAuthors;
    }

    public List<TopPhotos> getTopPhotos() {
        return topPhotos;
    }
}
