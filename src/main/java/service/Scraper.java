package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import domain.Item;

import java.io.IOException;
import java.util.List;

public class Scraper {
    // private static final String baseUrl = "https://www.indeed.ca/jobs?q=developer&l=Winnipeg";
    private static final String baseUrl = "https://www.indeed.ca/jobs?q=developer&l=Winnipeg&sort=date";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[ starts-with(@class, 'jobsearch-SerpJobCard')]/h2[@id]");

            if (items.isEmpty()) {
                System.out.println("No items found.");
            } else {
                //System.out.println("Items found.");
                for (HtmlElement htmlItem : items) {
                    HtmlAnchor itemAnchor = (HtmlAnchor)htmlItem.getFirstChild();
                    String urlItemAnchor = itemAnchor.getHrefAttribute();
                    String titleItemAnchor = itemAnchor.asText();

                    Item item = new Item();
                    item.setTitle(titleItemAnchor);
                    item.setUrl(urlItemAnchor);

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);
                    System.out.println(jsonString);
                }
            }
            //System.out.println(page.asXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
