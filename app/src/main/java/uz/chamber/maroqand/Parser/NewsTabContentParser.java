package uz.chamber.maroqand.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.Model.NewsListComponent;

public class NewsTabContentParser {
    String html;
    String tabName;
    Document document;
    ArrayList<NewsListComponent> content;
    CallBackNetwork callBackNetwork;


    public NewsTabContentParser(String html, String tabName, CallBackNetwork callBackNetwork) {
        this.html = html;
        this.tabName = tabName;
        this.callBackNetwork = callBackNetwork;

        content = new ArrayList<>();
        NewsContentThread newsContentThread = new NewsContentThread();
        newsContentThread.start();
    }

    class NewsContentThread extends Thread {
        @Override
        public void run() {
            super.run();

            try {
                document = Jsoup.connect(html).get();
                Elements tab = document.select("div" + tabName);
                Elements elements = tab.select("div.items > div");

                for (Element e : elements) {
                    content.add(new NewsListComponent(e.select("img").attr("abs:src")
                            , e.select("span.date").text(), e.select("p").text()));
                }
                callBackNetwork.setNewsContent(content);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}