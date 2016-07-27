package uz.chamber.maroqand.Model;

/**
 * Created by lk on 2016. 7. 27..
 */
public class ATag {
    private String title;
    private String url;

    public ATag(String title, String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
