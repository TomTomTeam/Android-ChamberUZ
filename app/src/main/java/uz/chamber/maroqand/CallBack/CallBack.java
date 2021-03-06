package uz.chamber.maroqand.CallBack;

import java.util.ArrayList;

import uz.chamber.maroqand.Model.FooterData;
import uz.chamber.maroqand.Model.MainViewListData;
import uz.chamber.maroqand.Model.MainViewPagerData;

/**
 * Created by lk on 2016. 7. 20..
 */
public interface CallBack {
    void doneViewPager(ArrayList<MainViewPagerData> list);
    void doneNews(ArrayList<MainViewPagerData> list);
    void doneBannerBottom(String imgUrl, String linkUrl);
    void doneSchedule(ArrayList<MainViewListData> list);
    void donePartner(ArrayList<MainViewPagerData> dataListPartners);
    void doneFooter();

}
