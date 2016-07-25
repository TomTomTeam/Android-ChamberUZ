package uz.chamber.maroqand;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WTF on 2016-07-21.
 */
public class Parser {
    CallBackNetwork callBackNetwork;
    String html;
    Document document;
    String spage_header;
    ArrayList<String> breadcrumb;
    ArrayList<Selector> page_content;

    public Parser(String html, CallBackNetwork callBackNetwork){
        this.html= html;
        this.callBackNetwork=callBackNetwork;

        breadcrumb= new ArrayList<>();
        page_content=new ArrayList<>();

        ParserThread parserThread = new ParserThread();
        parserThread.start();
    }

    public ArrayList<Selector> getPage_content() {
        return page_content;
    }

    public ArrayList<String> getBreadcrumb() {
        return breadcrumb;
    }

    public String getSpage_header() {
        return spage_header;
    }

    class ParserThread extends Thread{

        @Override
        public void run() {
            super.run();

            try{
                document= Jsoup.connect(html).get();

                Elements elements = document.select("h1.spage-header");
                spage_header=elements.text();
                callBackNetwork.setTitle(spage_header);

                elements =document.select("ol.breadcrumb > li");

                for(int i=0;i<elements.size();i++){
                    breadcrumb.add(elements.get(i).text());
                }
                callBackNetwork.setBreadcrumb(breadcrumb);

                elements = document.select("div.page-content > p");

                for(int i=0;i<elements.size();i++){
                   Log.i("test", elements.get(i).absUrl("src"));

                    Selector se=new Selector(elements.get(i).text()
                            ,elements.get(i).select("a[href]").attr("abs:href")
                            ,elements.get(i).select("img").attr("src"));
                    page_content.add(se);
                }
                callBackNetwork.setContent(page_content);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
