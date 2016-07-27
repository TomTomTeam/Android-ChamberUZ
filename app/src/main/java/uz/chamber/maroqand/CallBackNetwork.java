package uz.chamber.maroqand;

import java.util.ArrayList;

public interface CallBackNetwork {
    // parse text hyper image
    void setTitle(String title);

    void setBreadcrumb(ArrayList<String> breadcrumb);

    void setContent(ArrayList<Selector> content);

    // get news page content
    void setNewsContent(ArrayList<NewsListComponent> newsContent);
}
