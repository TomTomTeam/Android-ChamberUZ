package uz.chamber.maroqand;

import java.util.ArrayList;

/**
 * Created by WTF on 2016-07-19.
 */
public interface CallBackNetwork {
    void setTitle(String title);
    void setBreadcrumb(ArrayList<String> breadcrumb);
    void setContent(ArrayList<Selector> content);
}
