package models.dashboard;


public class PopularAuthor {

    String name;
    String authorId;
    String imageUrl;

    public String getName() {
        return name;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PopularAuthor() {
        this.name = "";
        this.authorId = "";
        this.imageUrl = "";
    }
}
