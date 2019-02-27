package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import domain.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private static String baseUrl = "";
    private static String rootUrl = "";
    List<HtmlElement> itemsTotal = new ArrayList<>();
    List<Item> jobItems = new ArrayList<>();

    public Scraper(Item item) {
        baseUrl = "https://www.indeed.ca/jobs?q=" + item.getTitle() + "&l=" + item.getCity() + "&sort=date";
        rootUrl=baseUrl;
        WebClient client = new WebClient();
        boolean baseUrlIsFirst = true;
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        int jobPages = jobPageCounter(client, baseUrl);

        List<HtmlElement> items = new ArrayList<>();

        for (int i = 0; i <= jobPages; i++) {
            try {
                if (baseUrlIsFirst) {
                    baseUrlIsFirst = false;
                } else {
                    baseUrl = rootUrl + "&start=" + (2 * i) + "0";
                }

                HtmlPage page = client.getPage(baseUrl);

                items.addAll(page.getByXPath("//div[ starts-with(@class, 'jobsearch-SerpJobCard')]"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            this.itemsTotal=items;
           // System.out.println((items.size() + 1) + " items found.");
            try {
                for (HtmlElement htmlItem : items) {
                    HtmlElement xElement = (HtmlElement) htmlItem.getFirstByXPath("*");
                    HtmlAnchor itemAnchor;
                    if (xElement.getNodeName().equalsIgnoreCase("a")) {
                        itemAnchor = htmlItem.getFirstByXPath("*");
                    } else {
                        itemAnchor = htmlItem.getFirstByXPath(".//h2/a");
                    }

                    String titleItemAnchor = itemAnchor.asText();

                    HtmlSpan spanCompanyName = itemAnchor.getFirstByXPath("//div[@class='companyInfoWrapper']/div/span[@class='company']");
                    String companyName = spanCompanyName.asText();

                    HtmlSpan spanCity = itemAnchor.getFirstByXPath("//div[@class='companyInfoWrapper']/span[@class='location']");
                    String city = spanCity.asText();

                    String urlItemAnchor = itemAnchor.getHrefAttribute();


                    Item itemResult = new Item();
                    itemResult.setTitle(titleItemAnchor);
                    itemResult.setCompany(companyName);
                    itemResult.setCity(city);
                    itemResult.setUrl(urlItemAnchor);

                    jobItems.add(itemResult);

                   ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(itemResult);
                   //System.out.println(jsonString);
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
            int numberJobs = Integer.parseInt(stringNumberJobs[3].replaceAll(",", ""));
            return (numberJobs / 20);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Item> getJobItems() {
        return jobItems;
    }
}