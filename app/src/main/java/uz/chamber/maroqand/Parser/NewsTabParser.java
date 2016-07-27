package uz.chamber.maroqand.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.Selector;
import uz.chamber.maroqand.Model.TabList;

public class NewsTabParser {
    CallBackNetwork callBackNetwork;
    String html;
    Document document;
    ArrayList<TabList> tabLists;

    public NewsTabParser(String html, CallBackNetwork callBackNetwork) {
        this.html = html;
        this.callBackNetwork = callBackNetwork;

        tabLists = new ArrayList<>();

        NewsParserThread newsParserThread = new NewsParserThread();
        newsParserThread.start();
    }

    class NewsParserThread extends Thread {
        @Override
        public void run() {
            super.run();

            try {
                document = Jsoup.connect(html).get();

                Elements elements = document.select("ul.tabs > li");

                for (int i = 0; i < elements.size(); i++) {
                    tabLists.add(new TabList(elements.get(i).text()
                            , elements.get(i).select("a[href]").attr("href")));

                }

                for (int i = 0; i < tabLists.size(); i++) {
                    final int j = i;
                    elements = document.select("div" + tabLists.get(i).getHref());
                    Elements page = elements.select("ul.yiiPager > li");

                    for (Element e : page) {

                        if (e.text().equals("1")) {
                            NewsTabContentParser newsTabContentParser = new NewsTabContentParser(
                                    e.select("a[href]").attr("abs:href"), tabLists.get(i).getHref(), new CallBackNetwork() {
                                @Override
                                public void setTitle(String title) {
                                }

                                @Override
                                public void setBreadcrumb(ArrayList<String> breadcrumb) {
                                }

                                @Override
                                public void setContent(ArrayList<Selector> content) {
                                }

                                @Override
                                public void setNewsContent(ArrayList<NewsListComponent> newsContent) {

                                    tabLists.get(j).getNewsListComponents().addAll(newsContent);

                                }
                            });

                        }
                    }
                    if (page.size() == 0) {
                        NewsTabContentParser newsTabContentParser = new NewsTabContentParser(
                                html, tabLists.get(i).getHref(), new CallBackNetwork() {
                            @Override
                            public void setTitle(String title) {
                            }

                            @Override
                            public void setBreadcrumb(ArrayList<String> breadcrumb) {
                            }

                            @Override
                            public void setContent(ArrayList<Selector> content) {
                            }

                            @Override
                            public void setNewsContent(ArrayList<NewsListComponent> newsContent) {
                                tabLists.get(j).getNewsListComponents().addAll(newsContent);

                            }
                        });
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}