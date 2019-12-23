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

        for (int i = 0; i <= jobPages+1; i++) {
            try {
                if (baseUrlIsFirst) {
                    baseUrlIsFirst = false;
                } else {
                    baseUrl = rootUrl + "&start=" + (2 * i) + "0";
                }
                System.out.println("baseUrl:"+ baseUrl);
                HtmlPage page = client.getPage(baseUrl);

//                items.addAll(page.getByXPath("//div[ starts-with(@class, 'jobsearch-SerpJobCard')]"));
                items.addAll(page.getByXPath("//div[ starts-with(@class, 'jobsearch-SerpJobCard unifiedRow row result')]"));

                System.out.println("items.size()-"+items.size());
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
//                    System.out.println("htmlItem 1-"+htmlItem.getTextContent());
                    List<HtmlElement> hItems= htmlItem.getElementsByTagName("div");


//                    HtmlElement xElement = (HtmlElement) htmlItem.getFirstByXPath("*");
//                    System.out.println("xElement: "+xElement.getNodeName());

//                    HtmlAnchor itemAnchor;
//                    HtmlElement itemDiv= hItems.get(0);
                    HtmlElement itemDivTitle= htmlItem.getFirstByXPath(".//div[@class='title']");
                    HtmlAnchor itemTitleAnchor = itemDivTitle.getFirstByXPath(".//a");


//                    if (xElement.getNodeName().equalsIgnoreCase("a")) {
//                        itemAnchor = htmlItem.getFirstByXPath("*");
//                        System.out.println("--1");
//                    } else {
//                        itemAnchor = htmlItem.getFirstByXPath(".//h2/a");
//                        System.out.println("--2");
//                    }

//                    String titleItemAnchor = itemAnchor.asText();
                    String titleItem = itemDivTitle.asText();
                    System.out.println("titleItem-"+titleItem);

//                    HtmlSpan spanCompanyName = itemAnchor.getFirstByXPath("//div[@class='companyInfoWrapper']/div/span[@class='company']");
//                    String companyName = spanCompanyName.asText();

                    HtmlSpan spanCompanyName=htmlItem.getFirstByXPath(".//div[@class='sjcl']/div/span[@class='company']");
                    String companyName = spanCompanyName.asText();
//
//                    HtmlSpan spanCity = itemAnchor.getFirstByXPath("//div[@class='companyInfoWrapper']/span[@class='location']");
//                    String city = spanCity.asText();
                    String city="";
                    try{
                        HtmlSpan spanCity = htmlItem.getFirstByXPath(".//div[@class='sjcl']/span[@class='location accessible-contrast-color-location']");
                        city= spanCity.asText();
                    }catch (NullPointerException e){
                        HtmlDivision divCity = htmlItem.getFirstByXPath(".//div[@class='sjcl']/div[@class='location accessible-contrast-color-location']");
                        city= divCity.asText();
                    }

//
//                    String urlItemAnchor = itemAnchor.getHrefAttribute();
                    String urlItemAnchor = itemTitleAnchor.getHrefAttribute();


                    Item itemResult = new Item();
//                    itemResult.setTitle(titleItemAnchor);
                    itemResult.setTitle(titleItem);
                    itemResult.setCompany(companyName);
                    itemResult.setCity(city);
                    itemResult.setUrl(urlItemAnchor);

                    jobItems.add(itemResult);

                   ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(itemResult);
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
//            HtmlDivision divSearchCount = page.getFirstByXPath("//div[@id='searchCount']");
            HtmlDivision divSearchCountPages = page.getFirstByXPath("//div[@id='searchCountPages']");
            System.out.println("divSearchCountPages:"+ divSearchCountPages.asText());

            String[] stringNumberJobs = divSearchCountPages.getFirstChild().asText().split(" ");
            int numberJobs = Integer.parseInt(stringNumberJobs[3].replaceAll(",", ""));
            System.out.println("numberJobs:"+numberJobs);
            return (numberJobs / 20);
        } catch (IOException e) {
            System.out.println("jobPageCounter Exception!");
            e.printStackTrace();
            return 0;
        }
    }

    public List<Item> getJobItems() {
        return jobItems;
    }
}