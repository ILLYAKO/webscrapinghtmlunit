package domain;
import java.math.BigDecimal;

public class Item {
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url ="https://www.indeed.ca"+ url;
    }
}
