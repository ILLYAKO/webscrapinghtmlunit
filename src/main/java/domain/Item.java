package domain;

import java.util.Objects;

public class Item {
    private String title;
    private String company;
    private String city;
    private String url;

    public Item() {    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = "https://www.indeed.ca" + url;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(title, item.title) &&
                Objects.equals(company, item.company) &&
                Objects.equals(city, item.city) &&
                Objects.equals(url, item.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, company, city, url);
    }
}
