package uz.chamber.maroqand.CallBack;

import java.util.ArrayList;

import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.Selector;
import uz.chamber.maroqand.Model.TabList;

public interface CallBackNetwork {
    // parse text hyper image
    void setTitle(String title);

    void setBreadcrumb(ArrayList<String> breadcrumb);

    void setContent(ArrayList<Selector> content);
}
