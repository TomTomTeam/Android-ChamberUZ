package uz.chamber.maroqand;

/**
 * Created by WTF on 2016-07-27.
 */
public class NewsListComponent {
    String srcUrl;
    String date;
    String title;

    public NewsListComponent(String srcUrl, String date, String title) {
        this.srcUrl = srcUrl;
        this.date = date;
        this.title = title;
    }
}
