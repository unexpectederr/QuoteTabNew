package models.dashboard;


public class PopularAuthor {

    private String name;
    private String authorId;
    private String imageUrl;

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

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
