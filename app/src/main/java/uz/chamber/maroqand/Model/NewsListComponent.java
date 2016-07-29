package uz.chamber.maroqand.Model;

public class NewsListComponent {
    String srcUrl;
    String date;
    String title;
    String html;

    public NewsListComponent(String srcUrl, String date, String title, String html) {
        this.srcUrl = srcUrl;
        this.date = date;
        this.title = title;
        this.html = html;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getHtml() {
        return html;
    }
}
