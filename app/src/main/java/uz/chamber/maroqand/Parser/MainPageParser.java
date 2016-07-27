package uz.chamber.maroqand.Parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import uz.chamber.maroqand.CallBack;
import uz.chamber.maroqand.Model.ATag;
import uz.chamber.maroqand.Model.FooterData;
import uz.chamber.maroqand.Model.MainViewListData;
import uz.chamber.maroqand.Model.MainViewPagerData;

/**
 * Created by lk on 2016. 7. 19..
 */
public class MainPageParser {
    String html = "http://chamber.uz/en/index"; //todo Language setting
    Document document;
    Elements carouselInner;
    Elements scarouselInner;
    Elements bottomBanner;
    Elements schedule;
    String title;
    CallBack callBack;
    String linkUrl;
    String imgUrl;


    final String TAG = "MainPageParser";


    public MainPageParser(CallBack callBack) {
        this.callBack = callBack;
        new ConnectThread().start();
    }

    private class ConnectThread extends Thread {
        @Override
        public void run() {
            ArrayList<MainViewPagerData> dataListBanner = new ArrayList<>();
            document = Jsoup.parse(requestHttp(html));
            carouselInner = document.select("div.carousel-inner > div.item");
            for (int i = 0; i < carouselInner.size(); i++) {
                imgUrl = "http://chamber.uz" + carouselInner.get(i).select("img[src]").first().attr("src");
                linkUrl = "http://chamber.uz" + carouselInner.get(i).select("a[href]").first().attr("href");
                title = carouselInner.get(i).text();
                MainViewPagerData data = new MainViewPagerData(imgUrl, title, linkUrl);
                dataListBanner.add(data);

                Log.d(TAG + "/Banner", "Image URL = " + imgUrl + "\nLinkURL = " + linkUrl + "\nTitle = " + title);
            }

            callBack.doneViewPager(dataListBanner);

            ArrayList<MainViewPagerData> dataListNews = new ArrayList<>();

            scarouselInner = document.select("div.scarousel-inner > div.scarousel-item");
            for (int i = 0; i < scarouselInner.size(); i++) {
                imgUrl = "http://chamber.uz" + scarouselInner.get(i).select("img[src]").first().attr("src");
                linkUrl = "http://chamber.uz" + scarouselInner.get(i).select("a[href]").first().attr("href");
                title = scarouselInner.get(i).text();
                try {
                    title = title.substring(10, title.length() - 10);
                } catch (StringIndexOutOfBoundsException e) {
                    title = "";
                }
                MainViewPagerData data = new MainViewPagerData(imgUrl, title, linkUrl);
                dataListNews.add(data);
                Log.d(TAG + "/News", "Image URL = " + imgUrl + "\nLinkURL = " + linkUrl + "\nTitle = " + title);
            }
            callBack.doneNews(dataListNews);

            bottomBanner = document.select("div.banner-bottom");
            imgUrl = "http://chamber.uz" + bottomBanner.first().select("img[src]").first().attr("src");
            linkUrl = "http://chamber.uz" + bottomBanner.first().select("a[href]").first().attr("href");

            Log.i(TAG + "/Banner", imgUrl + " / " + linkUrl);

            callBack.doneBannerBottom(imgUrl, linkUrl);

            ArrayList<MainViewListData> dataListSchedule = new ArrayList<>();

            schedule = document.select("div.row.sideevents > ul > li");
            for (int i = 0; i < schedule.size(); i++) {
                Element date = schedule.get(i).select("div.date").first();

                Element content = schedule.get(i).select("div.event-content").first();
                MainViewListData data = new MainViewListData(
                        content.select("a").first().text(),             // Content Title
                        content.select("li").get(0).text(),             // Time
                        content.select("li").get(1).text(),             // Address
                        date.select("span.month").first().text(),       // Month
                        date.select("span.day").first().text()          // Day
                );

                dataListSchedule.add(data);
            }

            callBack.doneSchedule(dataListSchedule);

            Elements partners = document.select("span.partners-inner > a");
            ArrayList<MainViewPagerData> dataListPartners = new ArrayList<>();
            Log.e("aa", partners.toString());
            for (int i = 0; i < partners.size(); i++) {
                imgUrl = "http://chamber.uz" + partners.get(i).select("img[src]").first().attr("src");
                linkUrl = partners.get(i).select("a[href]").first().attr("abs:href");
                Log.e("aa", imgUrl + ". / " + linkUrl);
                dataListPartners.add(new MainViewPagerData(imgUrl, "", linkUrl));
            }

            callBack.donePartner(dataListPartners);

            Elements footer = document.select("div#main-footer > div.container > div");
            Log.e("aaa", footer.toString());

            String addressTitle = footer.get(0).select("h4").text();
            String addressContent = footer.get(0).select("p").html().replace("<br>", "$$$");
            document = Jsoup.parse(addressContent);
            addressContent = document.text().replace("$$$", "\n");

            String linkTitle = footer.get(1).select("h4").text();
            ArrayList<ATag> linkList = new ArrayList<>();
            for (int j = 1; j <= 2; j++) {
                Elements usefulLinkes = footer.get(j).select("li > a");
                for (int i = 0; i < usefulLinkes.size(); i++) {
                    String title = usefulLinkes.get(i).text();
                    String url = usefulLinkes.get(i).attr("href");
                    if (url.charAt(0) == '/')
                        url = "http://chamber.uz" + url;
                    Log.e("aa", i + " / " + title + " / " + url);
                    ATag tag = new ATag(title, url);
                    linkList.add(tag);
                }
            }

            String connectCCITitle = footer.get(3).select("h4").text();
            Elements sendAMessageToCCI = footer.get(3).select("a");
            ArrayList<ATag> connectList = new ArrayList<>();
            for(int i=0; i< sendAMessageToCCI.size(); i++){
                String title = sendAMessageToCCI.get(i).text();
                String url = sendAMessageToCCI.get(i).attr("href");
                if (url.charAt(0) == '/')
                    url = "http://chamber.uz" + url;
                ATag tag = new ATag(title, url);
                connectList.add(tag);
            }
            FooterData.getInstance(addressTitle, addressContent, linkTitle, linkList, connectCCITitle, connectList);
            callBack.doneFooter();




        }

        public String requestHttp(String urlStr) {
            try {
                URL url = new URL(urlStr);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String response = "";
                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    response += tmp;
                    Log.e("parse", tmp);
                }
                is.close();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //todo Add Connection Error Dialog
                e.printStackTrace();
            }
            return null;
        }
    }
}
