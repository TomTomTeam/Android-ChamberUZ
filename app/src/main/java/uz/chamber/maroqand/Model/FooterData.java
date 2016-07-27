package uz.chamber.maroqand.Model;

import java.util.ArrayList;

/**
 * Created by lk on 2016. 7. 27..
 */
public class FooterData {
    private static FooterData instance;
    private String addressTitle;
    private String addressContent;
    private String linkTitle;
    private ArrayList<ATag> linkContent;
    private String connectCCITitle;
    private ArrayList<ATag> connectCCIContent;

    private FooterData(String addressTitle, String addressContent, String linkTitle, ArrayList<ATag> linkContent, String connectCCITitle, ArrayList<ATag> connectCCIContent) {
        this.addressTitle = addressTitle;
        this.addressContent = addressContent;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
        this.connectCCITitle = connectCCITitle;
        this.connectCCIContent = connectCCIContent;
    }

    public static FooterData getInstance(String addressTitle, String addressContent, String linkTitle, ArrayList<ATag> linkContent, String connectCCITitle, ArrayList<ATag> connectCCIContent) {
        if(instance == null){
            instance = new FooterData(addressTitle, addressContent, linkTitle, linkContent, connectCCITitle, connectCCIContent);
        }
        return instance;
    }

    public static FooterData getInstance(){
        return instance;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public ArrayList<ATag> getLinkContent() {
        return linkContent;
    }

    public String getConnectCCITitle() {
        return connectCCITitle;
    }

    public ArrayList<ATag> getConnectCCIContent() {
        return connectCCIContent;
    }
}
