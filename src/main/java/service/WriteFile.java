package service;

import domain.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {
//    List<Item> jobItems = new ArrayList<>();
    private Item item;
    private List<Item> jobItems;
    private final String SEPARATOR = ";";

    public WriteFile(Item item, List<Item> jobItems) throws IOException {
        this.item = item;
        this.jobItems = jobItems;
        FileWriter fw = new FileWriter(item.getTitle()+"_"+item.getCity()+".csv");
        fw.write("Title"+SEPARATOR+"Company"+SEPARATOR+"Location" +"\n");
        for (Item jobitem : jobItems) {
            System.out.println(jobitem.toString());
            fw.write(jobitem.getTitle()+SEPARATOR+jobitem.getCompany()+SEPARATOR+ jobitem.getCompany()+" "+jobitem.getCity() +"\n");
        }
        fw.close();
    }
}
