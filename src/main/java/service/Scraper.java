package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import domain.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Scraper {
//    private static  final  String baseUrl ="https://sfbay.craigslist.org/search/sss?query=iphone+8";
    private static  final  String baseUrl ="https://www.indeed.ca/jobs?q=developer&l=Winnipeg";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage page = client.getPage(baseUrl);
           // List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//li[@class='result-row']");
            List<HtmlElement> items =
                    (List<HtmlElement>) page.getByXPath(".//div[ starts-with(@class, 'jobsearch-SerpJobCard')]");
            if(items.isEmpty()){
                System.out.println("No items found.");
            }else{
                System.out.println("Items found.");
                for(HtmlElement htmlItem : items){
                    System.out.println("---: "+htmlItem);
//                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath("p[@class='result-info']/a"));
//                    HtmlElement spanPrice = ((HtmlElement)htmlItem.getFirstByXPath(".//a/span[@class='result-price']"));
//
//                    String itemPrice=(spanPrice==null ? "0.0":spanPrice.asText());
//
//                    Item item = new Item();
//                    item.setTitle(itemAnchor.asText());
//                    item.setUrl(itemAnchor.getHrefAttribute());
//                    item.setPrice(new BigDecimal(itemPrice.replace("$","")));
//
//                    ObjectMapper mapper = new ObjectMapper();
//                    String jsonString = mapper.writeValueAsString(item);
//                    System.out.println(jsonString);
                }

            }




            //System.out.println(page.asXml());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
