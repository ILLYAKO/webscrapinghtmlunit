package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import domain.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    // private static final String baseUrl = "https://www.indeed.ca/jobs?q=developer&l=Winnipeg";
    private static String baseUrl = "https://www.indeed.ca/jobs?q=developer&l=Winnipeg&sort=date";
    private static String rootUrl = baseUrl;
    //private static final String baseUrl = "https://www.indeed.ca/jobs?q=developer&l=Winnipeg&sort=date&start=20";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        boolean baseUrlIsFirst = true;
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        int jobPages = jobPageCounter(client, baseUrl);
        List<HtmlElement> items = new ArrayList<>();

        for (int i = 0; i <= jobPages; i++) {
            try {
                if (baseUrlIsFirst) {baseUrlIsFirst = false;
                } else { baseUrl = rootUrl + "&start=" + (2 * i) + "0";}

                HtmlPage page = client.getPage(baseUrl);
                items.addAll((List<HtmlElement>) page.getByXPath("//div[ starts-with(@class, 'jobsearch-SerpJobCard')]/h2[@id]"));
                //System.out.println(page.asXml());
            } catch (IOException e) { e.printStackTrace(); }
        }
        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            System.out.println( (items.size()+1) + " items found.");
            try {
                for (HtmlElement htmlItem : items) {
                    HtmlAnchor itemAnchor = (HtmlAnchor) htmlItem.getFirstChild();
                    String urlItemAnchor = itemAnchor.getHrefAttribute();
                    String titleItemAnchor = itemAnchor.asText();

                    Item item = new Item();
                    item.setTitle(titleItemAnchor);
                    item.setUrl(urlItemAnchor);

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);
                    System.out.println(jsonString);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private static int jobPageCounter(WebClient client, String baseUrl) {
        try {
            HtmlPage page = client.getPage(baseUrl);
            HtmlDivision divSearchCount = page.getFirstByXPath("//div[@id='searchCount']");
            String[] stringNumberJobs = divSearchCount.getFirstChild().asText().split(" ");
            int numberJobs = Integer.parseInt(stringNumberJobs[3]);
            System.out.println("numberJobs: " + numberJobs);
            return (numberJobs / 20 );
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
