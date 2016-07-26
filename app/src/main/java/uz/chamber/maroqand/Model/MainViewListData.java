package uz.chamber.maroqand.Model;

/**
 * Created by lk on 2016. 7. 25..
 */
public class MainViewListData {

    private String content;
    private String time;
    private String address;
    private String month;
    private String day;

    public MainViewListData(String content, String time, String address, String month, String day) {
        this.content = content;
        this.time = time;
        this.address = address;
        this.month = month;
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}
