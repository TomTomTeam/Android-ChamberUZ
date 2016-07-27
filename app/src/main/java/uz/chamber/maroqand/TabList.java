package uz.chamber.maroqand;

import java.util.ArrayList;

/**
 * Created by WTF on 2016-07-26.
 */
public class TabList {
    String title;
    String href;
    ArrayList<NewsListComponent> newsListComponents;

    public TabList(String title, String href) {
        this.title = title;
        this.href = href;
        newsListComponents = new ArrayList<>();
    }
}
