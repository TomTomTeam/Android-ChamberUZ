package uz.chamber.maroqand.Model;

import java.util.ArrayList;

public class TabList {
    private String title;
    private String href;
    private ArrayList<NewsListComponent> newsListComponents;

    public TabList(String title, String href) {
        this.title = title;
        this.href = href;
        newsListComponents = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public ArrayList<NewsListComponent> getNewsListComponents() {
        return newsListComponents;
    }
}

