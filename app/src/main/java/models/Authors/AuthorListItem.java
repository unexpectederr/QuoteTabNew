package models.Authors;

/**
 * Created by unexpected_err on 03/01/2017.
 */

public class AuthorListItem {

    public boolean isHeader;

    public String text;

    public AuthorListItem(String text, boolean isHeader) {
        this.isHeader = isHeader;
        this.text = text;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean header) {
        isHeader = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
