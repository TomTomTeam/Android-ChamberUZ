package uz.chamber.maroqand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Parser {
    CallBackNetwork callBackNetwork;
    String html;
    Document document;
    String spage_header;
    ArrayList<String> breadcrumb;
    ArrayList<Selector> page_content;

    public Parser(String html, CallBackNetwork callBackNetwork) {
        this.html = html;
        this.callBackNetwork = callBackNetwork;

        breadcrumb = new ArrayList<>();
        page_content = new ArrayList<>();

        ParserThread parserThread = new ParserThread();
        parserThread.start();
    }

    class ParserThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                document = Jsoup.connect(html).get();

                Elements elements = document.select("h1.spage-header");
                spage_header = elements.text();
                callBackNetwork.setTitle(spage_header);

                elements = document.select("ol.breadcrumb > li");

                for (int i = 0; i < elements.size(); i++) {
                    breadcrumb.add(elements.get(i).text());
                }
                callBackNetwork.setBreadcrumb(breadcrumb);

                elements = document.select("div.page-content > div");
                if (elements.size() == 0) {
                    elements = document.select("div.page-content > p");
                }

                for (int i = 0; i < elements.size(); i++) {

                    //line process
                    String tmp = elements.get(i).html().replace("<br>", "$$$");
                    Document tmpD;
                    tmpD = Jsoup.parse(tmp);

                    Selector se = new Selector(tmpD.body().text().replace("$$$", "\n")
                            , elements.get(i).select("a[href]").attr("abs:href")
                            , elements.get(i).select("img").attr("src"));
                    page_content.add(se);
                }
                callBackNetwork.setContent(page_content);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
