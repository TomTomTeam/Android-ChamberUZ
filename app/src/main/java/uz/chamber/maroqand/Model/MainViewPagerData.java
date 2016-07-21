package uz.chamber.maroqand.Model;

/**
 * Created by lk on 2016. 7. 21..
 */
public class MainViewPagerData {
    private String imgUrl;
    private String title;
    private String linkUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public MainViewPagerData(String imgUrl, String title, String linkUrl) {

        this.imgUrl = imgUrl;
        this.title = title;
        this.linkUrl = linkUrl;
    }
}
